package ua.com.vit.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.CourseService;
import ua.com.vit.validators.CourseValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseValidator validatorCourse;

    private final CourseService serviceCourse;

    public CourseController(CourseService serviceCourse) {
        this.serviceCourse = serviceCourse;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("courses", serviceCourse.getAll());
        return "courses/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("course", serviceCourse.getById(id));
        return "courses/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("course") @NotNull Course course) {
        return "courses/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("course") Course course) {
        serviceCourse.create(course);
        return "redirect:/courses";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("course") Course course) {
        serviceCourse.update(course);
        return "redirect:/courses";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("course") Course course, HttpServletRequest request) {
        validatorCourse.checkForDeletion(course,request);
        serviceCourse.delete(course);
        return "redirect:/courses";
    }

    @ExceptionHandler({IndelibleEntityException.class})
    public ModelAndView handleException(IndelibleEntityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String thrownOutUrl = exception.getThrownOutUrl();
        if (thrownOutUrl.contains("courses/")) {
            modelAndView.addObject("course",
                    serviceCourse.getById(Integer.parseInt(thrownOutUrl.substring(thrownOutUrl.length() - 1))));
            modelAndView.setViewName("courses/showById");
        } else {
            modelAndView.addObject("courses", serviceCourse.getAll());
            modelAndView.setViewName("courses/showAll");
        }
        return modelAndView;
    }
}
