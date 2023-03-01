package ua.com.vit.controllers.ui.handler;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionsHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";
    private static final String DEFAULT_ADDRESS_PART = "http://localhost:8080";

    @ExceptionHandler({BindException.class})
    public ModelAndView handleBindException(HttpServletRequest request, BindException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String previousPage = request.getHeader("Referer").substring(
                request.getHeader("Referer").indexOf(DEFAULT_ADDRESS_PART) + DEFAULT_ADDRESS_PART.length());
        modelAndView.addObject("previousPage", previousPage);
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ModelAndView handleConstraintViolationException(
            HttpServletRequest request, ConstraintViolationException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", exception.getMessage());
        String previousPage = request.getHeader("Referer").substring(
                request.getHeader("Referer").indexOf(DEFAULT_ADDRESS_PART) + DEFAULT_ADDRESS_PART.length());
        modelAndView.addObject("previousPage", previousPage);
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        return modelAndView;
    }
}
