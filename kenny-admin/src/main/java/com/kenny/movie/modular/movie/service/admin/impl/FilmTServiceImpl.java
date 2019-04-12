package com.kenny.movie.modular.movie.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kenny.movie.modular.movie.service.IFilmTService;
import com.kenny.movie.modular.system.dao.FilmTMapper;
import com.kenny.movie.modular.system.model.FilmT;
import org.springframework.stereotype.Service;

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

}
