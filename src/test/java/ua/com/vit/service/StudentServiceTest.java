package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.converters.LessonConverter;
import ua.com.vit.domain.converters.StudentConverter;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.repository.dao.LessonRepository;
import ua.com.vit.repository.dao.StudentRepository;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentServiceMock;

    @Mock
    private LessonRepository lessonRepositoryMock;

    @Mock
    private StudentRepository studentRepositoryMock;

    @Mock
    private StudentConverter studentConverterMock;

    @Mock
    private LessonConverter lessonConverterMock;

    @Test
    public void should_callReceiveLessonsOnDateRangeMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 7;
        LocalDate beginDate = LocalDate.of(2021, 2, 15);
        LocalDate endDate = LocalDate.of(2021, 2, 21);

        studentServiceMock.receiveLessonsOnDateRange(id, beginDate, endDate)
                .stream()
                .map(lessonConverterMock::toEntity)
                .collect(Collectors.toList());

        Mockito.verify(lessonRepositoryMock).getLessonsForStudentOnDateRange(id, beginDate, endDate);
    }

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        studentServiceMock.getAllAsDto();

        Mockito.verify(studentRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 16;

        studentServiceMock.getByIdAsDto(id);

        Mockito.verify(studentRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        StudentDto student = new StudentDto();
        student.setFirstName("Leo");
        student.setLastName("Leonberg");
        student.setFacultyId(1);

        studentServiceMock.createFromDto(student);

        Mockito.verify(studentRepositoryMock).saveAndFlush(studentConverterMock.toEntity(student));
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        StudentDto student = new StudentDto();
        student.setFirstName("Johan");
        student.setLastName("Bit");
        student.setFacultyId(2);

        studentServiceMock.updateFromDto(student);

        Mockito.verify(studentRepositoryMock).saveAndFlush(studentConverterMock.toEntity(student));
    }

    @Test
    public void should_callDeleteByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        StudentDto student = new StudentDto();
        student.setFirstName("Joe");
        student.setLastName("Dassin");
        student.setFacultyId(1);

        studentServiceMock.createFromDto(student);
        studentServiceMock.deleteById(student.getId());

        Mockito.verify(studentRepositoryMock).deleteById(student.getId());
    }

}
