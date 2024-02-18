import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Encryption {
    public String Encrypt(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte [] message= messageDigest.digest(input.getBytes());
        BigInteger bigInteger= new BigInteger(1,message);
        return bigInteger.toString(16);
    }
}
