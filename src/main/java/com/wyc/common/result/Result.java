package com.wyc.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 200;
        return result;
    }
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        result.code = 200;
        return result;
    }
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        result.msg = "success";
        return result;
    }
    public static <T> Result<T> success(String msg,T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 200;
        result.msg = msg;
        return result;
    }
    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 500;
        return result;
    }
}
