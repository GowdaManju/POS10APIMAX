package com.Pos10Max.POS10APIMAX.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.IM_USED)
public class ResourceAlreadyExists extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
