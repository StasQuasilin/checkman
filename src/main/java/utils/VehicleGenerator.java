package utils;


import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.Random;

/**
 * Created by szpt_user045 on 24.04.2019.
 */
public class VehicleGenerator {
    final static String[] models = {"isuzu", "iveco", "man", "mersedes", "renault",
            "scania", "volvo", "daf"};
    final static String[] regions = {"АВ", "АС", "АЕ", "АН", "АМ", "АО", "АР",
            "АТ", "АА", "АI", "ВА", "ВВ", "ВС", "ВЕ", "ВН", "ВI", "ВК", "СН",
            "ВМ", "ВО", "АХ", "ВТ", "ВХ", "СА", "СВ", "СЕ"};
    final static Random random = new Random();

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(models[random.nextInt(models.length - 1)]);
        for (int i = 0; i < 1 + random.nextInt(2); i++) {
            builder.append(" ").append(generateNumber());
        }
        System.out.println(builder.toString());
    }

    static String generateNumber(){
        StringBuilder builder = new StringBuilder();
        builder.append(regions[random.nextInt(regions.length - 1)]);

        for (int i = 0; i < 4 + random.nextInt(2); i++){
            builder.append(random.nextInt(9));
        }

        builder.append(regions[random.nextInt(regions.length- 1)]);
        return builder.toString();
    }
}
