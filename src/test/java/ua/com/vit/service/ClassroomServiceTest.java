package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.domain.converters.ClassroomConverter;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.repository.dao.ClassroomRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClassroomServiceTest {

    @InjectMocks
    private ClassroomService classroomServiceMock;

    @Mock
    private ClassroomRepository classroomRepositoryMock;

    @Mock
    private ClassroomConverter classroomConverterMock;

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        classroomServiceMock.getAll();

        Mockito.verify(classroomRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        int id = 4;

        classroomServiceMock.getByIdAsDto(id);

        Mockito.verify(classroomRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        ClassroomDto classroom = new ClassroomDto();
        classroom.setRoomName("C-18");
        classroom.setRoomType("Class");
        classroom.setRoomCapacity(20);
        classroom.setBuildingId(3);

        classroomServiceMock.createUseDto(classroom);

        Mockito.verify(classroomRepositoryMock).saveAndFlush(classroomConverterMock.toEntity(classroom));
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        ClassroomDto classroom = new ClassroomDto();
        classroom.setRoomName("B-50");
        classroom.setRoomType("Lecture");
        classroom.setRoomCapacity(100);
        classroom.setBuildingId(2);

        classroomServiceMock.updateUseDto(classroom);

        Mockito.verify(classroomRepositoryMock).saveAndFlush(classroomConverterMock.toEntity(classroom));
    }

    @Test
    public void should_callDeleteMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        ClassroomDto classroom = new ClassroomDto();
        classroom.setRoomName("A-20");
        classroom.setRoomType("Laboratory");
        classroom.setRoomCapacity(5);
        classroom.setBuildingId(1);

        classroomServiceMock.createUseDto(classroom);
        classroomServiceMock.deleteUseDto(classroom);

        Mockito.verify(classroomRepositoryMock).delete(classroomConverterMock.toEntity(classroom));
    }

}
