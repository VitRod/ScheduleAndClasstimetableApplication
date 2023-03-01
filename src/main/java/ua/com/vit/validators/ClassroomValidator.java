package ua.com.vit.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.com.vit.exceptions.IndelibleEntityException;
import ua.com.vit.domain.dto.ClassroomDto;
import ua.com.vit.domain.dto.LessonDto;
import ua.com.vit.service.LessonService;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
public class ClassroomValidator {

    private final LessonService serviceLesson;
    private final Logger logger = LoggerFactory.getLogger(BuildingValidator.class);

    public ClassroomValidator(LessonService serviceLesson) {
        this.serviceLesson = serviceLesson;
    }

    public void checkForDeletion(ClassroomDto classroom, HttpServletRequest request) {
        boolean undeletable = serviceLesson.getAllAsDto()
                .stream()
                .map(LessonDto::getClassroomId)
                .collect(Collectors.toList())
                .contains(classroom.getId());
        if (undeletable) {
            logger.error("checkForDeletion failed: classroom = {}", classroom);
            throw new IndelibleEntityException
                    ("The classroom cannot be deleted because still there are lessons which use it",
                            request.getHeader("Referer"));
        }
    }
}
