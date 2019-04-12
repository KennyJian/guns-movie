package com.kenny.movie.modular.system.warpper;

import com.kenny.movie.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

public class SecondWrapper extends BaseControllerWarpper {


    public SecondWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {

    }
}
