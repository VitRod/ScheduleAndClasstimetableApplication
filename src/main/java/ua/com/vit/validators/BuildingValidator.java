package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Building;
import ua.com.vit.repository.entities.Classroom;
import ua.com.vit.service.ClassroomService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class BuildingValidator {

    private final ClassroomService serviceClassroom;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public BuildingValidator(ClassroomService serviceClassroom) {
        this.serviceClassroom = serviceClassroom;
    }

    public void checkForDeletion(Building building, HttpServletRequest request) {
        boolean undeletable = serviceClassroom.getAll()
                .stream()
                .map(Classroom::getBuilding)
                .map(Building::getId)
                .distinct()
                .collect(Collectors.toList())
                .contains(building.getId());
        if (undeletable) {
            logger.error("checkForDeletion failed: building = {}", building);
            throw new IndelibleEntityException(
                    "The building cannot be deleted because it still contains classrooms that weren't deleted",
                    request.getHeader("Referer"));
        }
    }
}
