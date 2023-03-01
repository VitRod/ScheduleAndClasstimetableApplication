package ua.com.vit.domain.converters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.FacultyRepository;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.repository.entities.Teacher;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class TeacherConverterTest {


    @InjectMocks
    private TeacherConverter teacherConverterMock;

    @Mock
    private FacultyRepository facultyRepositoryMock;

    @Mock
    private CourseRepository courseRepositoryMock;

    @Test
    public void should_returnDto_when_argumentOfMethodIsEntity() {

        Set<Course> courses = new HashSet<>();
        Course course1 = new Course();
        course1.setId(1);
        courses.add(course1);
        Course course2 = new Course();
        course2.setId(2);
        courses.add(course2);
        Faculty faculty = new Faculty();
        faculty.setId(1);
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("firstName");
        teacher.setLastName("lastName");
        teacher.setFaculty(faculty);
        teacher.setTeacherCourses(courses);
        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(1);
        coursesId.add(2);
        TeacherDto dto = new TeacherDto();
        dto.setId(1);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(1);
        dto.setCoursesId(coursesId);

        Assert.assertEquals(dto, teacherConverterMock.toDto(teacher));
    }

    @Test
    public void should_returnEntity_when_argumentOfMethodIsDto() {

        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(3);
        coursesId.add(4);
        TeacherDto dto = new TeacherDto();
        dto.setId(2);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(2);
        dto.setCoursesId(coursesId);
        Set<Course> courses = new HashSet<>();
        courses.add(courseRepositoryMock.findById(3).orElse(null));
        courses.add(courseRepositoryMock.findById(4).orElse(null));
        Teacher teacher = new Teacher();
        teacher.setId(2);
        teacher.setFirstName("firstName");
        teacher.setLastName("lastName");
        teacher.setFaculty(facultyRepositoryMock.findById(2).orElse(null));
        teacher.setTeacherCourses(courses);

        Assert.assertEquals(teacher, teacherConverterMock.toEntity(dto));
    }

    @Test
    public void should_callToEntityMethodInDaoClass_when_converterClassCallAppropriateMethod() {

        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(1);
        coursesId.add(4);
        TeacherDto dto = new TeacherDto();
        dto.setId(3);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(3);
        dto.setCoursesId(coursesId);

        teacherConverterMock.toEntity(dto);

        Mockito.verify(facultyRepositoryMock).findById(dto.getFacultyId());
        Mockito.verify(courseRepositoryMock).findById(1);
        Mockito.verify(courseRepositoryMock).findById(4);
    }

}
