package com.kenny.movie.modular.movie.service.admin.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kenny.movie.core.util.ToolUtil;
import com.kenny.movie.modular.movie.dto.admin.*;
import com.kenny.movie.modular.movie.service.admin.IFilmTService;
import com.kenny.movie.modular.system.dao.*;
import com.kenny.movie.modular.system.model.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 影片主表 服务实现类
 * </p>
 *
 * @author kenny
 * @since 2019-02-05
 */
@Service
public class FilmTServiceImpl extends ServiceImpl<FilmTMapper, FilmT> implements IFilmTService {

    @Autowired
    private FilmTMapper filmTMapper;

    @Autowired
    private HallFilmInfoTMapper hallFilmInfoTMapper;

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;

    @Autowired
    private CatDictTMapper catDictTMapper;

    @Autowired
    private ActorTMapper actorTMapper;

    @Autowired
    private FilmActorTMapper filmActorTMapper;

    @Override
    public List<AdminShowDTO> findAllFilmList(String filmName, String filmType, String filmSource, String filmArea, String filmDate) {
        return filmTMapper.findAllFilm(filmName,filmType,filmSource,filmArea,filmDate);
    }

    @Transactional
    @Override
    public boolean insertFilm(AdminShowDTO adminShowDTO) {
        //组织cinema_hall_film_info_t
        HallFilmInfoT hallFilmInfoT=new HallFilmInfoT();
        hallFilmInfoT.setFilmName(adminShowDTO.getFilmName());
        hallFilmInfoT.setFilmLength(adminShowDTO.getFilmLength());
        hallFilmInfoT.setFilmLanguage(adminShowDTO.getFilmLanguage());
        //组织kenny_film_t
        FilmT filmT=new FilmT();
        filmT.setFilmName(adminShowDTO.getFilmName());
        filmT.setFilmType(Integer.parseInt(adminShowDTO.getFilmType()));
        filmT.setFilmSource(Integer.parseInt(adminShowDTO.getFilmSource()));
        filmT.setFilmArea(Integer.parseInt(adminShowDTO.getFilmArea()));
        filmT.setFilmDate(Integer.parseInt(adminShowDTO.getFilmDate()));
        filmT.setFilmTime(adminShowDTO.getFilmTime());
        filmT.setFilmStatus(2);
        //组织kenny_film_info_t
        FilmInfoT filmInfoT=new FilmInfoT();
        filmInfoT.setFilmEnName(adminShowDTO.getFilmEnName());
        filmInfoT.setFilmLength(Integer.parseInt(adminShowDTO.getFilmLength()));

        //插入kenny_film_t
        filmTMapper.insert(filmT);
        FilmT newFilmT=filmTMapper.selectOne(filmT);

        hallFilmInfoT.setFilmId(newFilmT.getUuid());
        filmInfoT.setFilmId(newFilmT.getUuid().toString());

        //插入cinema_hall_film_info_t
        hallFilmInfoTMapper.insert(hallFilmInfoT);
        //插入kenny_film_info_t
        filmInfoTMapper.insert(filmInfoT);
        return true;
    }

    @Transactional
    @Override
    public boolean deleteFilm(Integer id) {
        filmTMapper.deleteById(id);
        HallFilmInfoT hallFilmInfoT=new HallFilmInfoT();
        hallFilmInfoT.setFilmId(id);
        FilmInfoT filmInfoT=new FilmInfoT();
        filmInfoT.setFilmId(id.toString());
        hallFilmInfoTMapper.deleteById(hallFilmInfoTMapper.selectOne(hallFilmInfoT).getUuid());
        filmInfoTMapper.deleteById(filmInfoTMapper.selectOne(filmInfoT).getUuid());
        return true;
    }

    @Override
    public FilmInfoT getFilmInfoTByFilmId(String filmId){
        FilmInfoT filmInfoT=new FilmInfoT();
        filmInfoT.setFilmId(filmId);
        return filmInfoTMapper.selectOne(filmInfoT);
    }

    @Override
    public HallFilmInfoT getHallFilmInfoTByFilmId(String filmId) {
        HallFilmInfoT hallFilmInfoT=new HallFilmInfoT();
        hallFilmInfoT.setFilmId(Integer.parseInt(filmId));
        return hallFilmInfoTMapper.selectOne(hallFilmInfoT);
    }

    @Override
    public ActorT getActorByActorId(Integer actorId) {
        return actorTMapper.selectById(actorId);
    }

    @Transactional
    @Override
    public boolean updateCat(UpdateCatDTO updateCatDTO) {
        //根据catId获取cat名字
        CatDictT catDictT=new CatDictT();
        catDictT.setUuid(Integer.parseInt(updateCatDTO.getFilmNowCats()));
        String catName=catDictTMapper.selectOne(catDictT).getShowName();
        //更新cinema_hall_film_info_t表
        HallFilmInfoT hallFilmInfoT=getHallFilmInfoTByFilmId(updateCatDTO.getUuid());
        if (ToolUtil.isEmpty(hallFilmInfoT.getFilmCats())){
            hallFilmInfoT.setFilmCats(catName);
        }else {
            StringBuilder stringBuilder=new StringBuilder(hallFilmInfoT.getFilmCats());
            stringBuilder.append(","+catName);
            hallFilmInfoT.setFilmCats(stringBuilder.toString());
        }
        hallFilmInfoTMapper.updateById(hallFilmInfoT);
        //更新kenny_film_t
        FilmT filmT=filmTMapper.selectById(Integer.parseInt(updateCatDTO.getUuid()));
        if (ToolUtil.isEmpty(filmT.getFilmCats())){
            filmT.setFilmCats("#"+updateCatDTO.getFilmNowCats()+"#");
        }else {
            StringBuilder stringBuilder=new StringBuilder(filmT.getFilmCats());
            stringBuilder.append(updateCatDTO.getFilmNowCats()+"#");
            filmT.setFilmCats(stringBuilder.toString());
        }
        filmTMapper.updateById(filmT);
        return true;
    }

    @Transactional
    @Override
    public boolean addImg(AddImgDTO addImgDTO) {
        //组织cinema_hall_film_info_t
        HallFilmInfoT hallFilmInfoT=getHallFilmInfoTByFilmId(addImgDTO.getUuid());
        hallFilmInfoT.setImgAddress(addImgDTO.getImg01());
        hallFilmInfoTMapper.updateById(hallFilmInfoT);

        //组织kenny_film_t
        FilmT filmT=filmTMapper.selectById(addImgDTO.getUuid());
        filmT.setImgAddress(addImgDTO.getImg01());
        filmTMapper.updateById(filmT);
        //组织kenny_film_info_t
        FilmInfoT filmInfoT=getFilmInfoTByFilmId(addImgDTO.getUuid());
        filmInfoT.setFilmImgs(addImgDTO.getImg01()+","+addImgDTO.getImg02()+","+addImgDTO.getImg03()+","+addImgDTO.getImg04()+","+addImgDTO.getImg05());
        filmInfoTMapper.updateById(filmInfoT);
        return true;
    }

    @Transactional
    @Override
    public boolean addDirector(AddDirectorDTO addDirectorDTO) {
        //添加到演员表
        ActorT actorT=new ActorT();
        actorT.setActorName(addDirectorDTO.getDirectorName());
        actorT.setActorImg(addDirectorDTO.getDirectorImgUrl());
        actorTMapper.insert(actorT);
        ActorT newActor=actorTMapper.selectOne(actorT);

        //为电影设置导演id
        FilmInfoT filmInfoT=getFilmInfoTByFilmId(addDirectorDTO.getUuid());
        filmInfoT.setDirectorId(newActor.getUuid());
        filmInfoTMapper.updateById(filmInfoT);
        return true;
    }

    @Transactional
    @Override
    public boolean addActor(AddActorDTO addActorDTO) {
        //将演员新增到演员表 kenny_actor_t
        ActorT actorT=new ActorT();
        actorT.setActorName(addActorDTO.getActorName());
        actorT.setActorImg(addActorDTO.getActorImgUrl());
        actorTMapper.insert(actorT);
        ActorT newActor=actorTMapper.selectOne(actorT);
        //将演员新增到演员角色表 kenny_film_actor_t
        FilmActorT filmActorT=new FilmActorT();
        filmActorT.setFilmId(Integer.parseInt(addActorDTO.getUuid()));
        filmActorT.setActorId(newActor.getUuid());
        filmActorT.setRoleName(addActorDTO.getActorRole());
        filmActorTMapper.insert(filmActorT);
        //将演员新增到影厅电影信息表 主演最多为三位
        HallFilmInfoT hallFilmInfoT=getHallFilmInfoTByFilmId(addActorDTO.getUuid());
        if (ToolUtil.isEmpty(hallFilmInfoT.getActors())){
            hallFilmInfoT.setActors(addActorDTO.getActorName());
        }else if (hallFilmInfoT.getActors().split(",").length<3){
            StringBuilder stringBuilder=new StringBuilder(hallFilmInfoT.getActors());
            stringBuilder.append(","+addActorDTO.getActorName());
            hallFilmInfoT.setActors(stringBuilder.toString());
        }
        hallFilmInfoTMapper.updateById(hallFilmInfoT);
        return true;
    }

    @Transactional
    @Override
    public boolean updateStory(UpdateStoryDTO updateStoryDTO) {
        FilmInfoT realFilm=getFilmInfoTByFilmId(updateStoryDTO.getUuid());
        realFilm.setBiography(updateStoryDTO.getStory());
        filmInfoTMapper.updateById(realFilm);
        return true;
    }
}
