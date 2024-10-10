/**
 * 字节序列密码算法
 */
public class RC4 {

    /**
     *
     * @param aInput 明文输入
     * @param aKey  短密钥用于产生无限长密钥流
     * @return
     */
    public static String myRC4(String aInput,String aKey)
    {
      int[] iS=new int [256]; //状态数组
        byte[] iK=new byte[256];//指定一个短密钥存储在key[MAX]数组中
        //初始化向量数组
        for(int i=0;i<256;i++)
        {
            iS[i]=i;
        }
        //短密钥存储在key[MAX]数组中
        for (short i=0;i<256;i++)
        {
            iK[i]=(byte) aKey.charAt((i%aKey.length()));

        }
        //利用密钥数组对状态数组重新排列
        int j=0;
        for (int i=0;i<255;i++)
        {
         j=(j+iS[i]+iK[i])%256;
         int temp=iS[i];
         iS[i]=iS[j];
         iS[j]=temp;
        }


        int i=0;
        j=0;
        char[] iInputChar=aInput.toCharArray();
        char[] iOutputChar=new char[iInputChar.length];

        for(short x=0;x<iInputChar.length;x++)
        {
            i=(i+1)%256;
            j=(j+iS[i])%256;
            int temp=iS[i];
            iS[i]=iS[j];
            iS[j]=temp;
            int t=(iS[i]+(iS[j]%256))%256;
            int iY=iS[t];
            char iCY=(char) iY; //密钥流
            //密钥流与明文异或运算
            iOutputChar[x]=(char) (iInputChar[x]^iCY);


        }
  return new String(iOutputChar);

    }


    public static void main(String[] args) {
        String inputStr="你真是个人才";
        String key="abcdefg";
        String str=myRC4(inputStr,key);
        System.out.println(str);
        System.out.println(myRC4(str,key));
    }
}
