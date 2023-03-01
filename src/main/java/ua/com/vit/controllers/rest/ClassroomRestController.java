package ua.com.vit.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.validators.ClassroomValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rest/v1/classrooms")
public class ClassroomRestController {

    @Autowired
    private ClassroomValidator validatorClassroom;

    private final ClassroomService serviceClassroom;

    public ClassroomRestController(ClassroomService serviceClassroom) {
        this.serviceClassroom = serviceClassroom;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClassroomDto> getAll() {
        return serviceClassroom.getAllAsDto();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClassroomDto getById(@PathVariable("id") @Min(0) int id) {
        return serviceClassroom.getByIdAsDto(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ClassroomDto classroomDto) {
        serviceClassroom.createUseDto(classroomDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ClassroomDto classroomDto) {
        serviceClassroom.updateUseDto(classroomDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(0) int id,
                       HttpServletRequest request) {
        validatorClassroom.checkForDeletion(serviceClassroom.getByIdAsDto(id), request);
        serviceClassroom.deleteById(id);
    }

}
