package com.kenny.movie.modular.movie.controller.cinemaadmin;

import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.modular.movie.dto.cinemaadmin.UpdatePriceDTO;
import com.kenny.movie.modular.movie.exception.DeleteFieldException;
import com.kenny.movie.modular.movie.service.cinemaadmin.IOrderTService;
import com.kenny.movie.modular.system.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.kenny.movie.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.kenny.movie.core.common.annotion.Permission;
import com.kenny.movie.core.common.exception.BizExceptionEnum;
import com.kenny.movie.core.exception.GunsException;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import com.kenny.movie.core.shiro.ShiroKit;
import com.kenny.movie.core.util.ToolUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.movie.modular.movie.service.cinemaadmin.IFieldTService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 影院后台控制器
 *
 * @author fengshuonan
 * @Date 2019-02-09 18:58:01
 */
@Controller
@RequestMapping("/cinema/field")
public class FieldTController extends BaseController {

    private String PREFIX = "/movie/cinemaadmin/fieldT/";

    @Autowired
    private IFieldTService fieldTService;

    @Autowired
    private IOrderTService orderTService;


    /**
     * 跳转到影院后台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "fieldT.html";
    }

    /**
     * 跳转到添加影院后台
     */
    @RequestMapping("/fieldT_add")
    public String fieldTAdd(@RequestParam(required = false) String cinemaId,Model model) {
        List<CinemaT> allCinemaInBrands = fieldTService.getAllCinemaInBrands(ShiroKit.getUser().getDeptId());
        List<HallFilmInfoT> allHallFilm = fieldTService.getAllHallFilm();
        List<HallDictT> allHallDict = fieldTService.getHallDictByCinemaId(allCinemaInBrands.get(0).getUuid());
        model.addAttribute("cinemaList",allCinemaInBrands);
        model.addAttribute("filmList",allHallFilm);
        model.addAttribute("hallList",allHallDict);
        return PREFIX + "fieldT_add.html";
    }

    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/fieldT_update/{fieldTId}")
    public String fieldTUpdate(@PathVariable String fieldTId, Model model) {
        FieldT fieldT = fieldTService.selectById(Integer.parseInt(fieldTId));
        model.addAttribute("item",fieldT);
        LogObjectHolder.me().set(fieldT);
        return PREFIX + "fieldT_edit.html";
    }

    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String cinemaName,
    @RequestParam(required = false) String filmName,
    @RequestParam(required = false) String beginData){
        EntityWrapper<FieldT> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(cinemaName)){
            entityWrapper.in("cinema_id",fieldTService.getCinemaIdListByCinemaName(cinemaName));
        }
        if(ToolUtil.isNotEmpty(filmName)){
            entityWrapper.eq("film_id",fieldTService.getFilmIdByFilmName(filmName));
        }
        if(ToolUtil.isNotEmpty(beginData)){
            entityWrapper.eq("begin_data",beginData);
        }
        if (!ShiroKit.isAdmin()){
            entityWrapper.in("cinema_id",fieldTService.getCinemaIdListByBrandId(ShiroKit.getUser().deptId));
        }
        return fieldTService.getFieldShowDTOByFieldList(fieldTService.selectList(entityWrapper));
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid FieldT fieldT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        fieldTService.addField(fieldT);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String fieldTId) {
        EntityWrapper<OrderT> entityWrapper=new EntityWrapper<>();
        entityWrapper.eq("field_id",fieldTId);
        if (orderTService.selectList(entityWrapper).size()>0){
            throw new DeleteFieldException();
        }
        fieldTService.deleteById(Integer.parseInt(fieldTId));
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid UpdatePriceDTO updatePriceDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        fieldTService.updatePrice(updatePriceDTO);
        return SUCCESS_TIP;
    }

    /**
     * 影院后台详情
     */
    @RequestMapping(value = "/detail/{fieldTId}")
    @ResponseBody
    public Object detail(@PathVariable("fieldTId") String fieldTId) {
        Long idByInt= Long.parseLong(fieldTId);
        return fieldTService.selectById(idByInt);
    }

    @RequestMapping(value = "/getHalls")
    @ResponseBody
    public Object getHallsByCinemaId(String cinemaId){
        return fieldTService.getHallDictByCinemaId(Integer.parseInt(cinemaId));
    }


}
