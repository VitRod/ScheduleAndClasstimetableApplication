package ua.com.vit.domain.converters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.repository.dao.CourseRepository;
import ua.com.vit.repository.dao.FacultyRepository;
import ua.com.vit.repository.entities.Course;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.repository.entities.Student;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class StudentConverterTest {

    @InjectMocks
    private StudentConverter studentConverterMock;

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
        Student student = new Student();
        student.setId(1);
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setFaculty(faculty);
        student.setStudentCourses(courses);
        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(1);
        coursesId.add(2);
        StudentDto dto = new StudentDto();
        dto.setId(1);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(1);
        dto.setCoursesId(coursesId);

        Assert.assertEquals(dto, studentConverterMock.toDto(student));
    }

    @Test
    public void should_returnEntity_when_argumentOfMethodIsDto() {

        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(3);
        coursesId.add(4);
        StudentDto dto = new StudentDto();
        dto.setId(2);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(2);
        dto.setCoursesId(coursesId);
        Set<Course> courses = new HashSet<>();
        courses.add(courseRepositoryMock.findById(3).orElse(null));
        courses.add(courseRepositoryMock.findById(4).orElse(null));
        Student student = new Student();
        student.setId(2);
        student.setFirstName("firstName");
        student.setLastName("lastName");
        student.setFaculty(facultyRepositoryMock.findById(2).orElse(null));
        student.setStudentCourses(courses);

        Assert.assertEquals(student, studentConverterMock.toEntity(dto));
    }

    @Test
    public void should_callToEntityMethodInDaoClass_when_converterClassCallAppropriateMethod() {

        Set<Integer> coursesId = new HashSet<>();
        coursesId.add(1);
        coursesId.add(4);
        StudentDto dto = new StudentDto();
        dto.setId(3);
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setFacultyId(3);
        dto.setCoursesId(coursesId);

        studentConverterMock.toEntity(dto);

        Mockito.verify(facultyRepositoryMock).findById(dto.getFacultyId());
        Mockito.verify(courseRepositoryMock).findById(1);
        Mockito.verify(courseRepositoryMock).findById(4);
    }

}
