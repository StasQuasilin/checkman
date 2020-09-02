package ua.s.vasilina.checkman.activity.deals;

import ua.s.vasilina.checkman.activity.ParentActivity;
import ua.s.vasilina.checkman.client.R;

public class DealEdit extends ParentActivity {
    @Override
    protected void init() {

    }

    @Override
    protected int getPageTitle() {
        return R.string.deal_edit;
    }

    @Override
    protected int getView() {
        return R.layout.deal_edit_activity;
    }

    @Override
    protected void beforeDestroy() {

    }
}
