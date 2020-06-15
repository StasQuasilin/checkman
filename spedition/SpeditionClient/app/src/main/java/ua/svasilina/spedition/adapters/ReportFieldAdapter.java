package ua.svasilina.spedition.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.dialogs.ReportFieldEditDialog;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.ReportField;
import ua.svasilina.spedition.entity.Weight;
import ua.svasilina.spedition.utils.CustomListener;

import static ua.svasilina.spedition.constants.Keys.CURRENCY_SIGN;
import static ua.svasilina.spedition.constants.Keys.QUESTION;
import static ua.svasilina.spedition.constants.Keys.SPACE;

public class ReportFieldAdapter extends ArrayAdapter<ReportField> {

    private final int resource;
    private final LayoutInflater inflater;
    private final FragmentManager fragmentManager;
    private final CustomListener customListener;
    @SuppressLint("SimpleDateFormat")
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private final Report report;

    public ReportFieldAdapter(@NonNull Context context, int resource, FragmentManager fragmentManager, Report report, CustomListener customListener) {
        super(context, resource);
        this.resource = resource;
        this.fragmentManager = fragmentManager;
        this.report = report;
        this.customListener = customListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ReportField item = getItem(position);
        final View view = convertView != null ? convertView : inflater.inflate(resource, parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(item);
            }
        });

        final Resources resources = getContext().getResources();

        final TextView indexView = view.findViewById(R.id.index);
        indexView.setText(resources.getString(R.string.pointN) + (position + 1));

        final TextView timeView = view.findViewById(R.id.timeView);

        assert item != null;
        final Calendar arriveTime = item.getArriveTime();
        if (arriveTime != null) {
            simpleDateFormat.applyPattern("dd.MM.yy HH:mm");
            timeView.setText(simpleDateFormat.format(arriveTime.getTime()));
        } else {
            timeView.setVisibility(View.GONE);
        }

        final TextView counterpartyView = view.findViewById(R.id.counterparty);
        if (item.getCounterparty() != null) {
            counterpartyView.setText(item.getCounterparty().toUpperCase());
        } else {
            counterpartyView.setText(QUESTION);
        }

        StringBuilder builder = new StringBuilder();
        final Weight weight = item.getWeight();
        if (weight != null) {
            builder.append(resources.getString(R.string.B));
            builder.append(weight.getGross());
            builder.append(SPACE);
            builder.append(resources.getString(R.string.T));
            builder.append(weight.getTare());
            builder.append(SPACE);
            final float net = weight.getNet();
            if (net > 0) {
                builder.append(resources.getString(R.string.N));
                builder.append(net);
            }
        }

        final int money = item.getMoney();
        if (money != 0) {
            builder.append(SPACE);
            builder.append(money).append(CURRENCY_SIGN);
        }

        final TextView detailView = view.findViewById(R.id.details);
        final String details = builder.toString();
        if (details.isEmpty()) {
            detailView.setVisibility(View.GONE);
        } else {
            detailView.setText(details);
        }


        return view;
    }

    private void edit(ReportField item){
        final ReportFieldEditDialog editDialog = new ReportFieldEditDialog(item, inflater, report, new CustomListener() {
            @Override
            public void onChange() {
                customListener.onChange();
            }
        });
        editDialog.show(fragmentManager, "Edit Field");
    }
}
