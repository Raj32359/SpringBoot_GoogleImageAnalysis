package com.krk.imageanalysis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorBean {
    public String  errorCode;
    public String  errorMessage;

    public ErrorBean(String e0001, String string) {
    }
}