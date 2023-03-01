package ua.com.vit.domain.converters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.repository.dao.ClassroomRepository;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.TeacherRepository;
import ua.com.vit.repository.entities.Classroom;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.repository.entities.Lesson;
import ua.com.vit.repository.entities.Teacher;

import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(MockitoJUnitRunner.class)
public class LessonConverterTest {

    @InjectMocks
    private LessonConverter lessonConverterMock;

    @Mock
    private ClassroomRepository classroomRepositoryMock;

    @Mock
    private CourseRepository courseRepositoryMock;

    @Mock
    private TeacherRepository teacherRepositoryMock;

    @Test
    public void should_returnDto_when_argumentOfMethodIsEntity() {

        Classroom classroom = new Classroom();
        classroom.setId(1);
        Course course = new Course();
        course.setId(1);
        Teacher teacher = new Teacher();
        teacher.setId(1);
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setDate(LocalDate.of(2021, 4, 2));
        lesson.setStartTime(LocalTime.of(9, 20));
        lesson.setEndTime(LocalTime.of(11, 0));
        lesson.setClassroom(classroom);
        lesson.setCourse(course);
        lesson.setTeacher(teacher);
        LessonDto dto = new LessonDto();
        dto.setId(1);
        dto.setDate(LocalDate.of(2021, 4, 2));
        dto.setStartTime(LocalTime.of(9, 20));
        dto.setEndTime(LocalTime.of(11, 0));
        dto.setClassroomId(1);
        dto.setCourseId(1);
        dto.setTeacherId(1);

        Assert.assertEquals(dto, lessonConverterMock.toDto(lesson));
    }

    @Test
    public void should_returnEntity_when_argumentOfMethodIsDto() {

        LessonDto dto = new LessonDto();
        dto.setId(4);
        dto.setDate(LocalDate.of(2021, 5, 20));
        dto.setStartTime(LocalTime.of(12, 20));
        dto.setEndTime(LocalTime.of(14, 0));
        dto.setClassroomId(4);
        dto.setCourseId(4);
        dto.setTeacherId(4);
        Lesson lesson = new Lesson();
        lesson.setId(4);
        lesson.setDate(LocalDate.of(2021, 5, 20));
        lesson.setStartTime(LocalTime.of(12, 20));
        lesson.setEndTime(LocalTime.of(14, 0));
        lesson.setClassroom(classroomRepositoryMock.findById(4).orElse(null));
        lesson.setCourse(courseRepositoryMock.findById(4).orElse(null));
        lesson.setTeacher(teacherRepositoryMock.findById(4).orElse(null));

        Assert.assertEquals(lesson, lessonConverterMock.toEntity(dto));
    }

    @Test
    public void should_callToEntityMethodInDaoClass_when_converterClassCallAppropriateMethod() {

        LessonDto dto = new LessonDto();
        dto.setDate(LocalDate.of(2021, 5, 20));
        dto.setStartTime(LocalTime.of(12, 20));
        dto.setEndTime(LocalTime.of(14, 0));
        dto.setClassroomId(1);
        dto.setCourseId(2);
        dto.setTeacherId(3);

        lessonConverterMock.toEntity(dto);

        Mockito.verify(classroomRepositoryMock).findById(dto.getClassroomId());
        Mockito.verify(courseRepositoryMock).findById(dto.getCourseId());
        Mockito.verify(teacherRepositoryMock).findById(dto.getTeacherId());
    }

}
