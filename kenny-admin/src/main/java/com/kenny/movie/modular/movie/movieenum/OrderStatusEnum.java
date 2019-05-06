package com.kenny.movie.modular.movie.movieenum;

public enum OrderStatusEnum {

    ZERO(0,"待支付"),
    ONE(1,"已支付"),
    TWO(2,"已关闭");

    int code;
    String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for(OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()){
            if(orderStatusEnum.getCode()==code){
                return orderStatusEnum.getMsg();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
