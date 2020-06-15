package ua.svasilina.spedition.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.activity.ReportEdit;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.Route;

import static ua.svasilina.spedition.constants.Keys.HYPHEN;
import static ua.svasilina.spedition.constants.Keys.ID;

public class ReportListAdapter extends ArrayAdapter<Report> {

    private final Context context;
    private final int resource;
    private final List<Report> reports;
    private LayoutInflater inflater;
    @SuppressLint("SimpleDateFormat")
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


        byte b = 0;
        if (report.getLeaveTime() != null) {
            simpleDateFormat.applyPattern("dd.MM.yy");
            dateBuilder.append(simpleDateFormat.format(report.getLeaveTime().getTime()));
            b++;
        }
        if (report.getDoneDate() != null){
            dateBuilder.append(HYPHEN);
            dateBuilder.append(simpleDateFormat.format(report.getDoneDate().getTime()));
            b++;
        }
        dateView.setText(dateBuilder.toString());
        final TextView check = view.findViewById(R.id.check);
        if (b == 2){
            check.setVisibility(View.VISIBLE);
        } else {
            check.setVisibility(View.GONE);
        }

        final Driver driver = report.getDriver();
        if (driver != null){
            final TextView driverView = view.findViewById(R.id.driver);
            driverView.setText(driver.getValue().toUpperCase());
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

        return view;
    }

    private void open(String uuid) {
        Intent intent = new Intent(context, ReportEdit.class);
        intent.putExtra(ID, uuid);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
