package com.demon.mvprr.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeMon
 * @date 2017/12/22
 * @description Presenter 工厂
 */

public class PresenterFactory {

    public static BasePresenter getPresenter(Class<? extends BasePresenter> clazz) {
        BasePresenter presenter = null;
        try {
            presenter = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return presenter;
    }

    /**
     * 根据注解中的Presenter生成对应的Presenter
     *
     * @param object 声明注解的类
     * @return 已生成的Presenter的list，用list是因为可能存在声明多个Presenter
     */
    public static List<BasePresenter> getPresenter(Object object) {
        List<BasePresenter> list = new ArrayList<>();
        // 通过获取注解获取所需的Presenter
        CreatePresenter annotation = object.getClass().getAnnotation(CreatePresenter.class);
        if (annotation == null) {
            return list;
        }
        Class<? extends BasePresenter>[] array = annotation.value();
        for (Class<? extends BasePresenter> e : array) {
            BasePresenter presenter = getPresenter(e);
            list.add(presenter);
        }
        return list;
    }

}