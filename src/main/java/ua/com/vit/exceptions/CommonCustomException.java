package ua.com.vit.exceptions;

public class CommonCustomException extends RuntimeException {

    private String thrownOutUrl;

    public CommonCustomException() {
    }

    public CommonCustomException(String message) {
        super(message);
    }

    public CommonCustomException(String message, String thrownOutUrl) {
        super(message);
        this.thrownOutUrl = thrownOutUrl;
    }

    public String getThrownOutUrl() {
        return thrownOutUrl;
    }

    public void setThrownOutUrl(String thrownOutUrl) {
        this.thrownOutUrl = thrownOutUrl;
    }
}
