package com.kenny.movie.modular.movie.service.admin.impl;

import com.kenny.movie.modular.system.model.FilmActorT;
import com.kenny.movie.modular.system.dao.FilmActorTMapper;
import com.kenny.movie.modular.movie.service.admin.IFilmActorTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 影片与演员映射表 服务实现类
 * </p>
 *
 * @author kenny
 * @since 2019-05-07
 */
@Service
public class FilmActorTServiceImpl extends ServiceImpl<FilmActorTMapper, FilmActorT> implements IFilmActorTService {

}
