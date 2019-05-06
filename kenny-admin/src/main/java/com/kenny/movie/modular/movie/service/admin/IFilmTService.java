package com.kenny.movie.modular.movie.service.admin;

import com.baomidou.mybatisplus.service.IService;
import com.kenny.movie.modular.movie.dto.admin.*;
import com.kenny.movie.modular.system.model.ActorT;
import com.kenny.movie.modular.system.model.FilmInfoT;
import com.kenny.movie.modular.system.model.FilmT;
import com.kenny.movie.modular.system.model.HallFilmInfoT;

import java.util.List;

/**
 * <p>
 * 影片主表 服务类
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
public interface IFilmTService extends IService<FilmT> {

    List<AdminShowDTO> findAllFilmList(String filmName, String filmType, String filmSource, String filmArea, String filmDate);

    boolean insertFilm(AdminShowDTO adminShowDTO);

    boolean deleteFilm(Integer id);

    boolean updateStory(UpdateStoryDTO updateStoryDTO);

    FilmInfoT getFilmInfoTByFilmId(String filmId);

    HallFilmInfoT getHallFilmInfoTByFilmId(String filmId);

    ActorT getActorByActorId(Integer actorId);

    boolean updateCat(UpdateCatDTO updateCatDTO);

    boolean addImg(AddImgDTO addImgDTO);

    boolean addDirector(AddDirectorDTO addDirectorDTO);

    boolean addActor(AddActorDTO addActorDTO);
}
