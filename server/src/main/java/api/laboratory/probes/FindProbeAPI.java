package api.laboratory.probes;

import api.ServletAPI;
import constants.Branches;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.probes.SunProbe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szpt_user045 on 30.01.2020.
 */
@WebServlet(Branches.API.PROBE_FIND)
public class FindProbeAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Date from = parseDate(body.get(FROM));
            Date to = parseDate(body.get(TO));
            String organisation = String.valueOf(body.get(ORGANISATION));

            ArrayList<ProbeTurn> probeTurns = new ArrayList<>();

            compileSun(probeTurns, getProbes(SunProbe.class, from, to, organisation));
            compileOil(probeTurns, getProbes(OilProbe.class, from, to, organisation));

            JSONArray array = pool.getArray();
            for (ProbeTurn turn : probeTurns){
                array.add(turn.toJson());
            }

            write(resp, array.toJSONString());
            pool.put(array);
        }
    }

    synchronized Date parseDate(Object json){

        if (json != null){
            return Date.valueOf(String.valueOf(json));
        }

        return null;
    }

    synchronized void compileSun(ArrayList<ProbeTurn> turns, List<SunProbe> probes){
        for (SunProbe probe : probes){
            ProbeTurn turn = probe.getTurn();

            if (!turns.contains(turn)){
                turn.getSunProbes().clear();
                turns.add(turn);
            }
            ProbeTurn probeTurn = turns.get(turns.indexOf(turn));
            probeTurn.getSunProbes().add(probe);
        }
    }

    synchronized void compileOil(ArrayList<ProbeTurn> turns, List<OilProbe> probes){
        for (OilProbe probe : probes){
            ProbeTurn turn = probe.getTurn();

            if (!turns.contains(turn)){
                turn.getOilProbes().clear();
                turns.add(turn);
            }
            ProbeTurn probeTurn = turns.get(turns.indexOf(turn));
            probeTurn.getOilProbes().add(probe);
        }
    }

    synchronized <T> List<T> getProbes(Class<T> tClass, Date from, Date to, String organisation){
        return dao.findProbes(tClass, from, to, organisation);
    }
}
