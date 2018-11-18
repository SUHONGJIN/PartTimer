package com.mfzj.parttimer.presenter.impl;

import com.mfzj.parttimer.model.impl.SettingAModelImpl;
import com.mfzj.parttimer.model.inter.ISettingAModel;
import com.mfzj.parttimer.presenter.inter.ISettingAPresenter;
import com.mfzj.parttimer.view.inter.ISettingAView;

public class SettingAPresenterImpl implements ISettingAPresenter {
    private ISettingAView mISettingAView;
    private ISettingAModel mISettingAModel;

    public SettingAPresenterImpl(ISettingAView aISettingAView) {
        mISettingAView = aISettingAView;
        mISettingAModel = new SettingAModelImpl();
    }
}
