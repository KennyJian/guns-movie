package com.kenny.movie.modular.system.dao;

import com.kenny.movie.modular.system.model.OrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
public interface OrderTMapper extends BaseMapper<OrderT> {

    String getFieldSumOfMoney(@Param("fieldId")String fieldId);

}
