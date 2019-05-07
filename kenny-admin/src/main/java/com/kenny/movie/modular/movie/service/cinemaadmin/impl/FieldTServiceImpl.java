package com.kenny.movie.modular.movie.service.cinemaadmin.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.movie.core.util.ToolUtil;
import com.kenny.movie.modular.movie.dto.cinemaadmin.FieldShowDTO;
import com.kenny.movie.modular.movie.dto.cinemaadmin.UpdatePriceDTO;
import com.kenny.movie.modular.movie.exception.AddFieldException;
import com.kenny.movie.modular.movie.exception.FieldTimeException;
import com.kenny.movie.modular.movie.exception.SetPriceException;
import com.kenny.movie.modular.movie.service.cinemaadmin.IFieldTService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kenny.movie.modular.system.dao.CinemaTMapper;
import com.kenny.movie.modular.system.dao.FieldTMapper;
import com.kenny.movie.modular.system.dao.HallDictTMapper;
import com.kenny.movie.modular.system.dao.HallFilmInfoTMapper;
import com.kenny.movie.modular.system.model.CinemaT;
import com.kenny.movie.modular.system.model.FieldT;
import com.kenny.movie.modular.system.model.HallDictT;
import com.kenny.movie.modular.system.model.HallFilmInfoT;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 放映场次表 服务实现类
 * </p>
 *
 * @author kenny
 * @since 2019-02-09
 */
@Service
public class FieldTServiceImpl extends ServiceImpl<FieldTMapper, FieldT> implements IFieldTService {

    @Autowired
    private CinemaTMapper cinemaTMapper;

    @Autowired
    private HallFilmInfoTMapper hallFilmInfoTMapper;

    @Autowired
    private HallDictTMapper hallDictTMapper;

    @Autowired
    private FieldTMapper fieldTMapper;

    @Override
    public List<Integer> getCinemaIdListByBrandId(Integer brandId) {
        EntityWrapper<CinemaT> cinemaTEntityWrapper=new EntityWrapper<>();
        cinemaTEntityWrapper.eq("brand_id",brandId);
        List<CinemaT> cinemaTList=cinemaTMapper.selectList(cinemaTEntityWrapper);
        List<Integer> idList=new ArrayList<>();
        for (CinemaT cinemaT:cinemaTList){
            Integer id=cinemaT.getUuid();
            idList.add(id);
        }
        return idList;
    }

    @Override
    public Integer getCinemaIdByCinemaName(String cinemaName) {
        CinemaT cinemaT=new CinemaT();
        cinemaT.setCinemaName(cinemaName);
        if (ToolUtil.isNotEmpty(cinemaTMapper.selectOne(cinemaT))){
            return cinemaTMapper.selectOne(cinemaT).getUuid();
        }
        return -1;
    }

    @Override
    public List<Integer> getCinemaIdListByCinemaName(String cinemaName) {
        List<Integer> idList=new ArrayList<>();
        EntityWrapper<CinemaT> entityWrapper=new EntityWrapper<>();
        entityWrapper.like("cinema_name",cinemaName);
        List<CinemaT> cinemaTList=cinemaTMapper.selectList(entityWrapper);
        for(CinemaT cinemaT:cinemaTList){
            idList.add(cinemaT.getUuid());
        }
        return idList;
    }

    @Override
    public String getCinemaNameByCinemaId(Integer cinemaId) {
        return cinemaTMapper.selectById(cinemaId).getCinemaName();
    }

    @Override
    public Integer getFilmIdByFilmName(String filmName) {
        HallFilmInfoT hallFilmInfoT=new HallFilmInfoT();
        hallFilmInfoT.setFilmName(filmName);
        if (ToolUtil.isNotEmpty(hallFilmInfoTMapper.selectOne(hallFilmInfoT))){
            return hallFilmInfoTMapper.selectOne(hallFilmInfoT).getUuid();
        }
        return -1;
    }

    @Override
    public String getFilmNameByFilmId(Integer filmId) {
        return hallFilmInfoTMapper.selectById(filmId).getFilmName();
    }

    @Override
    public String getHallNameByHallId(Integer hallId) {
        return hallDictTMapper.selectById(hallId).getShowName();
    }

    @Override
    public List<FieldShowDTO> getFieldShowDTOByFieldList(List<FieldT> fieldTList) {
        List<FieldShowDTO> fieldShowDTOList=new ArrayList<>();
        for (FieldT fieldT:fieldTList){
            FieldShowDTO fieldShowDTO=new FieldShowDTO();
            BeanUtils.copyProperties(fieldT,fieldShowDTO);
            fieldShowDTO.setCinemaName(getCinemaNameByCinemaId(fieldT.getCinemaId()));
            fieldShowDTO.setFilmName(getFilmNameByFilmId(fieldT.getFilmId()));
            fieldShowDTO.setHallType(getHallNameByHallId(fieldT.getHallId()));
            fieldShowDTOList.add(fieldShowDTO);
        }
        return fieldShowDTOList;
    }

    @Override
    public List<CinemaT> getAllCinemaInBrands(Integer brandId) {
        EntityWrapper<CinemaT> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("brand_id",brandId);
        return cinemaTMapper.selectList(entityWrapper);
    }

    @Override
    public List<HallFilmInfoT> getAllHallFilm() {
        return hallFilmInfoTMapper.selectList(null);
    }

    @Override
    public List<HallDictT> getAllHallDict() {
        return hallDictTMapper.selectList(null);
    }

    @Override
    public List<HallDictT> getHallDictByCinemaId(Integer cinemaId) {
        List<HallDictT> hallDictTList=new ArrayList<>();
        if (ToolUtil.isNotEmpty(cinemaTMapper.selectById(cinemaId).getHallIds())){
            String[] halls=cinemaTMapper.selectById(cinemaId).getHallIds().split("#");
            for (int i=1;i<halls.length;i++){
                HallDictT hallDictT=hallDictTMapper.selectById(halls[i]);
                hallDictTList.add(hallDictT);
            }

        }
        return hallDictTList;
    }

    private Integer getFilmLengthByFilmId(Integer filmId){
        return Integer.parseInt(hallFilmInfoTMapper.selectById(filmId).getFilmLength());
    }

    private Integer getMinPriceByCinemaId(Integer cinemaId){
        return cinemaTMapper.selectById(cinemaId).getMinimumPrice();
    }

    @Override
    public boolean addField(FieldT fieldT) {
        if (fieldT.getBeginData().before(new Date())){
            throw new FieldTimeException();
        }

        if (fieldT.getPrice()<getMinPriceByCinemaId(fieldT.getCinemaId())){
            throw new SetPriceException();
        }
        //组织待插入对象
        String beginFormatDate=new SimpleDateFormat("yyyy/MM/dd").format(fieldT.getBeginData());
        String begin=fieldT.getBeginTime();
        Date beginTime=new Date(beginFormatDate+" "+begin);
        Integer filmLength=getFilmLengthByFilmId(fieldT.getFilmId());
        Date endDate=new Date(beginTime.getTime()+filmLength*60*1000);
        String endFormatData=new SimpleDateFormat("yyyy/MM/dd").format(endDate);
        System.out.println("结束日期:"+endFormatData);
        String endTime=endDate.getHours()+":"+endDate.getMinutes();
        fieldT.setEndData(endDate);
        fieldT.setEndTime(endTime);

        //判断场次时间冲突
        EntityWrapper<FieldT> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("cinema_id",fieldT.getCinemaId());
        entityWrapper.eq("hall_id",fieldT.getHallId());
        List<FieldT> fieldTS=fieldTMapper.selectList(entityWrapper);
        for (FieldT hasField:fieldTS){
            Date hasFieldBeginTime=new Date(new SimpleDateFormat("yyyy/MM/dd").format(hasField.getBeginData())+" "+hasField.getBeginTime());
            Date hasFieldEndTime=new Date(new SimpleDateFormat("yyyy/MM/dd").format(hasField.getEndData())+" "+hasField.getEndTime());
            if (beginTime.after(hasFieldBeginTime)&&beginTime.before(hasFieldEndTime)){
                throw new AddFieldException();
            }
            if (endDate.after(hasFieldBeginTime)&&endDate.before(hasFieldEndTime)){
                throw new AddFieldException();
            }
            if (beginTime.before(hasFieldBeginTime)&&endDate.after(hasFieldEndTime)){
                throw new AddFieldException();
            }
            if (beginTime.compareTo(hasFieldBeginTime)==0||beginTime.compareTo(hasFieldEndTime)==0||endDate.compareTo(hasFieldBeginTime)==0||endDate.compareTo(hasFieldEndTime)==0){
                throw new AddFieldException();
            }
        }
        fieldTMapper.insert(fieldT);
        return true;
    }

    @Override
    public boolean updatePrice(UpdatePriceDTO updatePriceDTO) {
        if (updatePriceDTO.getPrice()<getMinPriceByCinemaId(Integer.parseInt(updatePriceDTO.getUuid()))){
            throw new SetPriceException();
        }
        FieldT fieldT=fieldTMapper.selectById(updatePriceDTO.getUuid());
        fieldT.setPrice(updatePriceDTO.getPrice());
        fieldTMapper.updateById(fieldT);
        return true;
    }
}
