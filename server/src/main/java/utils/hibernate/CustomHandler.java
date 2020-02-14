package utils.hibernate;

import constants.Constants;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler implements Constants{

    static dbDAO dao = dbDAOService.getDAO();


    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();

//        SealBatch batch = dao.getObjectById(SealBatch.class, 114990);
//        for (int i = 0; i < 100; i++){
//            Seal seal = new Seal();
//            int num = 21213401 + i;
//            seal.setNumber(num);
//            seal.setBatch(batch);
//            seal.setValue("A " + num);
//            dao.save(seal);
//        }
//        String h = "-";
//        for (Seal s : instance.query(Seal.class, null)){
//            s.setValue(s.getPrefix() + h + s.getNumber());
//            instance.save(s);
//        }
//        PrinterUtil printerUtil = new PrinterUtil();
//        printerUtil.print("http://localhost:3332/app/api/v1/laboratory/oil/print");

//        for (SunProbe probe : dao.getObjects(SunProbe.class)){
//            probe.setDate(Date.valueOf(probe.getTurn().getTurn().getDate().toLocalDateTime().toLocalDate()));
//            dao.save(probe);
//        }
//        Date date = Date.valueOf(LocalDate.of(2019, 12, 21));
//        SunProbe sun = new SunProbe();
//
//        for (SunProbe probe : dao.findProbes(sun.getClass(), date, date, "Токарі")){
//            System.out.println(probe);
//        }

//        for (DocumentNote note : dao.getObjects(DocumentNote.class)){
//            Transportation transportation = note.getTransportation();
//            if (!U.exist(transportation.getUid())){
//                transportation.setUid(DocumentUIDGenerator.generateUID());
//                dao.save(transportation);
//            }
//            note.setDocument(transportation.getUid());
//            dao.save(note);
//        }

        HibernateSessionFactory.shutdown();
    }

    static String pretty(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
