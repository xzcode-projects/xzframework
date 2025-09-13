import java.math.BigInteger;

public class TestNumber {


    //    private static final Long MAX_SAFE_INTEGER = 9007199254740991L;
//    private static final long MAX_SAFE_INTEGER = 9000000000000000L;
//    //    private static final Long MIN_SAFE_INTEGER = -9007199254740991L;
//    private static final long MIN_SAFE_INTEGER = -9000000000000000L;


    public static void main(String[] args) {
        BigInteger MAX_SAFE_INTEGER = new BigInteger("9000000000000000");
        BigInteger MIN_SAFE_INTEGER = new BigInteger("-9000000000000000");
        BigInteger value = new BigInteger("90000000000000");


        System.out.println(value.compareTo(MIN_SAFE_INTEGER));
        System.out.println(value.compareTo(MAX_SAFE_INTEGER));
        if (value == null) {
            System.out.println("write null");
        } else if (value.compareTo(MIN_SAFE_INTEGER) > 0 && value.compareTo(MAX_SAFE_INTEGER) < 0) {
            System.out.println("write number");
        } else {
            System.out.println("write string");
        }
    }
}
