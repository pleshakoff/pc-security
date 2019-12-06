package com.parcom.security.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by apleshakov on 17.02.2015.
 */
@Getter
@Setter
@NoArgsConstructor
public class ExceptionResource {

    private String type = "exception";
    private String url;
    private String method;
    private String message;
    private String exceptionClass;
    private String description;

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return   mapper.writeValueAsString(this);
    }

  }