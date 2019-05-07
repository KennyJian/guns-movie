package com.kenny.movie.modular.movie.service.cinemaadmin.impl;

import com.kenny.movie.modular.movie.dto.cinemaadmin.OrderShowDTO;
import com.kenny.movie.modular.movie.movieenum.OrderStatusEnum;
import com.kenny.movie.modular.movie.service.cinemaadmin.IFieldTService;
import com.kenny.movie.modular.movie.service.cinemaadmin.IOrderTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kenny.movie.modular.system.dao.CinemaTMapper;
import com.kenny.movie.modular.system.dao.HallFilmInfoTMapper;
import com.kenny.movie.modular.system.dao.OrderTMapper;
import com.kenny.movie.modular.system.dao.UserTMapper;
import com.kenny.movie.modular.system.model.OrderT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author kenny
 * @since 2019-02-10
 */
@Service
public class OrderTServiceImpl extends ServiceImpl<OrderTMapper, OrderT> implements IOrderTService {

    @Autowired
    IFieldTService iFieldTService;

    @Autowired
    private CinemaTMapper cinemaTMapper;

    @Autowired
    private HallFilmInfoTMapper hallFilmInfoTMapper;

    @Autowired
    private UserTMapper userTMapper;

    @Autowired
    private OrderTMapper orderTMapper;

    @Override
    public List<OrderShowDTO> changeOrderTToOrderShowDTO(List<OrderT> orderTList) {
        List<OrderShowDTO> orderShowDTOList=new ArrayList<>();
        for (OrderT orderT:orderTList){
            OrderShowDTO orderShowDTO=new OrderShowDTO();
            BeanUtils.copyProperties(orderT,orderShowDTO);
            orderShowDTO.setCinemaName(iFieldTService.getCinemaNameByCinemaId(orderT.getCinemaId()));
            orderShowDTO.setFilmName(iFieldTService.getFilmNameByFilmId(orderT.getFilmId()));
            orderShowDTO.setOrderName(userTMapper.selectById(orderT.getOrderUser()).getUserName());
            orderShowDTO.setOrderStatus(OrderStatusEnum.getMsg(orderT.getOrderStatus()));
            orderShowDTOList.add(orderShowDTO);
        }
        return orderShowDTOList;
    }

    @Override
    public String getFieldSumOfMoney(String fieldId) {
        return orderTMapper.getFieldSumOfMoney(fieldId);
    }
}
