import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class RSA_Test {
    /**
     * 计算最大公约数
     * @param a
     * @param b
     * @return
     */
    public  static int gcd(int a,int b)
    {
        int t;
        while (b!=0)
        {
            t=a;
            a=b;
            b=t%b;
        }
        return a;
    }

    /**
     * 判断两个数是否互质
     * @param a
     * @param b
     * @return
     */
    public static boolean prime_w(int a,int b)
    {
        if(gcd(a,b)==1)
        {
            return true;
        }
        return false;
    }

    /**
     * 计算出e的模反元素d
     * @param a
     * @param r
     * @return
     */
    public  static  int mod_inverse(int a, int r)
    {
        int b=1;
        while (((a*b)%r)!=1)
        {
            b++;
            if (b<0)
            {
                System.out.println("error,function can't find b,and how b is negative number");
                return -1;
            }

        }
        return  b;
    }

    /**
     * 判断一个数是不是素数
     * @param i
     * @return
     */
    public  static boolean prime(int i)
    {
        if (i<=1)
        {
            return false;
        }
        for (int j=2;j<i;j++)
        {
            if (i%j==0)
            {
                return false;
            }
        }
        return true;
    }

    public  static void secret_key(int[] p,int[]q)
    {
        int rn;
        Random ra=new Random();
        // 生成第一个素数 p
        do {
            rn=ra.nextInt(50);// 生成一个随机数
            p[0]=rn+1;// 将该随机数加 1，得到 p
        }while (!prime(p[0]));// 确保 p 是素数，如果不是则重新生成
        // 生成第二个素数 q
        do {
            rn=ra.nextInt(50);// 生成一个新的随机数
            q[0]=rn%50+1;// 将该随机数对 50 取模后加 1，得到 q
        }while (p[0]==q[0]||!prime(q[0]));// 确保 q 是素数，并且 q 不等于 p

    }

    /**
     * 1 < e < φ(N) 且e与φ(N)互质
     * @param r
     * @return
     */
    public static int  getRand_e(int r)
    {
        int e=2;
        //1 < e < φ(N) 且e与φ(N)互质
        while (e<1||e>r||!prime_w(e,r))
        {
            e++;
            if (e<0)
            {
                System.out.println("error,function can't find e,and now e is negative number");
                return -1;
            }

        }
        return e;
    }

    /**
     *运用模运算的分配率 实现M^e % N  或者 C^d % N
     * @param a  底数  加解密字符
     * @param b  指数 e或者d
     * @param c  模数 n
     * @return
     */
    public  static  int rsa(int a,int b,int c)
    {
        int aa=a,r=1;
        //b减到0就会实现e或者d次幂
        while (b!=0)
        {  //模运算的分配率
            r=r*aa;
            r=r%c;
            b--;
        }
        return  r;
    }

    public static void main(String[] args) {


        int[]p={0};
        int[]q={0};
        int N,r,e,d;
        N=0; e=0;d=0;
        secret_key(p,q);//选择一对不相等且足够大的质数  其实自己填也行hhh
        N=p[0]*q[0];//计算模数n
        r=(p[0]-1)*(q[0]-1);//计算欧拉函数值φ(N)
        e=getRand_e(r);//输入φ(N)随机获取公钥指数e满足 1 < e < φ(N) 且e与φ(N)互质
        d=mod_inverse(e,r);//计算私钥指数d满足  (e*d)%φ(N)=1
        System.out.println("质数p:"+p[0]+','+"质数q:"+q[0]+','+"模数n:"+N+'\n'+"n的欧拉函数值φ(n):"+r+'\n'+"公钥指数e:"+e+'\n'+"私钥指数d:"+d);
        char mingwen,jiemi;
        int miwen;
        char[]mingwenStr=new char[1024],jiemiStr=new char[1024];
        int mingwenStrlen;
        int[]miwenBuff;
        System.out.println("请输入明文:");
        Scanner sc=new Scanner(System.in);
        String str= sc.next();
        //将输入的明文字符串 str 转换为字符数组 mingwenStr，以便逐个字符进行加密。
        mingwenStr=str.toCharArray();
        mingwenStrlen=mingwenStr.length;
        //miwenBuff 数组用于存储加密后的密文。
        miwenBuff=new int[mingwenStrlen];
        for (int i=0;i<mingwenStrlen;i++)
        {   //对每个字符进行加密的模幂运算
            //对于每个字符 mingwenStr[i]，先将它转为 ASCII 码（(int) mingwenStr[i]），然后调用 rsa 函数进行加密，使用公钥指数 e 和模数 N。
            miwenBuff[i]=rsa((int) mingwenStr[i],e,N);
        }
        for (int i=0;i<mingwenStrlen;i++)
        {   //对每个字符进行解密的模幂运算
            jiemiStr[i]=(char) rsa(miwenBuff[i],d,N);
        }
        System.out.println("明文长度:"+mingwenStrlen);
        System.out.println("加密密文:");
        //输出加密后的密文 miwenBuff
        for (int i=0;i<mingwenStrlen;i++)
        {
            System.out.print(miwenBuff[i]+" ");
        }
        System.out.println("\n解密结果:");
        //输出解密后的明文 jiemiStr
        for (int i=0;i<mingwenStrlen;i++)
        {
            System.out.print(jiemiStr[i]);
        }

    }



}
