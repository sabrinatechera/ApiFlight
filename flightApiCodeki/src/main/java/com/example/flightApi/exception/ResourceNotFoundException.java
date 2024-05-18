package com.example.flightApi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends IllegalArgumentException {

    private String resourceName;
    private String field;
    private Long value;


    public ResourceNotFoundException(String resourceName, String field, Long value) {
        super(String.format("%s not found with : %s,'%s'", resourceName, field, value));
        this.resourceName = resourceName;
        this.field = field;
        this.value = value;
    }


}
