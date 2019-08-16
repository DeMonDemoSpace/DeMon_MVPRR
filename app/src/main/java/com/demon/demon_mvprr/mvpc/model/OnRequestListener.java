package com.demon.demon_mvprr.mvpc.model;

/**
 * @author DeMon
 * @date 2019/7/19
 * @email 757454343@qq.com
 * @description
 */
public interface OnRequestListener<T> {
    void onSucceed(T t);

    void onError(String s);
}
