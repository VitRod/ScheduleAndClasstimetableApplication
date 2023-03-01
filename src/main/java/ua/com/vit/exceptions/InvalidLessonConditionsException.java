package ua.com.vit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidLessonConditionsException extends CommonCustomException {

    public InvalidLessonConditionsException() {
    }

    public InvalidLessonConditionsException(String message) {
        super(message);
    }

    public InvalidLessonConditionsException(String message, String thrownOutUrl) {
        super(message, thrownOutUrl);
    }
}
