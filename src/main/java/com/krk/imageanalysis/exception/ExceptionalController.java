package com.krk.imageanalysis.exception;

import com.krk.imageanalysis.dto.CommonResponseBean;
import com.krk.imageanalysis.dto.ErrorBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

@RestControllerAdvice
public class ExceptionalController {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponseBean> handleException(FileNotFoundException fileNotFoundException, HttpRequest request) {
        CommonResponseBean res = new CommonResponseBean();
        res.setTimestamp(new Date().getTime());
        res.setErrors(new ArrayList<>());
        res.getErrors().add(new ErrorBean("E0001",fileNotFoundException.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

    }
}
