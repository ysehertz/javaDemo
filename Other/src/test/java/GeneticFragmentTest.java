import org.junit.jupiter.api.Test;

/**
 * ClassName: GeneticFragmentTest
 * Package: PACKAGE_NAME
 * Description: 在学习基因分片算法时尝试其中的位运算操作
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/13
 */
public class GeneticFragmentTest {

    /**
     * 获取username的后10位哈希值（2进制的后10位）
     */
    @Test
    public void testGetUserNameHash(){
        String username = "fuchen";
        int hash = username.hashCode();
        int fragment = hash & 1023;
        System.out.println("UsernameHash: " + fragment);
        // 输出hash的二进制形式
        System.out.println("Hash in binary: " + Integer.toBinaryString(hash));
        System.out.println("Fragment in binary: " + Integer.toBinaryString(fragment));
    }
}
