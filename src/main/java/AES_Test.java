import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


/**
 * AES算法加密过程
 * 初始化密钥[可以自己写死]，实例化keyGenerator对象，用于生成一个新的AES对称密钥key对象，并将其转化为二进制密钥字节数组
 * 加密：
 * 还原密钥：使用SecretKeySpec(key,KEY_ALGORITHM);根据给定的密钥二进制字节数组创建还原出密钥key对象。
 * 根据算法名称实例化Cipher对象
 * cipher.init(Cipher.ENCRYPT_MODE,k);传入密钥对象key以及工作模式初始化cipher对象
 * Cipher在调用doFinal方法时就会根据算法名称，工作模式，密钥长度完成多轮的加密解密工作
 *
 *
 */
public class AES_Test {
    /**
     * 密钥算法
     */
    public  static  final  String KEY_ALGORITHM="AES";

    /**
     * 加密解密算法/工作模式/填充方式
     */
    public  static  final  String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";

    /**
     * 生成密钥
     * @return byte[] 二进制密钥
     * @throws Exception
     * 实例化keyGenerator对象，用于生成一个新的AES对称密钥key对象，并将其转化为二进制密钥字节数组
     */
    public  static  byte[] initKey() throws Exception
    {   //实例化
        KeyGenerator keyGenerator=KeyGenerator.getInstance(KEY_ALGORITHM);
        //AES密钥长度
        keyGenerator.init(256);
        //调用此方法生成一个新的对称密钥（在本例中为 AES 密钥）对象
        SecretKey secretKey=keyGenerator.generateKey();
        //返回密钥的字节数组表示
        return secretKey.getEncoded();
    }


    /**
     * 转换密钥
     * @param key 二进制密钥
     * @return Key
     * 使用SecretKeySpec(key,KEY_ALGORITHM);根据给定的密钥字节数组创建还原出密钥key对象。，用于后续加密解密
     */
    private static Key toKey(byte[] key)
    {
        //实例化AES密钥
        //SecretKeySpec: 这是 javax.crypto.spec 包中的一个类，用于根据给定的密钥字节数组创建密钥对象。
        SecretKey secretKey=new SecretKeySpec(key,KEY_ALGORITHM);
        return  secretKey;
    }

    /**
     * 解密
     * @param data 待解密数据
     * @param key  二进制密钥
     * @return 解密数据
     */
    public  static  byte[] decrypt(byte[] data,byte[] key) throws Exception {
        //还原密钥
        Key k=toKey(key);
        //实例化
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE,k);
        //执行操作
        return cipher.doFinal(data);

    }

    /**
     * 加密
     * @param data 待加密数据
     * @param key  二进制密钥
     * @return 加密数据
     */
    public  static  byte[] encrypt(byte[] data,byte[] key) throws Exception {
        //还原密钥
        Key k=toKey(key);
        //实例化，
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE,k);
        //执行操作
        return cipher.doFinal(data);

    }

    public static void main(String[] args) throws Exception {
        String inputStr="AES";
        byte[] inputData=inputStr.getBytes();
        System.err.println("原文:\t"+inputStr);
        //初始化密钥
        byte[] key=AES_Test.initKey();
        System.err.println("密钥:\t"+ Base64.encodeBase64String(key));
        //加密
        byte[] encryption=AES_Test.encrypt(inputData,key);
        System.err.println("加密密文:\t"+ Base64.encodeBase64String(encryption));
        //解密
        byte[] outputData=AES_Test.decrypt(encryption,key);
        String decryption =new String(outputData);
        System.err.println("解密密文:\t"+ decryption);


    }





}
