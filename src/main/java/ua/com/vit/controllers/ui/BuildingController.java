package ua.com.vit.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.repository.entities.Building;
import ua.com.vit.service.BuildingService;
import ua.com.vit.validators.BuildingValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@Validated
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingValidator validatorBuilding;

    private final BuildingService serviceBuilding;

    public BuildingController(BuildingService serviceBuilding) {
        this.serviceBuilding = serviceBuilding;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("buildings", serviceBuilding.getAll());
        return "buildings/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("building", serviceBuilding.getById(id));
        return "buildings/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("building") @NotNull Building building) {
        return "buildings/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("building") Building building) {
        serviceBuilding.create(building);
        return "redirect:/buildings";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("building") Building building) {
        serviceBuilding.update(building);
        return "redirect:/buildings";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("building") Building building, HttpServletRequest request) {
        validatorBuilding.checkForDeletion(building, request);
        serviceBuilding.delete(building);
        return "redirect:/buildings";
    }

    @ExceptionHandler({IndelibleEntityException.class})
    public ModelAndView handleException(IndelibleEntityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String thrownOutUrl = exception.getThrownOutUrl();
        if (thrownOutUrl.contains("buildings/")) {
            modelAndView.addObject("building",
                            serviceBuilding.getById(
                                    Integer.parseInt(thrownOutUrl.substring(thrownOutUrl.length() - 1))))
                    .setViewName("buildings/showById");
        } else {
            modelAndView.addObject("buildings", serviceBuilding.getAll())
                    .setViewName("buildings/showAll");
        }
        return modelAndView;
    }
}
