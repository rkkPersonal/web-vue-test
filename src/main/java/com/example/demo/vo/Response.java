package com.example.demo.vo;

import lombok.Data;

/**
 * @author Steven
 */
@Data
public class Response {

    private Integer code;

    private String msg;

    private Object data;

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Response ok() {
        return new Response(200,"success","成功");
    }

    public static Response  error(String msg) {
        return new Response(-1,"error",msg);
    }
}
