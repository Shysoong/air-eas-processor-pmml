/* ***************************************************************
 * Copyright (c) 2018-2019 Contributors.
 * All rights reserved.
 * This program and the accompanying materials can be used
 * in the case of Anycc authorization
 *
 * Contributors:
 *   AnyccTeam           Initial implementation
 * WebSite:
 *   http://www.anycc.com
 * **************************************************************/
package io.skyease.air.eas.processor.pmml.common;

/**
 * 通用JSON返回对象
 *
 * @author Ace
 * @date 2019年1月29日
 * @time 下午6:10:32
 * @param <T> 返回对象
 */
public class Response<T> {
    public static final String FIELD_SUCCESS = "success";// 字段名-是否成功
    public static final String FIELD_DATA = "data";// 字段名-返回数据

    private boolean success;// 表明处理结果是否成功
    private T data;// 如果status为success，则data内返回结果数据；如果statsu为failed，则data内返回错误信息

    /**
     * 构造方法
     *
     * @param success 是否成功
     * @param data 返回数据
     */
    private Response(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 创建JSON返回对象
     *
     * @param success 是否成功
     * @param data 数据
     * @return Response
     */
    private static <T> Response<T> create(boolean success, T data) {
        return new Response<>(success, data);
    }

    /**
     * 创建通用成功状态的JSON返回对象
     *
     * @param data 返回数据
     * @return Response
     */
    public static <T> Response<T> success(T data) {
        return create(true, data);
    }

    /**
     * 创建通用失败状态的JSON返回对象
     *
     * @param data 返回数据
     * @return Response
     */
    public static <T> Response<T> failed(T data) {
        return create(false, data);
    }

}

