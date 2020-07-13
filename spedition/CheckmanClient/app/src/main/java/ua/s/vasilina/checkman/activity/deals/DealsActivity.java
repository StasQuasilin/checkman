package ua.s.vasilina.checkman.activity.deals;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ua.s.vasilina.checkman.activity.ParentActivity;
import ua.s.vasilina.checkman.adapters.SimpleAdapter;
import ua.s.vasilina.checkman.client.R;
import ua.s.vasilina.checkman.entity.Deal;
import ua.s.vasilina.checkman.utils.AdapterViewBuilder;

public class DealsActivity extends ParentActivity {

    private FloatingActionButton addButton;
    private ListView dealList;

    @Override
    protected void init() {
        addButton = findViewById(R.id.addButton);
        dealList = findViewById(R.id.dealList);
        initDealList();
        initAddButton();
    }

    private void initDealList() {
        AdapterViewBuilder<Deal> builder = new AdapterViewBuilder<Deal>() {
            @Override
            public View build(Deal item, View view) {
                return null;
            }
        };
        SimpleAdapter<Deal> adapter = new SimpleAdapter<>(getApplicationContext(), R.layout.deal_item_view, builder);
        dealList.setAdapter(adapter);
    }

    private void initAddButton() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = getApplicationContext();
                Intent intent = new Intent(context, DealEdit.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected int getPageTitle() {
        return R.string.menu_deals;
    }

    @Override
    protected int getView() {
        return R.layout.deal_activity;
    }

    @Override
    protected void beforeDestroy() {

    }
}
