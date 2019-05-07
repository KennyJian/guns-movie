package com.kenny.movie.modular.movie.controller.cinemaadmin;

import com.kenny.movie.core.base.controller.BaseController;
import com.kenny.movie.core.base.tips.SearchMoneyTip;
import com.kenny.movie.modular.movie.service.cinemaadmin.IFieldTService;
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
import com.kenny.movie.modular.system.model.OrderT;
import com.kenny.movie.modular.movie.service.cinemaadmin.IOrderTService;

import java.util.List;

/**
 * 影院后台控制器
 *
 * @author fengshuonan
 * @Date 2019-02-10 13:34:04
 */
@Controller
@RequestMapping("/cinema/order")
public class OrderTController extends BaseController {

    private String PREFIX = "/movie/cinemaadmin/orderT/";

    @Autowired
    private IFieldTService fieldTService;

    @Autowired
    private IOrderTService orderTService;


    /**
     * 跳转到影院后台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderT.html";
    }

    /**
     * 跳转到添加影院后台
     */
    @RequestMapping("/orderT_add")
    public String orderTAdd() {
        return PREFIX + "orderT_add.html";
    }

    /**
     * 跳转到修改影院后台
     */
    @RequestMapping("/orderT_update/{orderTId}")
    public String orderTUpdate(@PathVariable String orderTId, Model model) {
        Long idByInt= Long.parseLong(orderTId);
        OrderT orderT = orderTService.selectById(idByInt);
        model.addAttribute("item",orderT);
        LogObjectHolder.me().set(orderT);
        return PREFIX + "orderT_edit.html";
    }

    /**
     * 获取影院后台列表
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(    @RequestParam(required = false) String cinemaName,
    @RequestParam(required = false) String filmName,
    @RequestParam(required = false) String orderTime,
    @RequestParam(required = false) String orderStatus){
        EntityWrapper<OrderT> entityWrapper=new EntityWrapper<>();
        if (ToolUtil.isNotEmpty(cinemaName)){
            List<Integer> cinemaIdListByCinemaName = fieldTService.getCinemaIdListByCinemaName(cinemaName);
            if (cinemaIdListByCinemaName.size()>0){
                entityWrapper.in("cinema_id",cinemaIdListByCinemaName);
            }else {
                entityWrapper.eq("cinema_id","-1");
            }

        }
        if (ToolUtil.isNotEmpty(filmName)){
            entityWrapper.eq("film_id",fieldTService.getFilmIdByFilmName(filmName));
        }
        if (ToolUtil.isNotEmpty(orderTime)){
            entityWrapper.like("order_time",orderTime);
        }
        if (ToolUtil.isNotEmpty(orderStatus)){
            entityWrapper.eq("order_status",orderStatus);
        }
        if (!ShiroKit.isAdmin()){
            entityWrapper.in("cinema_id",fieldTService.getCinemaIdListByBrandId(ShiroKit.getUser().deptId));
        }
        return orderTService.changeOrderTToOrderShowDTO(orderTService.selectList(entityWrapper));
    }

    /**
     * 新增影院后台
     */
    @Permission
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid OrderT orderT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        orderTService.insert(orderT);
        return SUCCESS_TIP;
    }

    /**
     * 删除影院后台
     */
    @Permission
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String orderTId) {
        Long idByInt= Long.parseLong(orderTId);
        orderTService.deleteById(idByInt);
        return SUCCESS_TIP;
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid OrderT orderT, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        orderTService.updateById(orderT);
        return SUCCESS_TIP;
    }

    /**
     * 影院后台详情
     */
    @RequestMapping(value = "/detail/{orderTId}")
    @ResponseBody
    public Object detail(@PathVariable("orderTId") String orderTId) {
        Long idByInt= Long.parseLong(orderTId);
        return orderTService.selectById(idByInt);
    }

    /**
     * 修改影院后台
     */
    @Permission
    @RequestMapping(value = "/searchMoney")
    @ResponseBody
    public Object getMoney(@RequestParam String fieldId) {
        return new SearchMoneyTip(orderTService.getFieldSumOfMoney(fieldId));
    }
}
