/**
 * DES加密算法
 */
public class DES_test {

    // DES算法的初始置换表
    public static  int[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // DES算法的逆初始置换表
    public static  int[] IP_1 = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    // E扩展数组
    private   int[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    // P置换数组
    private int[] P = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };

    public static final int[][][] S_BOXES = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },

            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },

            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },

            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },

            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };
    //PC-1
    private int[] PC_1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    //PC-2
    private int[] PC_2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    private int[ ] LFT={1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

    /**
     * 加密轮数
     */
    private static  final int LOOP_NUM=16;
    private String[] keys=new String[LOOP_NUM];
//    private String[] pContent;
//    private String[] cContent;
    private int origin_length;
    /**
     * 16个子密钥
     */
    private int[][] sub_key=new int [16][48];
    private String content;
    private int p_origin_length;


    public DES_test(String key,String content)
    {
        this.content=content;
        //得到明文长度字节数，一字节为8bit
        p_origin_length=content.getBytes().length;
        //产生子密钥
        generateKeys(key);
    }

    /**
     * 将输入的字节数组分为8字节(64bit)的块进行处理，支持对数据的加密和解密。
     * @param p 输入的字节数组，表示需要处理的数据（明文或密文）。
     * @param flag 指示操作类型，0 表示解密，1 表示加密。
     * @return
     */
    public  byte[] deal(byte[] p,int flag)
    {
        origin_length=p.length;
        //组数
        int g_num;
        //需要填充数
        int r_num;
        //8个字节一组，即64bit
        g_num=origin_length/8;
        //计算需要填充数，只有小于等于的情况
        r_num=8-(origin_length-g_num*8);//8不填充
        byte[] p_padding;
        /**
         * 填充
         */
        if (r_num<8)
        {   //需要填充创建新的数组
            p_padding=new byte[origin_length+r_num];
            //复制明文数组byte[] p 到填充数组 p_padding
            System.arraycopy(p,0,p_padding,0,origin_length);
            //末尾进行填充
            for (int i=0;i<r_num;i++)
            {
                p_padding[origin_length+i]=(byte) r_num;
            }
        }else {
            p_padding=p;
        }
        //填充好的数组，再重新分组
        g_num=p_padding.length/8;
        byte[] f_p=new byte[8];
        //存储加密解密结果的数组 result_data。
        byte[] result_data=new byte[p_padding.length];
        for (int i=0;i<g_num;i++)
        {   //填充好的数组每8个字节分配到数组f_p 中进行加密解密处理
            System.arraycopy(p_padding,i*8,f_p,0,8);
            //对每个8字节组进行处理，调用 descryUnit() 方法进行加密或解密，结果存入 result_data。
            System.arraycopy(descryUnit(f_p,sub_key,flag),0,result_data,i*8,8);

        }
        if (flag==0)
        {
            //解密
            //原始明文长度的数组
            byte[] p_result_data=new byte[p_origin_length];
            //解密结束只复制结果数组result_data达到p_origin_length的长度，即去除填充的数组
            System.arraycopy(result_data,0,p_result_data,0,p_origin_length);
            return p_result_data;
        }
        return result_data;

    }

    /**
     * 对一个8字节的输入块进行加密或解密操作。
     * @param p 8字节的输入块（明文或密文）。
     * @param k 16个子密钥的二维数组，每个子密钥是48位。
     * @param flag 操作标志，0表示解密，1表示加密。
     * @return
     * descryUnit(f_p,sub_key,flag)
     */
    public byte[] descryUnit(byte[] p,int k[][],int flag)
    {   //8字节的字节数组（p）转换为一个64位的二进制表示，并将每一位存储在一个整数数组（p_bit）
        int [] p_bit=new int[64];
        // 创建一个StringBuilder用于构建二进制字符串
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<8;i++)
        {  // 将p中每个字节转换为无符号的二进制字符串
            String p_b=Integer.toBinaryString(p[i]&0xff);
            while (p_b.length()%8!=0)
            {  // 确保每个字节的二进制字符串长度为8位，不足则在前面补零
                p_b="0"+p_b;
            }
            // 将8位的二进制字符串追加到StringBuilder中
            stringBuilder.append(p_b);
        }
        String p_str=stringBuilder.toString();
        for (int i=0;i<64;i++)
        {   // 取出字符串中的每一位
            int p_t=Integer.valueOf(p_str.charAt(i));
            if (p_t==48){
                p_t=0;  // 如果是字符'0'（ASCII值48），则设置为0
            }else if (p_t==49)
            {
                p_t=1;// 如果是字符'1'（ASCII值49），则设置为1
            }else {
                System.out.println("To bit error!");
            }
            p_bit[i]=p_t;
        }

        /**
         * IP置换
         */
        int [] p_IP=new int[64];
        for (int i=0;i<64;i++)
        {   //举例，第58位数放在第一位，即p[0]换成p[58-1]
            p_IP[i]=p_bit[IP[i]-1];
        }
        if (flag==1)
        {
            for (int i=0;i<16;i++)
            {
                //进行IP置换后的数组进行16次 分两半，交换，执行轮函数
                L(p_IP,i,flag,k[i]);
            }

        }else if (flag==0)
        {
            for (int i=15;i>-1;i--)
            {

                L(p_IP,i,flag,k[i]);
            }
        }
        //逆置换
        int [] c=new int[64];
        for (int i=0;i<IP_1.length;i++)
        {
            c[i]=p_IP[IP_1[i]-1];
        }
        //创建一个长度为8的字节数组，因为64位数据可以分为8个字节
        //对于每个字节i，通过位移和加法操作将8个相应的位组合成一个字节。具体来说，c[8 * i]是最高位，依次向右合并直到最低位c[8 * i + 7]。
        byte [] c_byte=new byte[8];
        for (int i=0;i<8;i++)
        {   //对于每个字节i，通过位移和加法操作将8个相应的位组合成一个字节。具体来说，c[8 * i]是最高位，依次向右合并直到最低位c[8 * i + 7]。
            c_byte[i]=(byte) ((c[8*i]<<7)+(c[8*i+1]<<6)+(c[8*i+2]<<5)+(c[8*i+3]<<4)+(c[8*i+4]<<3)+(c[8*i+5]<<2)+(c[8*i+6]<<1)+(c[8*i+7]));

        }
        return c_byte;


    }

    /**
     *
     * @param M 传入的64位数据数组（经过初始置换或前一轮处理后的结果）。
     * @param times 当前的轮数（从0到15）。
     * @param flag 处理模式，1表示加密，0表示解密。
     * @param keyarray 当前轮次使用的子密钥。
     */
    public void L(int [] M ,int times,int flag,int[] keyarray)
    {
        int[] L0=new int[32];
        int[] R0=new int[32];
        int[] L1=new int[32];
        int[] R1=new int[32];
        int[] f=new int[32];
        //对半分成两组L0 R0
        System.arraycopy(M,0,L0,0,32);
        System.arraycopy(M,32,R0,0,32);
        //将 R0 赋值给 L1，这将在下一轮中用作新的左半部分。
        L1=R0;
        //调用轮函数 fFunction，传入当前右半部分 R0 和当前的子密钥 keyarray，计算出一个 32 位的结果 f。
        f=fFunction(R0,keyarray);
       //对 L0 和 f 进行异或运算，生成新的右半部分 R1。
        for (int j=0;j<32;j++)
        {
            R1[j]=L0[j]^f[j];
            if (((flag==0)&&(times==0)||((flag==1)&&(times==15))))
            {   //如果是最后一轮左右要交换
                M[j]=R1[j];
                M[j+32]=L1[j];
            }
            else {
                //左边32位L1，右边32位R1，拼会M[64]，继续再前面32后面32平均分成两组
                M[j]=L1[j];
                M[j+32]=R1[j];
            }
        }

    }

    /**
     * 轮函数
     * @param r_content 当前的右半部分（32 位数组）。
     * @param key 当前轮次的子密钥（48 位数组）。
     * @return
     */
    public int[] fFunction(int [] r_content,int [] key)
    {   //最终返回的 32 位结果数组。
        int [] result=new int[32];
        //48 位的扩展数据和密钥异或结果。
        int[] e_k=new int[48];
        /**拓展+异或运算*/
        for (int i=0;i<E.length;i++)
        {
            e_k[i]=r_content[E[i]-1]^key[i];
        }
        /**S盒替换**/
        int [][] s=new int[8][6];
        int [] s_after=new int[32];
        for (int i=0;i<8;i++)
        {
            System.arraycopy(e_k,i*6,s[i],0,6);
            int r=(s[i][0]<<1)+s[i][5];//横坐标
            int c=(s[i][1]<<3)+(s[i][2]<<2)+(s[i][3]<<1)+(s[i][4]);//纵坐标
            String str=Integer.toBinaryString(S_BOXES[i][r][c]);
            while (str.length()<4)
            {
                str="0"+str; // 确保长度为 4 位
            }

            for (int j=0;j<4;j++)
            {
                int p=Integer.valueOf(str.charAt(j));
                if (p==48)
                {
                    p=0;
                }else if (p==49)
                {
                    p=1;
                }else {
                    System.out.println("To bit error!");
                }
                s_after[4*i+j]=p; // 保存 S-盒输出
            }
        }
        /**S盒替换结束**/
        /**P盒替代**/
        for (int i=0;i<P.length;i++)
        {
            result[i]=s_after[P[i]-1];
        }
        return result;


    }

    /**
     * 产生子密钥
     * @param key
     */
    public void generateKeys(String key) {
//        如果输入的密钥长度小于 8，则重复附加密钥本身直到达到 8 个字符。
        while (key.length() < 8) {
            key = key + key;
        }
        //只保留前 8 个字符，确保密钥长度为 8 字节（64 位）。
        key = key.substring(0, 8);
        byte[] keys = key.getBytes();
        //初始化一个 64 位的数组 k_bit。
        //将每个字节转换为其 8 位二进制表示，填充至 8 位长，并存入 k_bit 数组。
        int[] k_bit = new int[64];
        for (int i = 0; i < 8; i++) {
            String k_str = Integer.toBinaryString(keys[i] & 0xff);
            if (k_str.length() < 8) {
                for (int t = 0; t < 8 - k_str.length(); t++) {
                    k_str = "0" + k_str;
                }
            }
            for (int j = 0; j < 8; j++) {
                int p = Integer.valueOf(k_str.charAt(j));
                if (p == 48) {
                    p = 0;
                } else if (p == 49) {
                    p = 1;
                } else {
                    System.out.println("To bit error!");
                }
                k_bit[i * 8 + j] = p;
            }
        }
        //通过选择置换表 PC_1，从 64 位的 k_bit 中提取出 56 位，存入 k_new_bit。PC-1 表定义了哪些位会被保留，哪些位会被丢弃。
        int[] k_new_bit = new int[56];
        for (int i = 0; i < PC_1.length; i++) {
            k_new_bit[i] = k_bit[PC_1[i] - 1];
        }



        /*****************/
        int[] c0 = new int[28];
        int[] d0 = new int[28];
        System.arraycopy(k_new_bit, 0, c0, 0, 28);
        System.arraycopy(k_new_bit, 28, d0, 0, 28);
        for (int i = 0; i < 16; i++) {
            int[] c1 = new int[28];
            int[] d1 = new int[28];
            if (LFT[i] == 1) {
//                如果 LFT[i] 是 1，则 c0 和 d0 各左移 1 位。
                System.arraycopy(c0, 1, c1, 0, 27);
                c1[27] = c0[0];
                System.arraycopy(d0, 1, d1, 0, 27);
                d1[27] = d0[0];
            } else if (LFT[i] == 2) {
                //如果 LFT[i] 是 2，则 c0 和 d0 各左移 2 位。
                System.arraycopy(c0, 2, c1, 0, 26);
                c1[26] = c0[0];
                c1[27] = c0[1];
                System.arraycopy(d0, 2, d1, 0, 26);
                d1[26] = d0[0];
                d1[27] = d0[1];
            } else {
                System.out.println("LFT Error!");
            }

//            将 c1 和 d1 组合成 56 位的临时数组 tmp。
            //根据置换表 PC_2 从 tmp 中提取出 48 位，并将其存入 sub_key 数组的对应子密钥中。
            int[] tmp = new int[56];
            System.arraycopy(c1, 0, tmp, 0, 28);
            System.arraycopy(d1, 0, tmp, 28, 28);
            for (int j = 0; j < PC_2.length; j++) {
                sub_key[i][j] = tmp[PC_2[j] - 1];
            }
            c0 = c1;
            d0 = d1;


        }


    }

    public static void main(String[] args) {
        String origin="Hello,DES";
        System.out.println("原文:\n"+origin);
        DES_test des_test=new DES_test("itpxw@qq.com",origin);
        byte[] c=des_test.deal(origin.getBytes(),1);
        System.out.println("密文\n"+new String(c));
        byte[] p=des_test.deal(c,0);
        byte [] p_d=new byte[origin.getBytes().length];
        System.arraycopy(p,0,p_d,0,origin.getBytes().length);
        System.out.println("明文\n"+new String(p));
    }

}
