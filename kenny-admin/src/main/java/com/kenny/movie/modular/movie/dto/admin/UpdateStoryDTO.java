package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateStoryDTO implements Serializable {

    private String uuid;

    private String story;
}
