package ua.com.vit.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.FacultyService;
import ua.com.vit.service.StudentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping("/students")
public class StudentController {

    private final StudentService serviceStudent;
    private final FacultyService serviceFaculty;
    private final CourseService serviceCourse;

    public StudentController(StudentService serviceStudent, FacultyService serviceFaculty,
                             CourseService serviceCourse) {
        this.serviceStudent = serviceStudent;
        this.serviceFaculty = serviceFaculty;
        this.serviceCourse = serviceCourse;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("students", serviceStudent.getAllAsDto())
                .addAttribute("faculties", serviceFaculty)
                .addAttribute("courses", serviceCourse);
        return "students/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("student", serviceStudent.getByIdAsDto(id))
                .addAttribute("faculties", serviceFaculty.getAll())
                .addAttribute("allCourses", serviceCourse.getAll());
        return "students/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("student") @NotNull StudentDto student,
                         ModelMap model) {
        model.addAttribute("faculties", serviceFaculty.getAll())
                .addAttribute("allCourses", serviceCourse.getAll());
        return "students/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("student") StudentDto student) {
        serviceStudent.createFromDto(student);
        return "redirect:/students";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("student") StudentDto student) {
        serviceStudent.updateFromDto(student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("student") StudentDto student) {
        serviceStudent.deleteById(student.getId());
        return "redirect:/students";
    }

}
