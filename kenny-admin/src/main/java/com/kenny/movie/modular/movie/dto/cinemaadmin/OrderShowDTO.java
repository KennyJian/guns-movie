package com.kenny.movie.modular.movie.dto.cinemaadmin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderShowDTO implements Serializable {

    /**
     * 主键编号
     */
    private String uuid;
    /**
     * 影院编号
     */
    private String cinemaName;
    /**
     * 放映场次编号
     */
    private Integer fieldId;
    /**
     * 电影编号
     */
    private String filmName;
    /**
     * 已售座位名称
     */
    private String seatsName;
    /**
     * 订单总金额
     */
    private Double orderPrice;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 下单人
     */
    private String orderName;
    /**
     * 0-待支付 1-已支付 2-已关闭
     */
    private String orderStatus;
}
