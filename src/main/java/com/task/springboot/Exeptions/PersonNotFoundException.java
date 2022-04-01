package com.task.springboot.Exeptions;

import org.springframework.http.HttpStatus;


public class PersonNotFoundException extends RuntimeException{

    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus() {return httpStatus;}


    public PersonNotFoundException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus=httpStatus;
    }
}
