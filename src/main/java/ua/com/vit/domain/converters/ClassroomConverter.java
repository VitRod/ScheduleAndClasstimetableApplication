package ua.com.vit.domain.converters;

import org.springframework.stereotype.Component;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.repository.dao.BuildingRepository;
import ua.com.vit.repository.entities.Classroom;

@Component
public class ClassroomConverter {

    private final BuildingRepository buildingRepository;

    public ClassroomConverter(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public ClassroomDto toDto(Classroom classroom) {
        ClassroomDto dto = new ClassroomDto();
        dto.setId(classroom.getId());
        dto.setRoomName(classroom.getRoomName());
        dto.setRoomType(classroom.getRoomType());
        dto.setRoomCapacity(classroom.getRoomCapacity());
        dto.setBuildingId(classroom.getBuilding().getId());
        return dto;
    }

    public Classroom toEntity(ClassroomDto dto) {
        Classroom classroom = new Classroom();
        classroom.setId(dto.getId());
        classroom.setRoomName(dto.getRoomName());
        classroom.setRoomType(dto.getRoomType());
        classroom.setRoomCapacity(dto.getRoomCapacity());
        classroom.setBuilding(buildingRepository.findById(dto.getBuildingId()).orElse(null));
        return classroom;
    }

}
