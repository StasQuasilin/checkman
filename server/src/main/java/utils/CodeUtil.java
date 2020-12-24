package utils;

import entity.User;
import org.apache.log4j.Logger;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class CodeUtil {

    private static final int[] coefficient1 = {1, 2, 3, 4, 5, 6, 7};
    private static final int[] coefficient2 = {7, 1, 2, 3, 4, 5, 6};
    private static final int[] coefficient3 = {-1, 5, 7, 9, 4, 6, 10, 5, 7};

    private static final Logger log = Logger.getLogger(CodeUtil.class);

    public static boolean validCode(String codeString) {
        long code = Long.parseLong(codeString);
        int[] coefficient;
        if (codeString.length() == 8) {
            if (code >= 30000000 && code <= 60000000) {
                coefficient = coefficient2;
            } else {
                coefficient = coefficient1;
            }
        } else {
            coefficient = coefficient3;
        }
        ArrayList<Integer> integers = DecomposeNumber(code);
        while (integers.size() < codeString.length()){
            integers.add(0, 0);
        }

        int result = checkCode(integers, coefficient);
        if (result > 9){
            for(int i = 0; i < coefficient.length; i++){
                coefficient[i] += 2;
            }
            result = checkCode(integers, coefficient);
        }
        boolean valid = result == integers.get(integers.size() - 1);
        log.info("Code " + code + " is " + (valid ? "valid" : "invalid"));

        return valid;
    }

    private static int checkCode(ArrayList<Integer> integers, int[] coefficient){
        int result = 0;
        for (int i = 0; i < integers.size() - 1; i++){
            result += integers.get(i) * coefficient[i];
        }
        return result % 11;
    }

    public static ArrayList<Integer> DecomposeNumber(long number){
        ArrayList<Integer> numbers = new ArrayList<>();
        DecomposeNumber(number, 1, numbers);
        return numbers;
    }

    private static void DecomposeNumber(long num, int divider, ArrayList<Integer> numbers){
        if (divider > 0) {
            long div = num / divider;
            if (div > 9) {
                DecomposeNumber(num, divider * 10, numbers);
            } else {
                numbers.add((int) div);
                DecomposeNumber(num - div * divider, divider / 10, numbers);
            }
        }
    }

    public static void main(String[] args) {
        Crypto crypto = new Crypto();
        Hibernator hibernator = Hibernator.getInstance();
        final Base64.Decoder decoder = Base64.getDecoder();
        for (User user : hibernator.query(User.class, null)){
            final String password = user.getPassword();
            final String decode = new String(decoder.decode(password));
            System.out.print(decode + ": ");
            final String md5 = crypto.md5(decode);
            user.setPasswordHash(md5);
            hibernator.save(user);
            System.out.println("Set hash "+ md5 + " for " + user.getWorker().getValue() );
        }
        HibernateSessionFactory.shutdown();
    }

    private static void check(String code){
    }
}
