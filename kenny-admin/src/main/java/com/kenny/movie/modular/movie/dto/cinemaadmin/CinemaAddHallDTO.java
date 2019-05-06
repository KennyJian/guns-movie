package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaAddHallDTO implements Serializable {

    private String uuid;
    private String oldHall;
    private String hallId;
}
