package com.kenny.movie.modular.movie.controller.cinemaadmin;

import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.modular.movie.dto.cinemaadmin.CinemaAddHallDTO;
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
import com.kenny.movie.modular.system.model.CinemaT;
import com.kenny.movie.modular.movie.service.cinemaadmin.ICinemaTService;

/**
 * 影院后台控制器
 *
 * @author fengshuonan
 * @Date 2019-02-09 13:17:10
 */
@Controller
@RequestMapping("/cinema/admin")
public class CinemaTController extends BaseController {

    private String PREFIX = "/movie/cinemaadmin/cinemaT/";

    @Autowired
    private ICinemaTService cinemaTService;


    /**
     * 跳转到影院后台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "cinemaT.html";
    }

    /**
     * 跳转到添加影院后台
     */
    @RequestMapping("/cinemaT_add")
    public String cinemaTAdd() {
        return PREFIX + "cinemaT_add.html";
    }

    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/cinemaT_update/{cinemaTId}")
    public String cinemaTUpdate(@PathVariable String cinemaTId, Model model) {
        Long idByInt= Long.parseLong(cinemaTId);
        CinemaT cinemaT = cinemaTService.selectById(idByInt);
        model.addAttribute("item",cinemaT);
        LogObjectHolder.me().set(cinemaT);
        return PREFIX + "cinemaT_edit.html";
    }


    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/addHall/{cinemaTId}")
    public String cinemaTaddHall(@PathVariable String cinemaTId, Model model) {
        CinemaAddHallDTO cinemaAddHallDTO=new CinemaAddHallDTO();
        cinemaAddHallDTO.setUuid(cinemaTId);
        cinemaAddHallDTO.setOldHall(cinemaTService.getOldHall(cinemaTId));
        model.addAttribute("item",cinemaAddHallDTO);
        LogObjectHolder.me().set(cinemaAddHallDTO);
        return PREFIX + "cinemaT_add_hall.html";
    }
    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String cinemaName){
        EntityWrapper<CinemaT> entityWrapper=new EntityWrapper<>();
        if(ToolUtil.isNotEmpty(cinemaName)){
            entityWrapper.like("cinema_name",cinemaName);
        }
        if (!ShiroKit.isAdmin()){
            entityWrapper.in("brand_id",ShiroKit.getUser().getDeptId().toString());
        }
        return cinemaTService.cinemaTToCinemaShowDTO(cinemaTService.selectList(entityWrapper));
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid CinemaT cinemaT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        cinemaT.setBrandId(ShiroKit.getUser().getDeptId());
        cinemaT.setImgAddress("/cinemas/jin_yi_06.jpg");
        cinemaTService.insert(cinemaT);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String cinemaTId) {
        cinemaTService.deleteById(Integer.parseInt(cinemaTId));
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid CinemaT cinemaT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        cinemaTService.updateById(cinemaT);
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/addHall")
    @ResponseBody
    public Object update(@Valid CinemaAddHallDTO cinemaAddHallDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        cinemaTService.addHall(cinemaAddHallDTO);
        return SUCCESS_TIP;
    }

    /**
     * 影院后台详情
     */
    @RequestMapping(value = "/detail/{cinemaTId}")
    @ResponseBody
    public Object detail(@PathVariable("cinemaTId") String cinemaTId) {
        Long idByInt= Long.parseLong(cinemaTId);
        return cinemaTService.selectById(idByInt);
    }
}
