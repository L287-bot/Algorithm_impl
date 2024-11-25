import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Base64;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DSACoderTest {
    private  byte[] publicKey;
    private  byte[] privateKey;
   @BeforeEach
    public void initKey() throws Exception{
        Map<String, Object> keyMap = DSACoder.initKey();
        publicKey = DSACoder.getPublicKey(keyMap);
        privateKey = DSACoder.getPrivateKey(keyMap);
        System.err.println("公钥:\n"+ Base64.getEncoder().encodeToString(publicKey));
        System.err.println("私钥:\n"+Base64.getEncoder().encodeToString(privateKey));
    }

    @Test
    public void test() throws Exception{
        String inputStr = "DSA数字签名";
        byte[] data = inputStr.getBytes();
        byte[] sign = DSACoder.sign(data, privateKey);
        System.err.println("签名:\n"+Base64.getEncoder().encodeToString(sign));
        boolean status = DSACoder.verify(data, publicKey, sign);
        System.err.println("状态:\n"+status);
         assert status;
    }
}
