import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
public class Virginia_Test {
    public static void main(String[] args) {

        for (int d = 0; d <= 5; d++) {
            long totalTime = 0;
            int trials = 3; // 三组实验
            for (int i = 0; i < trials; i++) {
                long startTime = System.nanoTime();
                mine(d);
                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            double averageTime = totalTime / (double) trials;
            System.out.printf("难度为:%d, 平均时间: %.2f ms%n", d, averageTime/1000000);
        }
    }

    private static void mine(int difficulty) {
        Random random = new Random();
        int x;
        String hash;
        String targetPrefix = new String(new char[difficulty]).replace('\0', '0');

        do {
            x = random.nextInt(); // 随机生成整数
            hash = sha256(Integer.toString(x));
        } while (!hash.startsWith(targetPrefix));
    }

    private static String sha256(String input) {
        try {
            //创建一个 SHA-256 的实例,指定使用 SHA-256 算法。
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //输入字符串转换为字节数组
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);//0xff & b 确保字节 b 被转换为无符号整数，以便正确处理负值。
                if (hex.length() == 1) {
                    hexString.append('0');
                    hexString.append(hex);
                }
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
