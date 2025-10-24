package com.fuchen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ClassName: ChuangTest
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/17
 */
@Slf4j
public class ChuangTest {
    @Test
    public void testGetGrade_Excellent() {
        // 测试优秀等级：single_Max >= 5 或 sum >= 7
        Chuang chuang1 = new Chuang(5.0, 6.0); // single_Max >= 5
        assertEquals("优秀", chuang1.getGrade());

        Chuang chuang2 = new Chuang(4.0, 7.0); // sum >= 7
        assertEquals("优秀", chuang2.getGrade());
    }

    @Test
    public void testGetGrade_Good() {
        // 测试良好等级：single_Max >= 4 或 sum >= 6
        Chuang chuang1 = new Chuang(4.0, 5.0); // single_Max >= 4
        assertEquals("良", chuang1.getGrade());

        Chuang chuang2 = new Chuang(3.0, 6.0); // sum >= 6
        assertEquals("良好", chuang2.getGrade());
    }

    @Test
    public void testGetGrade_Average() {
        // 测试中等等级：(single_Max >= 3 && sum >= 4) 或 sum >= 5
        Chuang chuang1 = new Chuang(3.0, 4.0); // single_Max >= 3 && sum >= 4
        assertEquals("中等", chuang1.getGrade());

        Chuang chuang2 = new Chuang(2.0, 5.0); // sum >= 5
        assertEquals("中等", chuang2.getGrade());
    }

    @Test
    public void testGetGrade_Pass() {
        // 测试及格等级：sum >= 4
        Chuang chuang = new Chuang(2.0, 4.0);
        assertEquals("及格", chuang.getGrade());
    }

    @Test
    public void testGetGrade_Fail() {
        // 测试不及格等级：不满足以上任何条件
        Chuang chuang = new Chuang(2.0, 3.0);
        assertEquals("不及格", chuang.getGrade());
    }
}
