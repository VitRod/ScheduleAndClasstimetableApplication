package ua.com.vit.exceptions;

public class IndelibleEntityException extends CommonCustomException {

    public IndelibleEntityException() {
        super();
    }

    public IndelibleEntityException(String message) {
        super(message);
    }

    public IndelibleEntityException(String message, String thrownOutUrl) {
        super(message, thrownOutUrl);
    }
}
