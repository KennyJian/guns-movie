package com.stylefeng;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KennyTest {

    @Test
    public void testTime() throws ParseException {
        Date date= new Date("2019-02-15 00:00:00");
        System.out.println(date);
    }

    @Test
    public void testTime2() throws ParseException {
        String beginData="2019-02-15";
        String begin="21:45";
        String formatData=beginData.replaceAll("-","/");
        Date beginTime=new Date(formatData+" "+begin);
        Integer filmLength=Integer.parseInt("119");
        Date endDate=new Date(beginTime.getTime()+filmLength*60*1000);
        String endTime=endDate.getHours()+":"+endDate.getMinutes();
        System.out.println("开始时间:"+beginData+" "+begin);
        System.out.println("结束时间"+endTime);
    }
}
