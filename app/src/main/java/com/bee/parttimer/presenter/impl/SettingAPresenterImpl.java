package com.bee.parttimer.presenter.impl;

import com.bee.parttimer.model.impl.SettingAModelImpl;
import com.bee.parttimer.model.inter.ISettingAModel;
import com.bee.parttimer.presenter.inter.ISettingAPresenter;
import com.bee.parttimer.view.inter.ISettingAView;

public class SettingAPresenterImpl implements ISettingAPresenter {
    private ISettingAView mISettingAView;
    private ISettingAModel mISettingAModel;

    public SettingAPresenterImpl(ISettingAView aISettingAView) {
        mISettingAView = aISettingAView;
        mISettingAModel = new SettingAModelImpl();
    }
}
