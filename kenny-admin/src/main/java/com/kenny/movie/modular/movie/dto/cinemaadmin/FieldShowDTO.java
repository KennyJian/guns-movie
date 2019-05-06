package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FieldShowDTO implements Serializable {

    private Integer uuid;
    private String cinemaName;
    private String filmName;
    private String beginTime;
    private String endTime;
    private String hallType;
    private String hallName;
    private Integer price;
    private Date beginData;
}
