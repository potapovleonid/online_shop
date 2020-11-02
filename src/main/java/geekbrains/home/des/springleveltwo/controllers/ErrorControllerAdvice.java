package geekbrains.home.des.springleveltwo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception exception, Model model){
        String errorMsg = (exception != null ? exception.getMessage() : "Unknow error");
        model.addAttribute("errorMsg", errorMsg);
        return "error";
    }
}
