package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddImgDTO implements Serializable {

    private String uuid;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
    private String img05;
}
