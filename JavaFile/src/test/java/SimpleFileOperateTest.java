import com.fuchen.OIN.SimpleFileOperate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * ClassName: SimpleFileOperate
 * Package: PACKAGE_NAME
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/15
 */
@Slf4j
public class SimpleFileOperateTest {
    @Test
    public void SimpleFileListTest(){
        SimpleFileOperate simpleFileOperate = new SimpleFileOperate("D:/forbid.txt");
        try {
            simpleFileOperate.GetFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void SimpleFileMapTest(){
        SimpleFileOperate simpleFileOperate = new SimpleFileOperate("D:/forbid.txt");
        try {
            Map<Integer, String> map = simpleFileOperate.GetFileToMap();
            map.entrySet().stream().forEach((k) -> log.info(String.valueOf(k.getKey()) + ":" + k.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
