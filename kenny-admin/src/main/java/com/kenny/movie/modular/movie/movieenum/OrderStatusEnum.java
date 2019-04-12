package com.kenny.movie.modular.movie.movieenum;

public enum FilmTypeEnum {

    ZERO(0,"2D"),
    ONE(1,"3D"),
    TWO(2,"3D-IMAX"),
    THREE(3,"无");

    int code;
    String msg;

    FilmTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsg(int code){
        for(FilmTypeEnum filmTypeEnum : FilmTypeEnum.values()){
            if(filmTypeEnum.getCode()==code){
                return filmTypeEnum.getMsg();
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
