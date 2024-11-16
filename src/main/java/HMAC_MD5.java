import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HMAC_MD5 {
    /**
     * 计算参数md5信息
     * @param str 待处理的字节数组
     * @return md5摘要信息
     * @throws NoSuchAlgorithmException
     */
    private  static byte[] md5(byte[] str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
       md5.update(str);
       return md5.digest();
    }

    /**
     * 将待加密数据data通过密钥key使用HMAC-MD5算法进行加密，然后返回加密结果
     * @param key  密钥
     * @param data  待加密数据
     * @return  加密结果
     * @throws NoSuchAlgorithmException
     */
    public  static  byte[] getHmacMD5Bytes(byte[] key,byte[] data) throws NoSuchAlgorithmException {
        int length=64;
        byte[] ipad=new byte[length];
        byte[] opad=new byte[length];
        for (int i=0;i<64;i++)
        {
            ipad[i]=0x36;
            opad[i]=0x5C;
        }
        byte[] actualKey=key;
        byte[] keyArr = new byte[length];
        /**
         * 如果密钥长度大于64字节，就是用哈希算法计算其摘要作为真正的密钥
         */
        if (key.length>length)
        {
            actualKey=md5(key);
        }
        for (int i=0;i<actualKey.length;i++)
        {
            keyArr[i]=actualKey[i];
        }
        /**
         * 如果密钥长度不足64字节，就是用0x00补齐到64字节
         */
        if (actualKey.length<length)
        {
            for (int i= actualKey.length;i<keyArr.length;i++)
            {
                keyArr[i]=0x00;
            }

        }
        /**
         * 使用密钥和ipad进行异或运算
         */
        byte[] kIpadXorResult=new byte[length];
        for (int i=0;i<length;i++)
        {
            kIpadXorResult[i]=(byte) (keyArr[i]^ipad[i]);

        }
        /**
         * 将待加密数据追加到 k XOR ipad计算结果后面
         */
        byte[] firstAppendResult=new byte[kIpadXorResult.length+ data.length];

        for (int i=0;i< kIpadXorResult.length;i++)
        {
            firstAppendResult[i]=kIpadXorResult[i];
        }

        for (int i=0;i< data.length;i++)
        {
            firstAppendResult[i+ keyArr.length]=data[i];
        }
        /**
         * 使用哈希算法计算上面结果的摘要
         */
        byte[] firstHashResult = md5(firstAppendResult);
        /**
         * 使用密钥和opad进行异或运算
         */
        byte[] kOpadXorResult = new byte[length];
        for (int i=0;i<length;i++)
        {
            kOpadXorResult[i]=(byte) (keyArr[i]^opad[i]);
        }
        /**
         * 将H(K XOR ipad,text)结果追加到 K XOR opad后面
         */
        byte[] secondAppendResult=new  byte[kOpadXorResult.length+ firstHashResult.length];
        for (int i=0;i< kIpadXorResult.length;i++)
        {
            secondAppendResult[i]=kOpadXorResult[i];
        }
        for (int i=0;i< firstHashResult.length;i++)
        {
            secondAppendResult[i+keyArr.length]=firstHashResult[i];
        }
        /**
         * H(K XOR opad,H(K XOR ipad,text))
         * 对上面的数据进行哈希运算
         */
        byte[] hmacMd5bytes = md5(secondAppendResult);
        return hmacMd5bytes;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String key="123",data="abc";
        byte[] hmacMD5Bytes = getHmacMD5Bytes(key.getBytes(), data.getBytes());
        String strHexBytes = DatatypeConverter.printHexBinary(hmacMD5Bytes);
        System.out.println("HMAC-MD5:"+strHexBytes);

    }
}
