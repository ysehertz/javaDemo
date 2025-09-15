import com.fuchen.ThreadPoolDemo;
import org.junit.jupiter.api.Test;

/**
 * ClassName: ThreadPoolDemoTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/14
 */

public class ThreadPoolDemoTest {
    @Test
    public void basicsThreadPoolTest(){
        new ThreadPoolDemo().basicsThreadPool();
    }

    @Test
    public void simpleThreadTest(){
        new Thread().start();
    }
}
