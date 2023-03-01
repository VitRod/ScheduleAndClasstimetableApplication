package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.domain.dto.TeacherDto;
import ua.com.vit.service.LessonService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class TeacherValidator {

    private final LessonService serviceLesson;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public TeacherValidator(LessonService serviceLesson) {
        this.serviceLesson = serviceLesson;
    }

    public void checkForDeletion(TeacherDto teacherDto, HttpServletRequest request) {
        boolean undeletable = serviceLesson.getAllAsDto()
                .stream()
                .map(LessonDto::getTeacherId)
                .distinct()
                .collect(Collectors.toList())
                .contains(teacherDto.getId());
        if (undeletable) {
            logger.error("checkForDeletion failed: teacher = {}", teacherDto);
            throw new IndelibleEntityException
                    ("The teacher cannot be deleted because still there are lessons which he teaches",
                            request.getHeader("Referer"));
        }
    }
}
