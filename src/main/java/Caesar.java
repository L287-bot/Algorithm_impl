import java.util.Scanner;

/**
 * 凯撒密码 移位解密转正+26而非绝对值
 */
public class Caesar {

    private  String str;
    private  int  key;

    public Caesar(String plaintext,int key) {
        this.str=plaintext;
        this.key=key;
    }

    public StringBuilder encryption(String plaintext,int key)
    {
        StringBuilder ciphertext=new StringBuilder();
        for(int i=0;i<plaintext.length();i++)
        {
            char oneChar=plaintext.charAt(i);
            if(Character.isLowerCase(oneChar))
            {
                int convert=oneChar-97;
                int index=(convert+key)%26;

                ciphertext.append((char) ('a'+index));


            }
            if (Character.isUpperCase(oneChar))
            {
                int convert=oneChar-65;
                int index=(convert+key)%26;

                ciphertext.append((char) ('A'+index));
            }
            if (!Character.isUpperCase(oneChar)&&!Character.isLowerCase(oneChar))
            {
                ciphertext.append(oneChar);
            }




        }
        return ciphertext;
    }

    public StringBuilder decrypt(String ciphertext,int key)
    {
        StringBuilder translation=new StringBuilder();
        for(int i=0;i<ciphertext.length();i++)
        {
            char oneChar=ciphertext.charAt(i);
            if(Character.isLowerCase(oneChar))
            {
               int convert=oneChar-97;
               int index=(convert-key+26)%26;

               translation.append((char) ('a'+index));


            }
            if (Character.isUpperCase(oneChar))
            {
                int convert=oneChar-65;
                int index=(convert-key+26)%26;

                translation.append((char) ('A'+index));
            }
            if (!Character.isUpperCase(oneChar)&&!Character.isLowerCase(oneChar))
            {
                translation.append(oneChar);
            }





        }
        return translation;
    }

    public static void main(String[] args) {
        System.out.println("请输入明文：");
        Scanner scanner=new Scanner(System.in);
        String plaintext=scanner.nextLine();
        System.out.println("请输入密钥(移位)：");
        int key=scanner.nextInt();
        Caesar caesar=new Caesar(plaintext,key);
        System.out.println("输入明文为:"+caesar.getStr()+",密钥(移位)为:"+caesar.getKey());
        System.out.println("加密密文为:"+caesar.encryption(caesar.getStr(), caesar.getKey()));
        System.out.println("解密密文为:"+caesar.decrypt(caesar.encryption(caesar.getStr(), caesar.getKey()).toString(), caesar.getKey()));


    }

    public  String getStr()
    {
        return str;
    }
    public int getKey()
    {
        return  key;
    }

}
