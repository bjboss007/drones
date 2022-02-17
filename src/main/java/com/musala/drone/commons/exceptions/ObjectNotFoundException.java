package com.musala.drone.commons.exceptions;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
