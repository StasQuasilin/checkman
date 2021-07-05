package utils;

import constants.Constants;
import entity.transport.DocumentNote;
import entity.transport.Driver;
import entity.transport.Transportation;

import java.util.List;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
public class NoteUtil implements Constants{

    PhoneCreateUtil phoneCreateUtil = new PhoneCreateUtil();
    private PhoneIdentifier phoneIdentifier = new PhoneIdentifier();
    private static final NotesComparator notesComparator = new NotesComparator();
    public static void sort(List<DocumentNote> notes) {
        notes.sort(notesComparator);
    }

    public synchronized String checkNote(Transportation transportation, String note){
        for (String s : note.split(SPACE)){
            String trim = s.trim();
            if (phoneIdentifier.identify(trim)){
                Driver driver = transportation.getDriver();
                if (driver != null){
                    phoneCreateUtil.createPhone(trim, driver.getPerson());
                    note = note.replace(trim, EMPTY);
                }
            }
        }
        return note;
    }
}
