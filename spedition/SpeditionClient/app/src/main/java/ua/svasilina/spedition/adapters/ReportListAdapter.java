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
import ua.svasilina.spedition.activity.ReportShow;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.Route;

import static ua.svasilina.spedition.constants.Keys.EMPTY;
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
                open(report);
            }
        });

        TextView dateView = view.findViewById(R.id.date);
        StringBuilder dateBuilder = new StringBuilder();

        if (report.getLeaveTime() != null) {
            simpleDateFormat.applyPattern("dd.MM.yy");
            dateBuilder.append(simpleDateFormat.format(report.getLeaveTime().getTime()));
        }

        dateView.setText(dateBuilder.toString());
        final TextView check = view.findViewById(R.id.check);

        if (report.getDoneDate() != null){
            check.setVisibility(View.VISIBLE);
        } else {
            check.setVisibility(View.GONE);
        }

        final Driver driver = report.getDriver();
        final TextView driverView = view.findViewById(R.id.driver);
        if (driver != null){
            driverView.setText(driver.getValue().toUpperCase());
        } else {
            driverView.setText(EMPTY);
        }

        final Route route = report.getRoute();
        final TextView routeView = view.findViewById(R.id.route);
        if (route != null){
            routeView.setText(route.getValue());
        } else {
            routeView.setText(EMPTY);
        }

        final Product product = report.getProduct();
        final TextView productView = view.findViewById(R.id.details);
        if (product != null){
            productView.setText(product.getName());
        } else {
            productView.setText(EMPTY);
        }


        return view;
    }

    private void open(Report report) {

        Intent intent = new Intent();
        if (report.isDone()){
            intent.setClass(context, ReportShow.class);
        } else {
            intent.setClass(context, ReportEdit.class);
        }

        intent.putExtra(ID, report.getUuid());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
