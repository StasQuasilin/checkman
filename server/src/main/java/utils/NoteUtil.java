package utils;

import constants.Constants;
import entity.transport.Driver;
import entity.transport.Transportation;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
public class NoteUtil implements Constants{

    PhoneCreateUtil phoneCreateUtil = new PhoneCreateUtil();
    private PhoneIdentifier phoneIdentifier = new PhoneIdentifier();

    public synchronized String checkNote(Transportation transportation, String note){
        System.out.println("Check note " + note);
        for (String s : note.split(SPACE)){
            String trim = s.trim();
            if (phoneIdentifier.identify(trim)){
                Driver driver = transportation.getDriver();
                if (driver != null){
                    phoneCreateUtil.createPhone(trim, driver.getPerson());
                    System.out.println("replace " + trim);
                    note = note.replace(trim, EMPTY);
                }
            }
        }
        System.out.println("result note " + note);
        return note;
    }
}
