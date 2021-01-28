package entity.laboratory.probes;

import entity.JsonAble;

/**
 * Created by szpt_user045 on 30.01.2020.
 */
public abstract class IProbe extends JsonAble{
    public abstract ProbeTurn getTurn();
}
