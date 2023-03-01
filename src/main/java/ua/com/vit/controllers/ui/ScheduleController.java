package ua.com.vit.controllers.ui;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.vit.service.ClassroomService;
import ua.com.vit.service.CourseService;
import ua.com.vit.service.StudentService;
import ua.com.vit.service.TeacherService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Controller
@Validated
@RequestMapping("/schedule")
public class ScheduleController {

    private final ClassroomService serviceClassroom;
    private final CourseService serviceCourse;
    private final TeacherService serviceTeacher;
    private final StudentService serviceStudent;

    public ScheduleController(ClassroomService serviceClassroom, CourseService serviceCourse,
                              TeacherService serviceTeacher, StudentService serviceStudent) {
        this.serviceClassroom = serviceClassroom;
        this.serviceCourse = serviceCourse;
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
    }

    @GetMapping()
    public String showForm(ModelMap model) {
        model.addAttribute("teachers", serviceTeacher.getAllAsDto())
                .addAttribute("students", serviceStudent.getAllAsDto());
        return "schedule/fillData";
    }

    @GetMapping("/show")
    public String showSchedule(@NotNull(message = "A person's selection is mandatory!")
                               @RequestParam Object person,
                               @NotNull(message = "The beginning of the date range is mandatory!")
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
                               @NotNull(message = "The end of the date range is mandatory!")
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                               ModelMap model) {
        String id = person.toString().substring(
                person.toString().indexOf("id=") + 3, person.toString().indexOf("id=") + 4);
        if (person.toString().contains("Teacher")) {
            model.addAttribute("schedule",
                    serviceTeacher.receiveLessonsOnDateRange(Integer.parseInt(id), beginDate, endDate));
        } else {
            model.addAttribute("schedule",
                    serviceStudent.receiveLessonsOnDateRange(Integer.parseInt(id), beginDate, endDate));
        }
        model.addAttribute("classrooms", serviceClassroom);
        model.addAttribute("courses", serviceCourse);
        model.addAttribute("teachers", serviceTeacher);
        return "schedule/show";
    }
}
