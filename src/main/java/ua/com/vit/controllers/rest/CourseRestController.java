package ua.com.vit.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.CourseService;
import ua.com.vit.validators.CourseValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rest/v1/courses")
public class CourseRestController {

    @Autowired
    private CourseValidator validatorCourse;

    private final CourseService serviceCourse;

    public CourseRestController(CourseService serviceCourse) {
        this.serviceCourse = serviceCourse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Course> getAll() {
        return serviceCourse.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Course getById(@PathVariable("id") @Min(0) int id) {
        return serviceCourse.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Course course) {
        serviceCourse.create(course);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Course course) {
        serviceCourse.update(course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(0) int id,
                       HttpServletRequest request) {
        validatorCourse.checkForDeletion(serviceCourse.getById(id), request);
        serviceCourse.deleteById(id);
    }

}
