package hemano.utils.misc;

/**
 * Created by hemantojha on 21/10/16.
 */
public class Random {

    public static String getRandomNumber(){
        java.util.Random rnd = new java.util.Random();
        int n = 100000 + rnd.nextInt(900000);
        return String.valueOf(n);
    }
}
