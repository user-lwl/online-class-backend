package com.lwl.ggkt.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果类
 * @author user-lwl
 * @createDate 2022/12/1 15:15
 */
@Data
@NoArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功
     * @return T
     * @param <T> T
     */
    @SuppressWarnings(value = "unused")
    public static <T> Result<T> ok () {
        Result<T> result = new Result<>();
        result.setCode(20000);
        result.setMessage("成功");
        return result;
    }

    /**
     * 失败
     * @return T
     * @param <T> T
     */
    @SuppressWarnings(value = "unused")
    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(20001);
        result.setMessage("失败");
        return result;
    }

    /**
     * 成功
     * @param data 数据
     * @return T
     * @param <T> T
     */
    @SuppressWarnings(value = "unused")
    public static <T> Result<T> ok (T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(20000);
        result.setMessage("成功");
        return result;
    }

    /**
     * 失败
     * @param data 数据
     * @return T
     * @param <T> T
     */
    @SuppressWarnings(value = "unused")
    public static <T> Result<T> fail(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(20001);
        result.setMessage("失败");
        return result;
    }

    /**
     * 设置返回消息
     * @param msg 消息
     * @return result
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 设置状态码
     * @param code 状态码
     * @return result
     */
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}
