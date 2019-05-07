package com.kenny.movie.modular.movie.service.cinemaadmin;

import com.baomidou.mybatisplus.service.IService;
import com.kenny.movie.modular.movie.dto.cinemaadmin.FieldShowDTO;
import com.kenny.movie.modular.movie.dto.cinemaadmin.UpdatePriceDTO;
import com.kenny.movie.modular.system.model.CinemaT;
import com.kenny.movie.modular.system.model.FieldT;
import com.kenny.movie.modular.system.model.HallDictT;
import com.kenny.movie.modular.system.model.HallFilmInfoT;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 放映场次表 服务类
 * </p>
 *
 * @author kenny
 * @since 2019-02-09
 */
public interface IFieldTService extends IService<FieldT> {

    List<Integer> getCinemaIdListByBrandId(Integer brandId);

    Integer getCinemaIdByCinemaName(String cinemaName);

    List<Integer> getCinemaIdListByCinemaName(String cinemaName);

    String getCinemaNameByCinemaId(Integer cinemaId);

    Integer getFilmIdByFilmName(String filmName);

    String getFilmNameByFilmId(Integer filmId);

    String getHallNameByHallId(Integer hallId);

    List<FieldShowDTO> getFieldShowDTOByFieldList(List<FieldT> fieldTList);

    List<CinemaT> getAllCinemaInBrands(Integer brandId);

    List<HallFilmInfoT> getAllHallFilm();

    List<HallDictT> getAllHallDict();

    List<HallDictT> getHallDictByCinemaId(Integer cinemaId);

    boolean addField(FieldT fieldT);

    boolean updatePrice(UpdatePriceDTO updatePriceDTO);


}
