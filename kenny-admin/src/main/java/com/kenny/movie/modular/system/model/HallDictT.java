package com.kenny.movie.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 地域信息表
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
@TableName("cinema_hall_dict_t")
public class HallDictT extends Model<HallDictT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;
    /**
     * 显示名称
     */
    @TableField("show_name")
    private String showName;
    /**
     * 座位文件存放地址
     */
    @TableField("seat_address")
    private String seatAddress;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSeatAddress() {
        return seatAddress;
    }

    public void setSeatAddress(String seatAddress) {
        this.seatAddress = seatAddress;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "HallDictT{" +
        "uuid=" + uuid +
        ", showName=" + showName +
        ", seatAddress=" + seatAddress +
        "}";
    }
}
