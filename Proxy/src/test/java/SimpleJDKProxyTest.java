import com.fuchen.simpleJDKProxy.LogHandler;
import com.fuchen.simpleJDKProxy.UserServiceImpl;
import com.fuchen.simpleJDKProxy.UserService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.nio.file.attribute.UserPrincipal;

/**
 * ClassName: SimpleJDKProxyTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/15
 */

public class SimpleJDKProxyTest {
    @Test
    public void testSimpleJDKProxy(){
        UserService target = new UserServiceImpl();

        UserService proxy = (UserService) Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class[]{UserService.class},
                new LogHandler(target)
        );

        proxy.save();
        proxy.delete();
        proxy.update();
    }
}
