package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.StudentDto;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.repository.entities.Faculty;
import ua.com.vit.service.StudentService;
import ua.com.vit.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class FacultyValidator {

    private final TeacherService serviceTeacher;
    private final StudentService serviceStudent;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public FacultyValidator(TeacherService serviceTeacher, StudentService serviceStudent) {
        this.serviceTeacher = serviceTeacher;
        this.serviceStudent = serviceStudent;
    }

    public boolean checkForDeletion(Faculty faculty, HttpServletRequest request) {
        if (checkForTeacherUsing(faculty)) {
            logger.error("checkForDeletion failed: faculty = {}", faculty);
            throw new IndelibleEntityException
                    ("The faculty cannot be deleted because still there are teachers which use it",
                            request.getHeader("Referer"));
        }
        if (checkForStudentUsing(faculty)) {
            logger.error("checkForDeletion failed: faculty = {}", faculty);
            throw new IndelibleEntityException
                    ("The faculty cannot be deleted because still there are students which use it",
                            request.getHeader("Referer"));
        } else {
            return true;
        }
    }

    private boolean checkForTeacherUsing(Faculty faculty) {
        return serviceTeacher.getAllAsDto()
                .stream()
                .map(TeacherDto::getFacultyId)
                .distinct()
                .collect(Collectors.toList())
                .contains(faculty.getId());
    }

    private boolean checkForStudentUsing(Faculty faculty) {
        return serviceStudent.getAllAsDto()
                .stream()
                .map(StudentDto::getFacultyId)
                .distinct()
                .collect(Collectors.toList())
                .contains(faculty.getId());
    }

}
