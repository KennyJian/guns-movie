package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateCatDTO implements Serializable {

    private String uuid;
    private String filmOldCats;
    private String filmNowCats;
}
