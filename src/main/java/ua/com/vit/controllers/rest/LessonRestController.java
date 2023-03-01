package ua.com.vit.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.exceptions.ClassroomCapacityException;
import ua.com.vit.exceptions.InvalidLessonConditionsException;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.StudentService;
import ua.com.vit.service.TeacherService;
import ua.com.vit.validators.LessonValidator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@Validated
@RequestMapping("/rest/v1/lessons")
public class LessonRestController {

    @Autowired
    private LessonValidator validatorLesson;

    private final LessonService serviceLesson;
    private final TeacherService serviceTeacher;
    private final StudentService serviceStudent;

    public LessonRestController(LessonService serviceLesson, TeacherService serviceTeacher,
                                StudentService serviceStudent) {
        this.serviceLesson = serviceLesson;
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDto> getAll() {
        return serviceLesson.getAllAsDto();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessonDto getById(@PathVariable("id") @Min(0) int id) {
        return serviceLesson.getByIdAsDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody LessonDto lessonDto) {
        validatorLesson.checkConditions(lessonDto);
        validatorLesson.checkClassroomCapacity(lessonDto);
        serviceLesson.createFromDto(lessonDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody LessonDto lessonDto) {
        serviceLesson.updateFromDto(lessonDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(0) int id) {
        serviceLesson.deleteById(id);
    }

    @ExceptionHandler({InvalidLessonConditionsException.class})
    public ResponseEntity<Object> handleInvalidLessonConditionsException(InvalidLessonConditionsException exception) {
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler({ClassroomCapacityException.class})
    public ResponseEntity<Object> handleClassroomCapacityException(ClassroomCapacityException exception) {
        return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
    }

    @GetMapping("/schedule")
    @ResponseStatus(HttpStatus.OK)
    public List<LessonDto> getSchedule(@NotNull(message = "A person's selection is mandatory!")
                                       @RequestParam Object person,
                                       @NotNull(message = "The beginning of the date range is mandatory!")
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
                                       @NotNull(message = "The end of the date range is mandatory!")
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<LessonDto> lessons;
        String id = person.toString().substring(
                person.toString().indexOf("id=") + 3, person.toString().indexOf("id=") + 4);
        if (person.toString().contains("Teacher")) {
            lessons = serviceTeacher.receiveLessonsOnDateRange(Integer.parseInt(id), beginDate, endDate);
        } else {
            lessons = serviceStudent.receiveLessonsOnDateRange(Integer.parseInt(id), beginDate, endDate);
        }
        return lessons;
    }

}
