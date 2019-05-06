package com.kenny.movie.modular.movie.controller.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.core.common.annotion.Permission;
import com.kenny.movie.core.common.exception.BizExceptionEnum;
import com.kenny.movie.core.exception.GunsException;
import com.kenny.movie.core.log.LogObjectHolder;
import com.kenny.movie.core.shiro.ShiroKit;
import com.kenny.movie.core.util.ToolUtil;
import com.kenny.movie.modular.movie.dto.admin.*;
import com.kenny.movie.modular.movie.service.admin.IFilmTService;
import com.kenny.movie.modular.system.dao.FilmInfoTMapper;
import com.kenny.movie.modular.system.model.ActorT;
import com.kenny.movie.modular.system.model.FilmInfoT;
import com.kenny.movie.modular.system.model.FilmT;
import com.kenny.movie.modular.system.model.HallFilmInfoT;
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
@RequestMapping("/admin/manage")
public class FilmTController extends BaseController {

    private String PREFIX = "/movie/admin/manage/";

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
     * 跳转到修改电影故事简介
     */
    @RequestMapping("/updateStory/{filmTId}")
    public String filmTUpdateStory(@PathVariable String filmTId, Model model) {
        FilmInfoT filmInfoT = filmTService.getFilmInfoTByFilmId(filmTId);
        UpdateStoryDTO updateStoryDTO=new UpdateStoryDTO();
        updateStoryDTO.setUuid(filmTId);
        updateStoryDTO.setStory(filmInfoT.getBiography());
        model.addAttribute("item",updateStoryDTO);
        LogObjectHolder.me().set(updateStoryDTO);
        return PREFIX + "filmT_edit_story.html";
    }

    /**
     * 跳转到修改电影标签
     */
    @RequestMapping("/updateCat/{filmTId}")
    public String filmTUpdateCat(@PathVariable String filmTId, Model model) {
        HallFilmInfoT hallFilmInfoT = filmTService.getHallFilmInfoTByFilmId(filmTId);
        UpdateCatDTO updateCatDTO=new UpdateCatDTO();
        updateCatDTO.setUuid(filmTId);
        updateCatDTO.setFilmOldCats(hallFilmInfoT.getFilmCats());
        model.addAttribute("item",updateCatDTO);
        LogObjectHolder.me().set(updateCatDTO);
        return PREFIX + "filmT_edit_cats.html";
    }


    /**
     * 跳转到新增电影图片页面
     */
    @RequestMapping("/addImg/{filmTId}")
    public String filmTAddImg(@PathVariable String filmTId, Model model) {
        AddImgDTO addImgDTO=new AddImgDTO();
        addImgDTO.setUuid(filmTId);
        FilmInfoT filmInfoT=filmTService.getFilmInfoTByFilmId(filmTId);
        if (ToolUtil.isNotEmpty(filmInfoT.getFilmImgs())) {
            String[] imgs = filmInfoT.getFilmImgs().split(",");
            if (imgs.length >= 5) {
                addImgDTO.setImg01(imgs[0]);
                addImgDTO.setImg02(imgs[1]);
                addImgDTO.setImg03(imgs[2]);
                addImgDTO.setImg04(imgs[3]);
                addImgDTO.setImg05(imgs[4]);
            }
        }
        model.addAttribute("item",addImgDTO);
        return PREFIX + "filmT_edit_imgs.html";
    }

    /**
     * 跳转到修改电影标签
     */
    @RequestMapping("/updateDirector/{filmTId}")
    public String filmTUpdateDirector(@PathVariable String filmTId, Model model) {
        AddDirectorDTO addDirectorDTO=new AddDirectorDTO();
        addDirectorDTO.setUuid(filmTId);
        FilmInfoT filmInfoT=filmTService.getFilmInfoTByFilmId(filmTId);
        if (ToolUtil.isNotEmpty(filmInfoT.getDirectorId())) {
            ActorT actorT = filmTService.getActorByActorId(filmInfoT.getDirectorId());
            addDirectorDTO.setDirectorName(actorT.getActorName());
            addDirectorDTO.setDirectorImgUrl(actorT.getActorImg());
        }
        model.addAttribute("item",addDirectorDTO);
        LogObjectHolder.me().set(addDirectorDTO);
        return PREFIX + "filmT_edit_directors.html";
    }

    /**
     * 跳转到添加演员标签
     */
    @RequestMapping("/addActor/{filmTId}")
    public String filmTAddActor(@PathVariable String filmTId, Model model) {
        model.addAttribute("item",filmTId);
        return PREFIX + "filmT_add_actors.html";
    }

    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String filmName,
    @RequestParam(required = false) String filmType,
    @RequestParam(required = false) String filmSource,
    @RequestParam(required = false) String filmArea,
    @RequestParam(required = false) String filmDate
    ){
        return filmTService.findAllFilmList(filmName,filmType,filmSource,filmArea,filmDate);
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid AdminShowDTO adminShowDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.insertFilm(adminShowDTO);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String filmTId) {
        Integer idByInt= Integer.parseInt(filmTId);
        System.out.println("选中删除的id为:"+idByInt);
        //不想真的删 暂时注释
//        filmTService.deleteFilm(idByInt);
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

    /**
     * 修改电影故事简介
     */
    @Permission
    @RequestMapping(value = "/updateStory")
    @ResponseBody
    public Object updateStory(@Valid UpdateStoryDTO updateStoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.updateStory(updateStoryDTO);
        return SUCCESS_TIP;
    }


    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/updateCat")
    @ResponseBody
    public Object updateCat(@Valid UpdateCatDTO updateCatDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.updateCat(updateCatDTO);
        return SUCCESS_TIP;
    }


    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/addFilmImg")
    @ResponseBody
    public Object addFilmImg(@Valid AddImgDTO addImgDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.addImg(addImgDTO);
        return SUCCESS_TIP;
    }

    /**
     * 修改电影导演
     */
    @Permission
    @RequestMapping(value = "/addFilmDirector")
    @ResponseBody
    public Object addFilmDirector(@Valid AddDirectorDTO addDirectorDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.addDirector(addDirectorDTO);
        return SUCCESS_TIP;
    }

    /**
     * 修改电影导演
     */
    @Permission
    @RequestMapping(value = "/addFilmActor")
    @ResponseBody
    public Object addFilmActor(@Valid AddActorDTO addActorDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        filmTService.addActor(addActorDTO);
        return SUCCESS_TIP;
    }
}
