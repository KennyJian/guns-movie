package com.kenny.movie.modular.movie.controller.admin;

import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.modular.movie.dto.admin.ActorEditDTO;
import com.kenny.movie.modular.movie.dto.admin.ActorShowDTO;
import com.kenny.movie.modular.movie.service.admin.IFilmTService;
import com.kenny.movie.modular.system.model.ActorT;
import com.kenny.movie.modular.system.model.FilmT;
import com.kenny.movie.modular.system.service.IActorTService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.kenny.movie.modular.system.model.FilmActorT;
import com.kenny.movie.modular.movie.service.admin.IFilmActorTService;

import java.util.ArrayList;
import java.util.List;

/**
 * 影院后台控制器
 *
 * @author fengshuonan
 * @Date 2019-05-07 11:10:23
 */
@Controller
@RequestMapping("/admin/manageActor")
public class FilmActorTController extends BaseController {

    private String PREFIX = "/movie/admin/manage/filmActorT/";

    @Autowired
    private IFilmActorTService filmActorTService;

    @Autowired
    private IFilmTService filmTService;

    @Autowired
    private IActorTService actorTService;

    /**
     * 跳转到影院后台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "filmActorT.html";
    }

    /**
     * 跳转到添加影院后台
     */
    @RequestMapping("/filmActorT_add")
    public String filmActorTAdd() {
        return PREFIX + "filmActorT_add.html";
    }

    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/filmActorT_update/{filmActorTId}")
    public String filmActorTUpdate(@PathVariable String filmActorTId, Model model) {

        ActorEditDTO actorEditDTO=new ActorEditDTO();
        actorEditDTO.setId(new Integer(filmActorTId));
        actorEditDTO.setRoleName(filmActorTService.selectById(filmActorTId).getRoleName());
        actorEditDTO.setActorImg(actorTService.selectById(filmActorTService.selectById(filmActorTId).getActorId()).getActorImg());
        model.addAttribute("item",actorEditDTO);
        LogObjectHolder.me().set(actorEditDTO);
        return PREFIX + "filmActorT_edit.html";
    }

    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String filmName,
    @RequestParam(required = false) String actorName,
    @RequestParam(required = false) String roleName){
        EntityWrapper<FilmActorT> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(filmName)){
            EntityWrapper<FilmT> filmTEntityWrapper=new EntityWrapper<>();
            filmTEntityWrapper.like("film_name",filmName);
            List<Integer> filmList=new ArrayList<>();
            for (FilmT filmT:filmTService.selectList(filmTEntityWrapper)){
                filmList.add(filmT.getUuid());
            }
            if (filmList.size()>0){
                entityWrapper.in("film_id",filmList);
            }else{
                entityWrapper.eq("film_id",-1);
            }
        }
        if(ToolUtil.isNotEmpty(actorName)){
            EntityWrapper<ActorT> actorTEntityWrapper=new EntityWrapper<>();
            actorTEntityWrapper.like("actor_name",actorName);
            List<Integer> actorList=new ArrayList<>();
            for (ActorT actorT:actorTService.selectList(actorTEntityWrapper)){
                actorList.add(actorT.getUuid());
            }
            if (actorList.size()>0){
                entityWrapper.in("actor_id",actorList);
            }else {
                entityWrapper.eq("actor_id",-1);
            }
        }
        if(ToolUtil.isNotEmpty(roleName)){
            entityWrapper.like("role_name",roleName);
        }
        List<FilmActorT> filmActorTList = filmActorTService.selectList(entityWrapper);
        List<ActorShowDTO> actorShowList=new ArrayList<>();
        for (FilmActorT filmActorT:filmActorTList){
            ActorShowDTO actorShowDTO=new ActorShowDTO();
            actorShowDTO.setUuid(filmActorT.getUuid());
            actorShowDTO.setFilmName(filmTService.selectById(filmActorT.getFilmId()).getFilmName());
            actorShowDTO.setActorName(actorTService.selectById(filmActorT.getActorId()).getActorName());
            actorShowDTO.setRoleName(filmActorT.getRoleName());
            actorShowList.add(actorShowDTO);
        }
        return actorShowList;
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid FilmActorT filmActorT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmActorTService.insert(filmActorT);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String filmActorTId) {
        filmActorTService.deleteById(new Integer(filmActorTId));
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @Transactional
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid ActorEditDTO actorEditDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //修改角色名
        FilmActorT filmActorT = filmActorTService.selectById(actorEditDTO.getId());
        filmActorT.setRoleName(actorEditDTO.getRoleName());
        filmActorTService.updateById(filmActorT);
        //修改演员图片
        ActorT actorT = actorTService.selectById(filmActorT.getActorId());
        actorT.setActorImg(actorEditDTO.getActorImg());
        actorTService.updateById(actorT);
        return SUCCESS_TIP;
    }

    /**
     * 影院后台详情
     */
    @RequestMapping(value = "/detail/{filmActorTId}")
    @ResponseBody
    public Object detail(@PathVariable("filmActorTId") String filmActorTId) {
        Long idByInt= Long.parseLong(filmActorTId);
        return filmActorTService.selectById(idByInt);
    }
}
