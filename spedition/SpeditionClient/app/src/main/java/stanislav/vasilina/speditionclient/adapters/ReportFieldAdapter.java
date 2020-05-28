package stanislav.vasilina.speditionclient.adapters;

import android.content.Context;
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

public class ReportFieldAdapter extends ArrayAdapter<ReportField> {

    private final int resource;
    private final LayoutInflater inflater;
    private final FragmentManager fragmentManager;

    public ReportFieldAdapter(@NonNull Context context, int resource, FragmentManager fragmentManager) {
        super(context, resource);
        this.resource = resource;
        this.fragmentManager = fragmentManager;
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
                showDialog(item);
            }
        });
        final TextView index = view.findViewById(R.id.index);
        index.setText(String.valueOf(item.getIndex()));
        return view;
    }

    private void showDialog(ReportField item){
        final ReportFieldEditDialog reportFieldEditDialog = new ReportFieldEditDialog(item, inflater);
        reportFieldEditDialog.show(fragmentManager, "editField");

    }
}
