package com.ryan.pojo;

import com.ryan.constant.Constants;

import java.io.Serializable;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public class ResponseEntity<T> implements Serializable {

    /**状态码 */
    private String code = Constants.RESPONSE_SUCCESS;

    /**返回信息 */
    private String msg;

    /**数据 */
    private T data;

    /**数据条数 */
    private long count;

    public ResponseEntity() {

    }

    /**
     * 返回 数据
     * @param data
     */
    public ResponseEntity(T data) {
        this.data = data;
    }

    /**
     * 返回 数据、数据条数
     * @param data
     * @param count
     */
    public ResponseEntity(T data, long count) {
        this.data = data;
        this.count = count;
    }

    /**
     * 返回 状态码、返回信息
     * @param code
     * @param msg
     */
    public ResponseEntity(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public ResponseEntity<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public ResponseEntity<T> setData(T data) {
        this.data = data;
        return this;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseEntity(super=" + super.toString() + ", msg=" + getMsg() + ", data=" + getData() + ", count=" + getCount() + ", code=" + getCode() + ")";
    }
}
