package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddActorsDTO implements Serializable {

    private String directorName;
    private String directorImgUrl;
    
}
