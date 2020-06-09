package stanislav.vasilina.speditionclient.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.NoteAdapter;
import stanislav.vasilina.speditionclient.entity.ReportNote;
import stanislav.vasilina.speditionclient.utils.CustomListener;

public class NoteEditDialog extends DialogFragment {
    private final Context context;
    private final ArrayList<ReportNote> notes;
    private final LayoutInflater inflater;
    private final CustomListener listener;
    private EditText noteEdit;
    private int currentItem = -1;
    ArrayAdapter<ReportNote> adapter;

    public NoteEditDialog(Context context, ArrayList<ReportNote> notes, LayoutInflater inflater, CustomListener listener) {
        this.context = context;
        this.notes = notes;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = inflater.inflate(R.layout.note_edit_dialog, null);

        noteEdit = view.findViewById(R.id.noteEdit);

        final ImageButton saveNote = view.findViewById(R.id.saveNote);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        adapter = new NoteAdapter(context, R.layout.note_view, inflater);
        adapter.addAll(notes);

        final ListView noteList = view.findViewById(R.id.noteList);
        noteList.setAdapter(adapter);



        builder.setTitle(R.string.notes);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void saveNote() {
        final String noteText = noteEdit.getText().toString();
        if (!noteText.isEmpty()){
            ReportNote note = null;
            if (currentItem != -1){
                note = adapter.getItem(currentItem);
            }

            if (note == null){
                note = new ReportNote();
                note.setUuid(UUID.randomUUID().toString());
                note.setNote(noteText);
                note.setTime(Calendar.getInstance());
            }

            note.setNote(noteText);
            if (currentItem != -1){
                adapter.remove(note);
                adapter.insert(note, currentItem);
                currentItem = -1;
            } else {
                adapter.add(note);
            }
            noteEdit.getText().clear();
        }
    }

    private void save() {
        saveNote();
        notes.clear();
        for (int i = 0; i < adapter.getCount(); i++){
            notes.add(adapter.getItem(i));
        }
        listener.onChange();
    }
}
