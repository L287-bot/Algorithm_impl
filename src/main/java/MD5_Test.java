import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5_Test {
    public  static  String encryptMode(String str,String mode) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(mode);
        md5.update(str.getBytes());
        byte[] bt = md5.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<bt.length;i++)
        {
            stringBuffer.append(Character.forDigit((bt[i]&240)>>4,16));
            stringBuffer.append(Character.forDigit(bt[i]&15,16));
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str="abc";
        System.out.println("MD5哈希结果为："+encryptMode(str,"MD5"));
    }
}
