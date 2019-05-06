package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminShowDTO implements Serializable {

    //cinema_hall_film_info_t
    private Integer uuid;
    private String filmName;
    private String filmLength;
    private String filmLanguage;
    //kenny_film_t
    private String filmType;
    private String filmSource;
    private String filmArea;
    private String filmDate;
    private Date filmTime;
    //kenny_film_info_t
    private String filmEnName;
}
