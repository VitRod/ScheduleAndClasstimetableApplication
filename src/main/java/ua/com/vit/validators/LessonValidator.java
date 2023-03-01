package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.ClassroomCapacityException;
import ua.com.vit.exceptions.InvalidLessonConditionsException;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.repository.entities.Classroom;
import ua.com.vit.repository.entities.Lesson;
import ua.com.vit.repository.entities.Teacher;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.service.LessonService;
import ua.com.vit.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonValidator {

    private final LessonService serviceLesson;
    private final StudentService serviceStudent;
    private final ClassroomService serviceClassroom;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public LessonValidator(LessonService serviceLesson, StudentService serviceStudent,
                           ClassroomService serviceClassroom) {
        this.serviceLesson = serviceLesson;
        this.serviceStudent = serviceStudent;
        this.serviceClassroom = serviceClassroom;
    }

    public void checkClassroomCapacity(LessonDto lesson) {
        if (serviceStudent.receiveStudentsIdsWithCertainCourse(lesson.getCourseId()).size() >
                serviceClassroom.getById(lesson.getClassroomId()).getRoomCapacity()) {
            logger.error("checkClassroomCapacity failed: lesson = {}", lesson);
            throw new ClassroomCapacityException("This classroom doesn't fit all student studying this course");
        }
    }

    public void checkConditions(LessonDto lesson) {
        List<Lesson> lessonsWithTemporalCollision = checkDateAndTimeCollision(lesson);
        if (!lessonsWithTemporalCollision.isEmpty()) {
            if (checkClassroom(lessonsWithTemporalCollision, lesson)) {
                logger.error("checkTemporalConditions failed: lesson = {}", lesson);
                throw new InvalidLessonConditionsException("This classroom is occupied during this time");
            } else if (checkTeacher(lessonsWithTemporalCollision, lesson)) {
                throw new InvalidLessonConditionsException("This teacher is busy during this time");
            }
        }
    }

    private List<Lesson> checkDateAndTimeCollision(LessonDto lesson) {
        return serviceLesson.getLessonsByTemporalConditions(
                lesson.getDate(), lesson.getStartTime()
                        .minusMinutes(1)
                , lesson.getEndTime()
                        .plusMinutes(1)
        );
    }

    private boolean checkClassroom(List<Lesson> checkedLessons, LessonDto lesson) {
        return checkedLessons.stream()
                .map(Lesson::getClassroom)
                .map(Classroom::getId)
                .collect(Collectors.toList())
                .contains(lesson.getClassroomId());
    }

    private boolean checkTeacher(List<Lesson> checkedLessons, LessonDto lesson) {
        return checkedLessons.stream()
                .map(Lesson::getTeacher)
                .map(Teacher::getId)
                .collect(Collectors.toList())
                .contains(lesson.getTeacherId());
    }
}
