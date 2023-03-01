package ua.com.vit.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.FacultyService;
import ua.com.vit.service.TeacherService;
import ua.com.vit.validators.TeacherValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    TeacherValidator validatorTeacher;

    private final TeacherService serviceTeacher;
    private final FacultyService serviceFaculty;
    private final CourseService serviceCourse;

    public TeacherController(TeacherService serviceTeacher, FacultyService serviceFaculty,
                             CourseService serviceCourse) {
        this.serviceTeacher = serviceTeacher;
        this.serviceFaculty = serviceFaculty;
        this.serviceCourse = serviceCourse;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("teachers", serviceTeacher.getAllAsDto())
                .addAttribute("faculties", serviceFaculty)
                .addAttribute("courses", serviceCourse);
        return "teachers/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("teacher", serviceTeacher.getByIdAsDto(id))
                .addAttribute("faculties", serviceFaculty.getAll())
                .addAttribute("allCourses", serviceCourse.getAll());
        return "teachers/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("teacher") @NotNull TeacherDto teacher,
                         ModelMap model) {
        model.addAttribute("faculties", serviceFaculty.getAll())
                .addAttribute("allCourses", serviceCourse.getAll());
        return "teachers/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("teacher") TeacherDto teacher) {
        serviceTeacher.createFromDto(teacher);
        return "redirect:/teachers";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("teacher") TeacherDto teacher) {
        serviceTeacher.updateFromDto(teacher);
        return "redirect:/teachers";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("teacher") TeacherDto teacher, HttpServletRequest request) {
        validatorTeacher.checkForDeletion(teacher, request);
        serviceTeacher.deleteById(teacher.getId());
        return "redirect:/teachers";
    }

    @ExceptionHandler({IndelibleEntityException.class})
    public ModelAndView handleException(IndelibleEntityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String thrownOutUrl = exception.getThrownOutUrl();
        if (thrownOutUrl.contains("teachers/")) {
            modelAndView.addObject("teacher",
                            serviceTeacher.getByIdAsDto(Integer.parseInt(thrownOutUrl.substring(thrownOutUrl.length() - 1))))
                    .addObject("faculties", serviceFaculty.getAll())
                    .addObject("allCourses", serviceCourse.getAll())
                    .setViewName("teachers/showById");
        } else {
            modelAndView.addObject("teachers", serviceTeacher.getAllAsDto())
                    .addObject("faculties", serviceFaculty)
                    .addObject("courses", serviceCourse)
                    .setViewName("teachers/showAll");
        }
        return modelAndView;
    }
}
