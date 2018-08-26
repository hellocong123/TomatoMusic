package com.cong.cong_music.bean.response;

/**
 * @author Cong
 * @date 2018/8/22
 * @description
 */
public class DetailResponse<T> extends BaseResponse {
    private T data;

    public T getData() {
        return data;
    }

    public DetailResponse setData(T data) {
        this.data = data;
        return this;
    }
}
