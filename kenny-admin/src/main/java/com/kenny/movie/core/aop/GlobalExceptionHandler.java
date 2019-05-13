package com.kenny.movie.core.aop;

import com.kenny.movie.core.common.exception.BizExceptionEnum;
import com.kenny.movie.core.common.exception.InvalidKaptchaException;
import com.kenny.movie.core.exception.GunsException;
import com.kenny.movie.core.log.factory.LogTaskFactory;
import com.kenny.movie.core.support.HttpKit;
import com.kenny.movie.core.base.tips.ErrorTip;
import com.kenny.movie.core.log.LogManager;
import com.kenny.movie.core.shiro.ShiroKit;
import com.kenny.movie.modular.movie.exception.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(GunsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(GunsException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        HttpKit.getRequest().setAttribute("tip", e.getMessage());
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 用户未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unAuth(AuthenticationException e) {
        log.error("用户未登陆：", e);
        return "/login.html";
    }

    /**
     * 账号被冻结异常
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accountLocked(DisabledAccountException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结", HttpKit.getIp()));
        model.addAttribute("tips", "账号被冻结");
        return "/login.html";
    }

    /**
     * 账号密码错误异常
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String credentials(CredentialsException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", HttpKit.getIp()));
        model.addAttribute("tips", "账号密码错误");
        return "/login.html";
    }

    /**
     * 验证码错误异常
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String credentials(InvalidKaptchaException e, Model model) {
        String username = HttpKit.getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", HttpKit.getIp()));
        model.addAttribute("tips", "验证码错误");
        return "/login.html";
    }

    /**
     * 无权访问该资源异常
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorTip credentials(UndeclaredThrowableException e) {
        HttpKit.getRequest().setAttribute("tip", "权限异常");
        log.error("权限异常!", e);
        return new ErrorTip(BizExceptionEnum.NO_PERMITION.getCode(), BizExceptionEnum.NO_PERMITION.getMessage());
    }

    @ExceptionHandler(AddFieldException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip applyError(AddFieldException e){
        HttpKit.getRequest().setAttribute("tip", "该影厅已被占用");
        log.error("该影厅已被占用!", e);
        return new ErrorTip(BizExceptionEnum.CHOOSE_DATA_OCCUPIED.getCode(), BizExceptionEnum.CHOOSE_DATA_OCCUPIED.getMessage());
    }

    @ExceptionHandler(SetPriceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip applyError(SetPriceException e){
        HttpKit.getRequest().setAttribute("tip", "不得低于影厅最低票价");
        log.error("不得低于影厅最低票价!", e);
        return new ErrorTip(BizExceptionEnum.SET_PRICE.getCode(), BizExceptionEnum.SET_PRICE.getMessage());
    }


    @ExceptionHandler(DeleteFieldException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip applyError(DeleteFieldException e){
        HttpKit.getRequest().setAttribute("tip", "该场次已有人购票,不得删除!");
        log.error("该场次已有人购票,不得删除!", e);
        return new ErrorTip(BizExceptionEnum.DELETE_FIELD_ERROR.getCode(), BizExceptionEnum.DELETE_FIELD_ERROR.getMessage());
    }

    @ExceptionHandler(FieldTimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip applyError(FieldTimeException e){
        HttpKit.getRequest().setAttribute("tip", "排场时间必须提前一天");
        log.error("排场时间必须提前一天!", e);
        return new ErrorTip(BizExceptionEnum.FIELR_TIME_ERROR.getCode(), BizExceptionEnum.FIELR_TIME_ERROR.getMessage());
    }

    @ExceptionHandler(AddCatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip addCatError(AddCatException e){
        HttpKit.getRequest().setAttribute("tip", "该类型已经添加过");
        log.error("该类型已经添加过!", e);
        return new ErrorTip(BizExceptionEnum.ADD_CAT_ERROR.getCode(), BizExceptionEnum.ADD_CAT_ERROR.getMessage());
    }
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        HttpKit.getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        return new ErrorTip(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }
}
