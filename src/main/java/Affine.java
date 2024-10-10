import java.util.Scanner;

/**
 * 仿射变换  【得到下标】
 */
public class Affine {
   //求最大公因子
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }


    public static StringBuilder Affine_encryption(int key_a,int key_b,String plaintext)
    {
        StringBuilder ciphertext=new StringBuilder();
        if(gcd(key_a,26)!=1)
        {
            System.out.println("密钥a不满足gcd(a,26)=1");
        }
        for(int i=0;i<plaintext.length();i++)
        {   char one_char= plaintext.charAt(i);
            if(Character.isLowerCase(one_char))
            {
                int convert=one_char-97; //把a~z ASCII值变成0-25

                int index=(key_a*convert+key_b)%26;
                ciphertext.append((char) ('a'+index));


            }
            if(Character.isUpperCase(one_char))
            {
                int convert=one_char-65; //把A~Z ASCII值变成0-25

                int index=(key_a*convert+key_b)%26;
                ciphertext.append((char)('A'+index));


            }
            if (!Character.isUpperCase(one_char)&&!Character.isLowerCase(one_char))
            {
                ciphertext.append(one_char);
            }
        }
        return ciphertext;
    }

    // 辅助函数，用于计算扩展欧几里得算法
    private static int extendedGCD(int a, int b, int[] xy) {
        if (a == 0) {
            xy[0] = 0;
            xy[1] = 1;
            return b;
        }

        int[] xy1 = new int[2];
        int gcd = extendedGCD(b % a, a, xy1);

        xy[0] = xy1[1] - (b / a) * xy1[0];
        xy[1] = xy1[0];

        return gcd;
    }

    // 主函数，用于计算模逆
    public static int modInverse(int a, int m) {
        int[] xy = new int[2];
        int gcd = extendedGCD(a, m, xy);

        if (gcd != 1) {
            // 如果gcd不是1，则没有逆元
            return -1;
        } else {
            // 保证结果为正数
            return (xy[0] % m + m) % m;
        }
    }
    public static StringBuilder  Affine_decryption(int key_a,int key_b,String ciphertext)
    {
        StringBuilder plaintext=new StringBuilder();
         int Inverse= modInverse(key_a,26);

        for (int i=0;i<ciphertext.length();i++)
        {
            char one_Char= ciphertext.charAt(i);
            if(Character.isLowerCase(one_Char))
            {
                int convert=one_Char-97;

                int absolute=convert-key_b+26;
                int index=(Inverse*absolute)%26;

                plaintext.append((char) ('a'+index));

            }
            if(Character.isUpperCase(one_Char))
            {
                double convert=one_Char-65;
                double absolute=convert-key_b+26;
                double index=(Inverse*absolute)%26;
                plaintext.append((char) ('A'+index));

            }
            if (!Character.isUpperCase(one_Char)&&!Character.isLowerCase(one_Char))
            {
                plaintext.append(one_Char);
            }

        }
        return plaintext;

    }


     public static void main(String[] args) {
         System.out.println("请输入明文：");
         Scanner scanner=new Scanner(System.in);
         String plaintext=scanner.nextLine();
         System.out.println("请输入密钥a：");
         int key_a=scanner.nextInt();
         System.out.println("请输入密钥b：");
         int key_b=scanner.nextInt();
         System.out.println("输入明文为:"+plaintext+",密钥a为:"+key_a+",密钥b为:"+key_b);
         System.out.println("加密密文为:"+Affine_encryption(key_a,key_b,plaintext));
         System.out.println("解密密文为:"+Affine_decryption(key_a,key_b,Affine_encryption(key_a,key_b,plaintext).toString()));


    }

}
