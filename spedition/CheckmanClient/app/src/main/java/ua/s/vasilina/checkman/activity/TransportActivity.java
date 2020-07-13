package ua.s.vasilina.checkman.activity;

import androidx.appcompat.app.AppCompatActivity;

import ua.s.vasilina.checkman.client.R;

public class TransportActivity extends ParentActivity {
    @Override
    protected void init() {

    }

    @Override
    protected int getPageTitle() {
        return R.string.menu_transport;
    }

    @Override
    protected int getView() {
        return R.layout.transport_activity;
    }

    @Override
    protected void beforeDestroy() {

    }
}
