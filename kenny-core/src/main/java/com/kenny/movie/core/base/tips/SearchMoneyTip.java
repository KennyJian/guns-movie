package com.kenny.movie.core.base.tips;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SearchMoneyTip extends Tip {

	public SearchMoneyTip(String money){
		super.code = 200;
		super.message = money;
	}
}
