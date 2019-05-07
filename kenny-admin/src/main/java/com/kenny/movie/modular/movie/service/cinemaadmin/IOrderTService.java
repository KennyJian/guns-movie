package com.kenny.movie.modular.movie.service.cinemaadmin;

import com.baomidou.mybatisplus.service.IService;
import com.kenny.movie.modular.movie.dto.cinemaadmin.OrderShowDTO;
import com.kenny.movie.modular.system.model.OrderT;

import java.util.List;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author kenny
 * @since 2019-02-10
 */
public interface IOrderTService extends IService<OrderT> {

    List<OrderShowDTO> changeOrderTToOrderShowDTO(List<OrderT> orderTList);

    String getFieldSumOfMoney(String fieldId);
}
