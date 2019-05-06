package com.kenny.movie.modular.movie.dto.cinemaadmin;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaShowDTO implements Serializable {

    private Integer uuid;
    /**
     * 影院名称
     */
    private String cinemaName;
    /**
     * 影院电话
     */
    private String cinemaPhone;
    /**
     * 地域编号
     */
    private String areaId;
    /**
     * 包含的影厅类型,以#作为分割
     */
    private String hallIds;
    /**
     * 影院地址
     */
    private String cinemaAddress;
    /**
     * 最低票价
     */
    private Integer minimumPrice;
}
