package ua.com.vit.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vit.exceptions.ClassroomCapacityException;
import ua.com.vit.exceptions.InvalidLessonConditionsException;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.TeacherService;
import ua.com.vit.validators.LessonValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonValidator validatorLesson;

    private final LessonService serviceLesson;
    private final ClassroomService serviceClassroom;
    private final CourseService serviceCourse;
    private final TeacherService serviceTeacher;

    public LessonController(LessonService serviceLesson, ClassroomService serviceClassroom, CourseService serviceCourse,
                            TeacherService serviceTeacher) {
        this.serviceLesson = serviceLesson;
        this.serviceClassroom = serviceClassroom;
        this.serviceCourse = serviceCourse;
        this.serviceTeacher = serviceTeacher;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("lessons", serviceLesson.getAllAsDto());
        model.addAttribute("classrooms", serviceClassroom);
        model.addAttribute("courses", serviceCourse);
        model.addAttribute("teachers", serviceTeacher);
        return "lessons/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("lesson", serviceLesson.getByIdAsDto(id)).
                addAttribute("classrooms", serviceClassroom.getAllAsDto()).
                addAttribute("courses", serviceCourse.getAll()).
                addAttribute("teachers", serviceTeacher.getAllAsDto());
        return "lessons/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("lesson") @NotNull LessonDto lesson,
                         ModelMap model) {
        model.addAttribute("classrooms", serviceClassroom.getAllAsDto()).
                addAttribute("courses", serviceCourse.getAll()).
                addAttribute("teachers", serviceTeacher.getAllAsDto());
        return "lessons/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("lesson") LessonDto lesson) {
        validatorLesson.checkConditions(lesson);
        validatorLesson.checkClassroomCapacity(lesson);
        serviceLesson.createFromDto(lesson);
        return "redirect:/lessons";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("lesson") LessonDto lesson) {
        serviceLesson.updateFromDto(lesson);
        return "redirect:/lessons";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("lesson") LessonDto lesson) {
        serviceLesson.deleteFromDto(lesson);
        return "redirect:/lessons";
    }

    @ExceptionHandler({InvalidLessonConditionsException.class})
    public ModelAndView handleInvalidLessonConditionsException(InvalidLessonConditionsException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage())
                .addObject("lesson", new LessonDto())
                .addObject("classrooms", serviceClassroom.getAllAsDto())
                .addObject("courses", serviceCourse.getAll())
                .addObject("teachers", serviceTeacher.getAllAsDto())
                .setViewName("lessons/create");
        return modelAndView;
    }

    @ExceptionHandler({ClassroomCapacityException.class})
    public ModelAndView handleClassroomCapacityExceptionException(ClassroomCapacityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage())
                .addObject("lesson", new LessonDto())
                .addObject("classrooms", serviceClassroom.getAllAsDto())
                .addObject("courses", serviceCourse.getAll())
                .addObject("teachers", serviceTeacher.getAllAsDto())
                .setViewName("lessons/create");
        return modelAndView;
    }
}
