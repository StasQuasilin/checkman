package stanislav.vasilina.speditionclient.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import stanislav.vasilina.speditionclient.R;

public class DateDialog extends DialogFragment {

    private final LayoutInflater inflater;
    private final Calendar calendar;
    private final Calendar innerCalendar;
    private final DateDialogState state;
    private final View.OnClickListener onClickListener;

    DateDialog(Calendar calendar, LayoutInflater inflater, DateDialogState state, View.OnClickListener onClickListener) {
        this.calendar = calendar;
        this.inflater = inflater;
        this.state = state;
        this.onClickListener = onClickListener;
        innerCalendar = (Calendar) calendar.clone();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });

        final View view = inflater.inflate(R.layout.date_picker_dialog, null);
        final CalendarView calendarView = view.findViewById(R.id.calendarView);

        if (state == DateDialogState.date) {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    innerCalendar.set(year, month, dayOfMonth);
                }
            });
        } else {
            calendarView.setVisibility(View.GONE);
        }
        final TimePicker timePicker = view.findViewById(R.id.timePicker);
        if (state == DateDialogState.time) {
            timePicker.setIs24HourView(true);
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    innerCalendar.set(Calendar.HOUR, hourOfDay);
                    innerCalendar.set(Calendar.MINUTE, minute);
                }
            });
        } else {
            timePicker.setVisibility(View.GONE);
        }



        builder.setView(view);
        return builder.create();
    }

    private void cancel() {

    }

    private void save() {
        calendar.setTime(innerCalendar.getTime());
        onClickListener.onClick(null);
    }
}
