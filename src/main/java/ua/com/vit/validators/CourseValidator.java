package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.StudentService;
import ua.com.vit.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseValidator {

    private final TeacherService serviceTeacher;
    private final StudentService serviceStudent;
    private final LessonService serviceLesson;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public CourseValidator(TeacherService serviceTeacher, StudentService serviceStudent, LessonService serviceLesson) {
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
        this.serviceLesson = serviceLesson;
    }

    public boolean checkForDeletion(Course course, HttpServletRequest request) {
        if (checkForTeacherUsing(course)) {
            logger.error("checkForDeletion failed: course = {}", course);
            throw new IndelibleEntityException
                    ("The course cannot be deleted because still there are teachers which use it",
                            request.getHeader("Referer"));
        }
        if (checkForStudentUsing(course)) {
            logger.error("checkForDeletion failed: course = {}", course);
            throw new IndelibleEntityException
                    ("The course cannot be deleted because still there are students which use it",
                            request.getHeader("Referer"));
        }
        if (checkForLessonUsing(course)) {
            logger.error("checkForDeletion failed: course = {}", course);
            throw new IndelibleEntityException
                    ("The course cannot be deleted because still there are lessons which use it",
                            request.getHeader("Referer"));
        } else {
            return true;
        }
    }

    private boolean checkForTeacherUsing(Course course) {
        List<Integer> actualTeachersCourses = new ArrayList<>();
        serviceTeacher.getAllAsDto()
                .stream()
                .map(TeacherDto::getCoursesId)
                .forEach(set -> actualTeachersCourses.add(set.iterator().next()));
        return actualTeachersCourses
                .stream()
                .distinct()
                .collect(Collectors.toList())
                .contains(course.getId());
    }

    private boolean checkForStudentUsing(Course course) {
        List<Integer> actualStudentsCourses = new ArrayList<>();
        serviceStudent.getAllAsDto()
                .stream()
                .map(StudentDto::getCoursesId)
                .forEach(set -> actualStudentsCourses.add(set.iterator().next()));
        return actualStudentsCourses
                .stream()
                .distinct()
                .collect(Collectors.toList())
                .contains(course.getId());
    }

    private boolean checkForLessonUsing(Course course) {
        return serviceLesson.getAllAsDto()
                .stream()
                .map(LessonDto::getCourseId)
                .collect(Collectors.toList())
                .contains(course.getId());
    }
}

