package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dto.ErrorResponseMsgDTO;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
//aspect
public class GlobalExceptionHandler {
//    @ExceptionHandler(Throwable.class)
//    public ErrorResponseMsgDTO uncaughtExceptions(Throwable t){
//        t.printStackTrace();
//        return new ErrorResponseMsgDTO(t.getMessage(),405);
//    }
//mis recording

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponseMsgDTO validationExceptions(MethodArgumentNotValidException e){
//        String message = e.getFieldErrors().stream().map(err -> err.getField() + ": " + err.getDefaultMessage() + ",")
//                .reduce((prev, cur) -> prev + cur).get();
//        return new ErrorResponseMsgDTO(message,400);
//
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)//point cut
    public Map<String,Object> validationExceptions(MethodArgumentNotValidException exp){//joinpoints

        //advice
        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.BAD_REQUEST.value());
        errAttributes.put("error",HttpStatus.BAD_REQUEST.getReasonPhrase());
        errAttributes.put("timestamp",new Date().toString());
        List<String> validationErrList = exp.getFieldErrors().stream().
                map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList());
        errAttributes.put("errors",validationErrList);
        return errAttributes;

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public Map<String,Object> duplicateEntityExceptionHandler(){

        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.CONFLICT.value());
        errAttributes.put("error",HttpStatus.CONFLICT.getReasonPhrase());
        errAttributes.put("message","Duplicate entity found");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;

    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String,Object> accessDeniedExceptionHandler(){

        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.FORBIDDEN.value());
        errAttributes.put("error",HttpStatus.FORBIDDEN.getReasonPhrase());
        errAttributes.put("message","Access Denied");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public Map<String,Object> EmptyResultDataAccessExceptionHandler(){
        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.NOT_FOUND.value());
        errAttributes.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
        errAttributes.put("message","Entity not found");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;

    }
}


