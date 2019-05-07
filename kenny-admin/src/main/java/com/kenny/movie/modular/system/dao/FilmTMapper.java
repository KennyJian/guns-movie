package com.kenny.movie.modular.system.dao;

import com.kenny.movie.modular.movie.dto.admin.AdminShowDTO;
import com.kenny.movie.modular.system.model.FilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
public interface FilmTMapper extends BaseMapper<FilmT> {

    List<AdminShowDTO> findAllFilm(@Param("filmName") String filmName,@Param("filmType")  String filmType,@Param("filmSource")  String filmSource,@Param("filmArea")  String filmArea,@Param("filmDate")  String filmDate);
}
