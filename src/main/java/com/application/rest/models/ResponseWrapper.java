package com.application.rest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseWrapper {

    private boolean success;
    
    @JsonInclude(Include.NON_EMPTY)
    private String message;
    
    @JsonInclude(Include.NON_EMPTY)
    private Object data;
    
    @JsonInclude(Include.NON_EMPTY)
    private Object error;

}
