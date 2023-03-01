package ua.com.vit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.vit.repository.dao.BuildingRepository;
import ua.com.vit.repository.entities.Building;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BuildingServiceTest {

    @InjectMocks
    private BuildingService buildingServiceMock;

    @Mock
    private BuildingRepository buildingRepositoryMock;

    @Test
    public void should_callGetAllMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        buildingServiceMock.getAll();

        Mockito.verify(buildingRepositoryMock).findAll();
    }

    @Test
    public void should_callGetByIdMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Optional<Building> building = Optional.of(new Building());
        int id = 1;

        Building returnedBuilding = buildingServiceMock.getById(id);

        Mockito.verify(buildingRepositoryMock).findById(id);
    }

    @Test
    public void should_callCreateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Building building = new Building();
        building.setBuildingName("D");

        buildingServiceMock.create(building);

        Mockito.verify(buildingRepositoryMock).saveAndFlush(building);
    }

    @Test
    public void should_callUpdateMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Building building = new Building();
        building.setBuildingName("S");

        buildingServiceMock.update(building);

        Mockito.verify(buildingRepositoryMock).saveAndFlush(building);
    }

    @Test
    public void should_callDeleteMethodInDaoClass_when_serviceClassCallsAppropriateMethod() {

        Building building = new Building();
        building.setBuildingName("P");

        buildingServiceMock.create(building);
        buildingServiceMock.delete(building);

        Mockito.verify(buildingRepositoryMock).delete(building);
    }

}
