package ua.com.vit.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.vit.repository.entities.Building;
import ua.com.vit.service.BuildingService;
import ua.com.vit.validators.BuildingValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/rest/v1/buildings")
public class BuildingRestController {

    @Autowired
    private BuildingValidator validatorBuilding;

    private final BuildingService serviceBuilding;

    public BuildingRestController(BuildingService buildingService) {
        this.serviceBuilding = buildingService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Building> getAll() {
        return serviceBuilding.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Building getById(@PathVariable("id") @Min(0) int id) {
        return serviceBuilding.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Building building) {
        serviceBuilding.create(building);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Building building) {
        serviceBuilding.update(building);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(0) int id,
                       HttpServletRequest request) {
        validatorBuilding.checkForDeletion(serviceBuilding.getById(id), request);
        serviceBuilding.deleteById(id);
    }

}
