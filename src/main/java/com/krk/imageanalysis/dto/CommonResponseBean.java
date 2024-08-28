package com.krk.imageanalysis.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponseBean {
    public long timestamp;
    public List<ErrorBean> errors;
}