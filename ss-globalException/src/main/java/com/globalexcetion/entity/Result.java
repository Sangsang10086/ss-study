package com.globalexcetion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    /**
     * 失败返回格式
     * @param message
     * @return
     */
    public static Result<String> error(String message){
        return new Result<>("500",message,null);
    }

    /**
     * 成功返回格式
     * @return
     */

    public static Result<String> success(){
        return new Result<>("200","success",null);
    }

    public static Result<String> success(String data){
        return new Result<>("200","success",data);
    }

    public static Result<String> success(String msg,String data){
        return new Result<>("200",msg,data);
    }

    public static Result<String> success(String code,String msg,String data){
        return new Result<>(code,msg,data);
    }

}
