package utils.transport;

import entity.Worker;
import entity.transport.DocumentNote;
import entity.transport.Transportation;
import utils.NoteUtil;
import utils.U;
import utils.hibernate.Hibernator;
import utils.json.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static constants.Constants.ID;
import static constants.Constants.NOTE;

public class NoteEditor {
    private final Hibernator hibernator = Hibernator.getInstance();
    private final NoteUtil noteUtil = new NoteUtil();
    private final Transportation transportation;
    private List<DocumentNote> notes;
    final HashMap<Integer, DocumentNote> noteMap = new HashMap<>();

    public NoteEditor(Transportation transportation) {
        this.transportation = transportation;
        this.notes = transportation.getNotes();
        if (notes != null){
            initNotes();
        } else {
            notes = new LinkedList<>();
        }

    }

    public void initNotes() {
        for (DocumentNote note : notes) {
            noteMap.put(note.getId(), note);
        }
        notes.clear();
    }

    public boolean saveNote(JsonObject note, Worker worker) {
        final int noteId = note.getInt(ID);
        DocumentNote documentNote = noteMap.remove(noteId);
        if (documentNote == null){
            documentNote = new DocumentNote(transportation, worker);
            documentNote.setDocument(transportation.getUid());
        }
        String value = String.valueOf(note.get(NOTE));
        boolean saveNote = false;
        boolean save = false;
        String s = noteUtil.checkNote(transportation, value).trim();
        if (U.exist(s)){
            documentNote.setNote(s);
            saveNote = true;
        } else {
            if (documentNote.getId() > 0){
                hibernator.remove(documentNote);
                save = true;
            }
        }
        if (saveNote) {
            hibernator.save(documentNote);
            notes.add(documentNote);
        }
        return saveNote || save;
    }

    public void clear() {
        for (Map.Entry<Integer, DocumentNote> entry : noteMap.entrySet()){
            final DocumentNote note = entry.getValue();
            if (note.getCreator() != null){
                hibernator.remove(note);
                noteMap.remove(entry.getKey());
            }
        }
    }

    public List<DocumentNote> getNotes() {
        notes.addAll(noteMap.values());
        return notes;
    }
}
