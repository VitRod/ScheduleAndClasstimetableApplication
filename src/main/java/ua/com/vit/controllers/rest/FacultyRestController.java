package ua.com.vit.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.service.FacultyService;
import ua.com.vit.validators.FacultyValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rest/v1/faculties")
public class FacultyRestController {

    @Autowired
    private FacultyValidator validatorFaculty;

    private final FacultyService serviceFaculty;

    public FacultyRestController(FacultyService serviceFaculty) {
        this.serviceFaculty = serviceFaculty;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Faculty> getAll() {
        return serviceFaculty.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Faculty getById(@PathVariable("id") @Min(0) int id) {
        return serviceFaculty.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Faculty faculty) {
        serviceFaculty.create(faculty);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Faculty faculty) {
        serviceFaculty.update(faculty);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(0) int id,
                       HttpServletRequest request) {
        validatorFaculty.checkForDeletion(serviceFaculty.getById(id), request);
        serviceFaculty.deleteById(id);
    }

}
