import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public abstract class DSACoder {
//数字签名密钥算法
    public final static String ALGORITHM = "DSA";
    /**
     * 数字签名
     * 签名/验证算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";
     //公钥
    private static final String PUBLIC_KEY = "DSAPublicKey";
    //私钥
    private static final String PRIVATE_KEY = "DSAPrivateKey";
    /**
     * DSA密钥长度
     * 默认1024位
     * 密钥长度必须是64的倍数
     *
     */
    private static  final int KEY_SIZE = 1024;

    /**
     * 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return byte[] 数字签名
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     * @throws InvalidKeyException
     */
    public static byte[] sign(byte[] data,byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        //还原私钥
        //转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        //生成私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        //初始化Signature
        signature.initSign(priKey);
        //更新
        signature.update(data);
        //签名
        return signature.sign();

    }

    public  static boolean verify(byte[] data,byte[] publicKey,byte[] sign) throws Exception {
        X509EncodedKeySpec KeySpec = new X509EncodedKeySpec(publicKey);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        PublicKey pubKey    = keyFactory.generatePublic(KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }
    public static Map<String,Object> initKey() throws Exception{

        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
        keygen.initialize(KEY_SIZE,new SecureRandom());
        KeyPair keys = keygen.generateKeyPair();
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey)keys.getPrivate();
        HashMap<String, Object> map = new HashMap<>(2);
        map.put(PUBLIC_KEY,publicKey);
        map.put(PRIVATE_KEY,privateKey);
        return map;

    }

    public static byte[] getPrivateKey(Map<String,Object> keyMap) throws Exception{
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{
        Key key = (Key)keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }
}
