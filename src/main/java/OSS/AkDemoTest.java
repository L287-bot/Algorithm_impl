package OSS;

import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;

public class AkDemoTest {
    public static void main(String[] args) throws Exception {
        // 从环境变量中获取凭证
        EnvironmentVariableCredentialsProvider credentialsProvider =  CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 使用credentialsProvider进行后续操作...
    }
}