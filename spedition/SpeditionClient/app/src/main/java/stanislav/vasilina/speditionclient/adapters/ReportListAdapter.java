package stanislav.vasilina.speditionclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.activity.ReportEdit;
import stanislav.vasilina.speditionclient.entity.Driver;
import stanislav.vasilina.speditionclient.entity.Product;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.Route;

import static stanislav.vasilina.speditionclient.constants.Keys.HYPHEN;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;

public class ReportListAdapter extends ArrayAdapter<Report> {

    private final Context context;
    private final int resource;
    private final List<Report> reports;
    private LayoutInflater inflater;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public ReportListAdapter(@NonNull Context context, int resource, @NonNull List<Report> reports) {
        super(context, resource, reports);
        this.context = context;
        this.resource = resource;
        this.reports = reports;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = inflater.inflate(resource, parent, false);
        }

        final Report report = reports.get(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(report.getUuid());
            }
        });

        TextView dateView = view.findViewById(R.id.date);
        StringBuilder dateBuilder = new StringBuilder();

        if (report.getLeaveTime() != null) {
            simpleDateFormat.applyPattern("dd.MM.yy");
            dateBuilder.append(simpleDateFormat.format(report.getLeaveTime().getTime()));

        }
        if (report.getDoneDate() != null){
            dateBuilder.append(HYPHEN);
            dateBuilder.append(simpleDateFormat.format(report.getDoneDate().getTime()));
        }
        dateView.setText(dateBuilder.toString());

        final Driver driver = report.getDriver();
        if (driver != null){
            final TextView driverView = view.findViewById(R.id.driver);
            driverView.setText(driver.getValue());
        }

        final Route route = report.getRoute();
        if (route != null){
            final TextView routeView = view.findViewById(R.id.route);
            routeView.setText(route.getValue());
        }

        final Product product = report.getProduct();
        if (product != null){
            final TextView productView = view.findViewById(R.id.details);
            productView.setText(product.getName());
        }
        if (report.isSync()){
            view.setBackgroundColor(Color.GREEN);
        }
        return view;
    }

    private void open(String uuid) {
        Intent intent = new Intent(context, ReportEdit.class);
        intent.putExtra(ID, uuid);
        context.startActivity(intent);
    }
}
