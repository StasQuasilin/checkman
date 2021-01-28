package utils;

import controllers.laboratory.laboratory.extraction.ExtractionTurnCellulose;
import entity.laboratory.subdivisions.extraction.*;
import entity.production.Turn;
import entity.transport.ActionTime;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.custom.CustomHibernator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Carrier2 {

    final CustomHibernator source;
    final CustomHibernator target;
    public static final String CONF_1 = "restored/hibernate.cfg.xml";
    public static final String CONF_2 = "hibernate.cfg.xml";

    public static void main(String[] args) {
        new Carrier2();
    }

    public Carrier2() {
        source = new CustomHibernator(CONF_2);
        target = new CustomHibernator(CONF_1);

        HashMap<String, Object> param = new HashMap<>();
        param.put("turn/date", new BETWEEN(
                Date.valueOf(LocalDate.of(2020, 6, 12)),
                        Date.valueOf(LocalDate.of(2020, 6, 13))
                        ));
        for (ExtractionTurn turn : source.query(ExtractionTurn.class, param)){
            final Turn targetTurn = getTargetTurn(turn.getTurn());
            final ExtractionTurn targetExtractionTurn = getTargetExtractionTurn(turn, targetTurn);
            ArrayList<ExtractionCrude> crudes = new ArrayList<>(turn.getCrudes());
            turn.setCrudes(null);


            System.out.println(turn.getTurn().getDate() + " #" + turn.getTurn().getNumber());

            for (ExtractionCrude crude : crudes){
                System.out.println("\t" + crude.getTime().toString());
                saveCrude(crude, targetExtractionTurn);
            }

            for (TurnCellulose cellulose : turn.getCellulose()){
                saveTurnCellulose(cellulose, targetExtractionTurn);
            }

            for (ExtractionOIl oil : turn.getOils()){
                saveOil(oil, targetExtractionTurn);
            }

            for (TurnProtein protein : turn.getTurnProteins()){
                saveTurnProtein(protein, targetExtractionTurn);
            }

            for (MealGranules granules : turn.getGranules()){
                saveGranules(granules, targetExtractionTurn);
            }

            for (StorageGrease grease : turn.getGreases()){
                saveStorageGrease(grease, targetExtractionTurn);
            }

            for (StorageProtein protein : turn.getProtein()){
                saveStorageProtein(protein, targetExtractionTurn);
            }
        }

        System.out.println("Done!");
        source.shutdown();
        target.shutdown();
    }

    private void saveStorageProtein(StorageProtein protein, ExtractionTurn targetExtractionTurn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("time", protein.getTime());
        final StorageProtein storageProtein = target.get(StorageProtein.class, params);
        if (storageProtein == null){
            protein.setId(0);
            protein.setTurn(targetExtractionTurn);
            checkTime(protein.getCreateTime());
            target.save(protein);
        }
    }

    private void saveStorageGrease(StorageGrease grease, ExtractionTurn targetExtractionTurn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("time", grease.getTime());
        final StorageGrease storageGrease = target.get(StorageGrease.class, params);
        if (storageGrease == null){
            grease.setId(0);
            grease.setTurn(targetExtractionTurn);
            checkTime(grease.getCreateTime());
            target.save(grease);
        }

    }

    private void saveGranules(MealGranules granules, ExtractionTurn turn) {
        granules.setId(0);
        granules.setTurn(turn);
        target.save(turn);
    }

    private void saveTurnProtein(TurnProtein protein, ExtractionTurn turn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("turn", turn.getId());
        final TurnProtein turnProtein = target.get(TurnProtein.class, params);
        if (turnProtein == null){
            protein.setId(0);
            protein.setTurn(turn);
            checkTime(protein.getCreateTime());
            target.save(protein);
        }

    }

    private void saveOil(ExtractionOIl oil, ExtractionTurn turn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("turn", turn.getId());
        final ExtractionOIl extractionOIl = target.get(ExtractionOIl.class, params);
        if (extractionOIl == null){
            oil.setId(0);
            oil.setTurn(turn);
            checkTime(oil.getCreateTime());
            target.save(oil);
        }
    }

    private void saveTurnCellulose(TurnCellulose cellulose, ExtractionTurn turn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("turn", turn.getId());
        final TurnCellulose turnCellulose = target.get(TurnCellulose.class, params);
        if (turnCellulose == null){
            cellulose.setId(0);
            cellulose.setTurn(turn);
            checkTime(cellulose.getCreateTime());
            target.save(cellulose);
        }

    }

    private void saveCrude(ExtractionCrude crude, ExtractionTurn turn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("time", crude.getTime());
        final ExtractionCrude extractionCrude = target.get(ExtractionCrude.class, params);
        if(extractionCrude == null){
            crude.setId(0);
            crude.setTurn(turn);
            checkTime(crude.getCreateTime());
            target.save(crude);
        }
    }

    private void checkTime(ActionTime actionTime) {
        if (actionTime != null){
            actionTime.setId(0);
            target.save(actionTime);
        }
    }

    private ExtractionTurn getTargetExtractionTurn(ExtractionTurn turn, Turn targetTurn) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("turn/date", turn.getTurn().getDate());
        final ExtractionTurn extractionTurn = target.get(ExtractionTurn.class, params);
        if (extractionTurn == null){
            turn.setId(0);
            turn.setTurn(targetTurn);
            target.save(turn);
            return turn;
        }
        return extractionTurn;
    }

    Turn getTargetTurn(Turn turn){
        HashMap<String, Object> params = new HashMap<>();
        params.put("date", turn.getDate());

        final Turn targetTurn = target.get(Turn.class, params);
        if (targetTurn == null){
            turn.setId(0);
            target.save(turn);
            return turn;
        }
        return targetTurn;
    }
}

