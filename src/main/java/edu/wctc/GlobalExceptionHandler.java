package edu.wctc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public String exception(
            HttpServletRequest request,
            Exception exception,
            Model theModel
    ) throws Exception {
        // other logging things here

        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            // template that renders this can't access the SecurityContext
            throw exception;
        }

        // this template can access the SecurityContext
        theModel.addAttribute("errorMessage", "Internal Server Error");
        theModel.addAttribute("exception", exception);

        return "errors";
    }
}
