package utils;

import entity.transport.DocumentNote;

import java.util.Comparator;

public class NotesComparator implements Comparator<DocumentNote> {
    @Override
    public int compare(DocumentNote documentNote, DocumentNote t1) {
        return documentNote.getTime().compareTo(t1.getTime());
    }
}
