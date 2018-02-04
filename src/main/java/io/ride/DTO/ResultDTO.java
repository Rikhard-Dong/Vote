package io.ride.DTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于返回json
 */
public class ResultDTO {
    private int code;       // 0. 失败     1. 成功
    private String msg;
    private Map<String, Object> data;

    public ResultDTO() {
        data = new HashMap<>();
    }

    public ResultDTO(int code, String msg) {
        data = new HashMap<>();
        this.code = code;
        this.msg = msg;
    }

    public static ResultDTO SUCCESS(String msg) {
        return new ResultDTO(1, msg);
    }

    public static ResultDTO FAIL(String msg) {
        return new ResultDTO(0, msg);
    }

    public ResultDTO addData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
