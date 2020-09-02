package ua.s.vasilina.checkman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.s.vasilina.checkman.utils.AdapterViewBuilder;

public class SimpleAdapter<T> extends ArrayAdapter<T> {

    private final AdapterViewBuilder<T> builder;
    private final int resource;
    private final LayoutInflater inflater;

    public SimpleAdapter(@NonNull Context context, int resource, AdapterViewBuilder<T> builder) {
        super(context, resource);
        this.builder = builder;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final T item = getItem(position);
        final View view = convertView != null ? convertView : inflater.inflate(resource, null);
        return builder.build(item, view);
    }
}
