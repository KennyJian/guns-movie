package com.kenny.movie.modular.movie.service.cinemaadmin;


import com.baomidou.mybatisplus.service.IService;
import com.kenny.movie.modular.movie.dto.cinemaadmin.CinemaAddHallDTO;
import com.kenny.movie.modular.movie.dto.cinemaadmin.CinemaShowDTO;
import com.kenny.movie.modular.system.model.CinemaT;

import java.util.List;

/**
 * <p>
 * 影院信息表 服务类
 * </p>
 *
 * @author kenny
 * @since 2019-02-09
 */
public interface ICinemaTService extends IService<CinemaT> {

    List<CinemaShowDTO> cinemaTToCinemaShowDTO(List<CinemaT> cinemaTList);

    String getOldHall(String cinemaId);

    boolean addHall(CinemaAddHallDTO cinemaAddHallDTO);

}
