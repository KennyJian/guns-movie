package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePriceDTO implements Serializable {

    private String uuid;
    private Integer price;
}
