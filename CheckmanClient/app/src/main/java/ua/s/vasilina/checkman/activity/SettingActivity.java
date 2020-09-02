package ua.s.vasilina.checkman.activity;

import ua.s.vasilina.checkman.client.R;

public class SettingActivity extends ParentActivity{
    @Override
    protected void init() {

    }

    @Override
    protected int getPageTitle() {
        return R.string.settings;
    }

    @Override
    protected int getView() {
        return R.layout.settings_activity;
    }

    @Override
    protected void beforeDestroy() {

    }
}
