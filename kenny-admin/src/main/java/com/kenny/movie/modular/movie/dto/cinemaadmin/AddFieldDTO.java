package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddFieldDTO implements Serializable {

    private String cinemaId;
    private String filmId;
    private String beginTime;
    private String hallId;
    private String hallName;
    private String price;
    private Date beginData;
}
