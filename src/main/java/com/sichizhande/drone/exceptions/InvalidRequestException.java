package com.sichizhande.drone.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message){
        super(message);
    }
}
