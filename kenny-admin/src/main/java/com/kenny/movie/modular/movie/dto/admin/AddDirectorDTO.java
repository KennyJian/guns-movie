package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddDirectorDTO implements Serializable {

    private String uuid;
    private String directorName;
    private String directorImgUrl;

}
