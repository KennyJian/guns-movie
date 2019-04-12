package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaAddHall implements Serializable {

    private String id;
    private String hallId;
}
