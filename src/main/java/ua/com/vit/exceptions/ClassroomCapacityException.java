package ua.com.vit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ClassroomCapacityException extends CommonCustomException {

    public ClassroomCapacityException() {
    }

    public ClassroomCapacityException(String message) {
        super(message);
    }

    public ClassroomCapacityException(String message, String thrownOutUrl) {
        super(message, thrownOutUrl);
    }
}
