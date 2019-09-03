package ua.quasilin.chekmanszpt.utils;

import java.util.Random;

/**
 * Created by szpt_user045 on 03.09.2019.
 */

public class UIDGenerator {
    static Random random = new Random();
    public static void main(String[] args) {
        System.out.println(random.nextInt());
    }
}
