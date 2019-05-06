package com.kenny.movie.modular.movie.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddActorDTO implements Serializable {

    private String uuid;
    private String actorName;
    private String actorRole;
    private String actorImgUrl;
}
