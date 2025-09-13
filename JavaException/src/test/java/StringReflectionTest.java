import com.fuchen.StringReflection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ClassName: StringReflectionTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/13
 */
@Slf4j
public class StringReflectionTest {
    private final StringReflection stringReflection = new StringReflection();

    @Test
    public void invokeValueOfTest(){
        Integer digit = 123;
        String stringValueOf = stringReflection.invokeValueOf(digit);
        assert stringValueOf != null;
        assert stringValueOf.equals(String.valueOf(digit));
        log.info("测试通过");
    }
}
