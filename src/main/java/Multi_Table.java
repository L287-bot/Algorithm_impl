import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * 多表代换
 */
public class Multi_Table {



        public static void main(String[] args) {
            // 创建一个示例矩阵
            double[][] data = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 10}
            };

            // 使用MatrixUtils将数据转换为RealMatrix
            RealMatrix matrix = MatrixUtils.createRealMatrix(data);

            // 执行LU分解
            LUDecomposition luDecomposition = new LUDecomposition(matrix);

            // 创建逆矩阵解决器
            DecompositionSolver solver = new LUDecomposition(matrix).getSolver();

            // 计算逆矩阵
            RealMatrix inverseMatrix = solver.getInverse();

            // 输出逆矩阵
            System.out.println("逆矩阵为:");
            System.out.println(inverseMatrix.getData());
        }

}
