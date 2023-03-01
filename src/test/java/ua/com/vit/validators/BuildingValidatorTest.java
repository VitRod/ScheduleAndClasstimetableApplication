package ua.com.vit.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Building;
import ua.com.vit.service.BuildingService;

import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class BuildingValidatorTest {

    @Mock
    private BuildingValidator buildingValidatorMock;

    @Mock
    private BuildingService buildingServiceMock;

    @Test(expected = IndelibleEntityException.class)
    public void should_throwIndelibleEntityException_when_entityHasDeletionConstraints() {

        Building building = buildingServiceMock.getById(1);
        MockHttpServletRequest request = new MockHttpServletRequest();

        doThrow(IndelibleEntityException.class)
                .when(buildingValidatorMock)
                .checkForDeletion(building, request);

        buildingValidatorMock.checkForDeletion(building, request);
    }

    @Test
    public void should_doNothing_when_entityIsDeletable() {

        Building building = new Building();
        building.setId(8);
        MockHttpServletRequest request = new MockHttpServletRequest();

        buildingValidatorMock.checkForDeletion(building, request);
    }
}
