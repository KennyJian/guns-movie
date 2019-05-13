package com.kenny.movie.modular.movie.service.cinemaadmin.impl;


import com.kenny.movie.core.util.ToolUtil;
import com.kenny.movie.modular.movie.dto.cinemaadmin.CinemaAddHallDTO;
import com.kenny.movie.modular.movie.dto.cinemaadmin.CinemaShowDTO;
import com.kenny.movie.modular.movie.exception.AddCatException;
import com.kenny.movie.modular.movie.service.cinemaadmin.ICinemaTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kenny.movie.modular.system.dao.AreaDictTMapper;
import com.kenny.movie.modular.system.dao.CinemaTMapper;
import com.kenny.movie.modular.system.dao.HallDictTMapper;
import com.kenny.movie.modular.system.model.AreaDictT;
import com.kenny.movie.modular.system.model.CinemaT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 影院信息表 服务实现类
 * </p>
 *
 * @author kenny
 * @since 2019-02-09
 */
@Service
public class CinemaTServiceImpl extends ServiceImpl<CinemaTMapper, CinemaT> implements ICinemaTService {

    @Autowired
    private CinemaTMapper cinemaTMapper;

    @Autowired
    private AreaDictTMapper areaDictTMapper;

    @Autowired
    private HallDictTMapper hallDictTMapper;

    private String getChineseHallsByCinemaT(CinemaT cinemaT){
        if (ToolUtil.isNotEmpty(cinemaT.getHallIds())) {
            String[] halls = cinemaT.getHallIds().split("#");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < halls.length; i++) {
                stringBuilder.append(hallDictTMapper.selectById(halls[i]).getShowName());
                if (i + 1 != halls.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public List<CinemaShowDTO> cinemaTToCinemaShowDTO(List<CinemaT> cinemaTList) {
        List<CinemaShowDTO> cinemaShowDTOS=new ArrayList<>();
        for(CinemaT cinemaT:cinemaTList){
            CinemaShowDTO cinemaShowDTO=new CinemaShowDTO();
            BeanUtils.copyProperties(cinemaT,cinemaShowDTO);
            cinemaShowDTO.setAreaId(areaDictTMapper.selectById(cinemaT.getAreaId()).getShowName());
            cinemaShowDTO.setHallIds(getChineseHallsByCinemaT(cinemaT));
            cinemaShowDTOS.add(cinemaShowDTO);
        }
        return cinemaShowDTOS;
    }

    @Override
    public String getOldHall(String cinemaId) {
        return getChineseHallsByCinemaT(cinemaTMapper.selectById(cinemaId));
    }

    @Override
    public boolean addHall(CinemaAddHallDTO cinemaAddHallDTO) {
        CinemaT cinemaT=cinemaTMapper.selectById(cinemaAddHallDTO);
        if (ToolUtil.isEmpty(cinemaT.getHallIds())){
            cinemaT.setHallIds("#"+cinemaAddHallDTO.getHallId()+"#");
        }else {
            //判断是否添加过
            String halls=cinemaT.getHallIds();
            String[] hall=halls.split("#");
            for (String str:hall){
                if (str.equals(cinemaAddHallDTO.getHallId())){
                    throw new AddCatException();
                }
            }
            StringBuilder stringBuilder=new StringBuilder(halls);
            stringBuilder.append(cinemaAddHallDTO.getHallId()+"#");
            cinemaT.setHallIds(stringBuilder.toString());
        }
        cinemaTMapper.updateById(cinemaT);
        return true;
    }
}
