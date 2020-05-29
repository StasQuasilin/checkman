package stanislav.vasilina.speditionclient.adapters;

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

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.dialogs.ReportFieldEditDialog;
import stanislav.vasilina.speditionclient.entity.ReportField;
import stanislav.vasilina.speditionclient.entity.Weight;
import stanislav.vasilina.speditionclient.utils.CustomListener;

import static stanislav.vasilina.speditionclient.constants.Keys.SPACE;

public class ReportFieldAdapter extends ArrayAdapter<ReportField> {

    private final int resource;
    private final LayoutInflater inflater;
    private final FragmentManager fragmentManager;
    private final CustomListener customListener;

    public ReportFieldAdapter(@NonNull Context context, int resource, FragmentManager fragmentManager, CustomListener customListener) {
        super(context, resource);
        this.resource = resource;
        this.fragmentManager = fragmentManager;
        this.customListener = customListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
        indexView.setText(resources.getString(R.string.pointN).toString() + (position + 1));

        final TextView counterpartyView = view.findViewById(R.id.counterparty);
        if (item.getCounterparty() != null) {
            counterpartyView.setText(item.getCounterparty());
        }

        StringBuilder builder = new StringBuilder();
        final Weight weight = item.getWeight();
        if (weight != null){
            builder.append(resources.getString(R.string.B));
            builder.append(weight.getGross());
            builder.append(resources.getString(R.string.T));
            builder.append(weight.getTare());
            final float net = weight.getNet();
            if (net > 0){
                builder.append(resources.getString(R.string.N));
                builder.append(net);
            }
        }

        final int money = item.getMoney();
        if (money != 0){
            builder.append(SPACE);
            builder.append(money);
        }

        final TextView detailView = view.findViewById(R.id.details);
        detailView.setText(builder.toString());

        return view;
    }

    private void edit(ReportField item){
        final ReportFieldEditDialog editDialog = new ReportFieldEditDialog(item, inflater, new CustomListener() {
            @Override
            public void onChange() {
                customListener.onChange();
            }
        });
        editDialog.show(fragmentManager, "Edit Field");
    }
}
