package utils.answers;

import entity.answers.IAnswer;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public class SuccessAnswer extends IAnswer {

    @Override
    public String status() {
        return "success";
    }
}
