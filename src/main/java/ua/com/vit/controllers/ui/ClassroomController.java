package ua.com.vit.controllers.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.repository.entities.Classroom;
import ua.com.vit.service.BuildingService;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.validators.ClassroomValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Validated
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomValidator validatorClassroom;

    private final ClassroomService serviceClassroom;
    private final BuildingService serviceBuilding;

    public ClassroomController(ClassroomService serviceClassroom, BuildingService serviceBuilding) {
        this.serviceClassroom = serviceClassroom;
        this.serviceBuilding = serviceBuilding;
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("classrooms", serviceClassroom.getAllAsDto());
        model.addAttribute("buildings", serviceBuilding);
        return "classrooms/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") @Min(0) int id,
                           ModelMap model) {
        model.addAttribute("classroom", serviceClassroom.getByIdAsDto(id)).
                addAttribute("buildings", serviceBuilding.getAll()).
                addAttribute("classroomTypes", getClassroomTypes());
        return "classrooms/showById";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("classroom") @NotNull ClassroomDto classroom,
                         ModelMap model) {
        model.addAttribute("classroomTypes", getClassroomTypes())
                .addAttribute("buildings", serviceBuilding.getAll());
        return "classrooms/create";
    }

    @PostMapping()
    public String addToDB(@Valid @ModelAttribute("classroom") ClassroomDto classroom) {
        serviceClassroom.createUseDto(classroom);
        return "redirect:/classrooms";
    }

    @PutMapping("/{id}")
    public String update(@Valid @ModelAttribute("classroom") ClassroomDto classroom,
                         ModelMap model) {
        serviceClassroom.updateUseDto(classroom);
        return "redirect:/classrooms";
    }

    @DeleteMapping("/{id}")
    public String delete(@NotNull @ModelAttribute("classroom") ClassroomDto classroom, HttpServletRequest request) {
        validatorClassroom.checkForDeletion(classroom, request);
        serviceClassroom.deleteUseDto(classroom);
        return "redirect:/classrooms";
    }

    @ExceptionHandler({IndelibleEntityException.class})
    public ModelAndView handleException(IndelibleEntityException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String thrownOutUrl = exception.getThrownOutUrl();
        if (thrownOutUrl.contains("classrooms/")) {
            modelAndView.addObject("classroom",
                    serviceClassroom.getByIdAsDto(Integer.parseInt(thrownOutUrl.substring(thrownOutUrl.length() - 1))));
            modelAndView.addObject("buildings", serviceBuilding.getAll());
            modelAndView.addObject("classroomTypes", getClassroomTypes());
            modelAndView.setViewName("classrooms/showById");
        } else {
            modelAndView.addObject("classrooms", serviceClassroom.getAllAsDto());
            modelAndView.addObject("buildings", serviceBuilding);
            modelAndView.setViewName("classrooms/showAll");
        }
        return modelAndView;
    }

    private List<String> getClassroomTypes() {
        return serviceClassroom.getAll()
                .stream()
                .map(Classroom::getRoomType)
                .distinct()
                .collect(Collectors.toList());
    }
}
