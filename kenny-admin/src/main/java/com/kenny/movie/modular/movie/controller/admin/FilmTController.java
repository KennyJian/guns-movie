package com.kenny.movie.modular.movie.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.core.common.annotion.Permission;
import com.kenny.movie.core.common.exception.BizExceptionEnum;
import com.kenny.movie.core.exception.GunsException;
import com.kenny.movie.core.log.LogObjectHolder;
import com.kenny.movie.core.shiro.ShiroKit;
import com.kenny.movie.core.util.ToolUtil;
import com.kenny.movie.modular.movie.service.IFilmTService;
import com.kenny.movie.modular.system.model.FilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 影院后台控制器
 *
 * @author fengshuonan
 * @Date 2019-02-05 12:27:27
 */
@Controller
@RequestMapping("/filmT")
public class FilmTController extends BaseController {

    private String PREFIX = "/movie/filmT/";

    @Autowired
    private IFilmTService filmTService;


    /**
     * 跳转到影院后台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "filmT.html";
    }

    /**
     * 跳转到添加影院后台
     */
    @RequestMapping("/filmT_add")
    public String filmTAdd() {
        return PREFIX + "filmT_add.html";
    }

    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/filmT_update/{filmTId}")
    public String filmTUpdate(@PathVariable String filmTId, Model model) {
        Long idByInt= Long.parseLong(filmTId);
        FilmT filmT = filmTService.selectById(idByInt);
        model.addAttribute("item",filmT);
        LogObjectHolder.me().set(filmT);
        return PREFIX + "filmT_edit.html";
    }

    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String uuid,
    @RequestParam(required = false) String filmName,
    @RequestParam(required = false) String filmType,
    @RequestParam(required = false) String imgAddress,
    @RequestParam(required = false) String filmScore,
    @RequestParam(required = false) String filmPresalenum,
    @RequestParam(required = false) String filmBoxOffice,
    @RequestParam(required = false) String filmSource,
    @RequestParam(required = false) String filmCats,
    @RequestParam(required = false) String filmArea,
    @RequestParam(required = false) String filmDate,
    @RequestParam(required = false) String filmTime,
    @RequestParam(required = false) String filmStatus){
        EntityWrapper<FilmT> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(uuid)){
            entityWrapper.like("uuid",uuid);
        }
        if(ToolUtil.isNotEmpty(filmName)){
            entityWrapper.like("filmName",filmName);
        }
        if(ToolUtil.isNotEmpty(filmType)){
            entityWrapper.like("filmType",filmType);
        }
        if(ToolUtil.isNotEmpty(imgAddress)){
            entityWrapper.like("imgAddress",imgAddress);
        }
        if(ToolUtil.isNotEmpty(filmScore)){
            entityWrapper.like("filmScore",filmScore);
        }
        if(ToolUtil.isNotEmpty(filmPresalenum)){
            entityWrapper.like("filmPresalenum",filmPresalenum);
        }
        if(ToolUtil.isNotEmpty(filmBoxOffice)){
            entityWrapper.like("filmBoxOffice",filmBoxOffice);
        }
        if(ToolUtil.isNotEmpty(filmSource)){
            entityWrapper.like("filmSource",filmSource);
        }
        if(ToolUtil.isNotEmpty(filmCats)){
            entityWrapper.like("filmCats",filmCats);
        }
        if(ToolUtil.isNotEmpty(filmArea)){
            entityWrapper.like("filmArea",filmArea);
        }
        if(ToolUtil.isNotEmpty(filmDate)){
            entityWrapper.like("filmDate",filmDate);
        }
        if(ToolUtil.isNotEmpty(filmTime)){
            entityWrapper.like("filmTime",filmTime);
        }
        if(ToolUtil.isNotEmpty(filmStatus)){
            entityWrapper.like("filmStatus",filmStatus);
        }
        if (!ShiroKit.isAdmin()){
            entityWrapper.in("deptid",ShiroKit.getDeptDataScope());
        }
        return filmTService.selectList(entityWrapper);
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid FilmT filmT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.insert(filmT);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String filmTId) {
        Long idByInt= Long.parseLong(filmTId);
        filmTService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid FilmT filmT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.updateById(filmT);
        return SUCCESS_TIP;
    }

    /**
     * 影院后台详情
     */
    @RequestMapping(value = "/detail/{filmTId}")
    @ResponseBody
    public Object detail(@PathVariable("filmTId") String filmTId) {
        Long idByInt= Long.parseLong(filmTId);
        return filmTService.selectById(idByInt);
    }
}
