package entity.answers;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class ErrorAnswer extends IAnswer {

    @Override
    public String status() {
        return "error";
    }
}
