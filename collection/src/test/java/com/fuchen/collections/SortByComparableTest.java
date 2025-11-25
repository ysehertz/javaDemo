package com.fuchen.collections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: SortByComparableTest
 * Package: com.fuchen.collections
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/9
 */
@Slf4j
public class SortByComparableTest {

    @Test
    public void test(){
        List<SortByComparable> list = new ArrayList<>();
        list.add(new SortByComparable("li" , 3 , 2.1));
        list.add(new SortByComparable("zhang" , 1 , 3.1));
        list.add(new SortByComparable("wang" , 2 , 1.1));
        list.add(new SortByComparable("zhao" , 4 , 4.1));
        list.add(new SortByComparable("sun" , 5 , 5.1));
        log.info("排序前：{}",list);
        Collections.sort(list);
        log.info("排序后：{}",list);
    }

    @Test
    public void testComparator(){
        List<SortByComparable> list = new ArrayList<>();
        list.add(new SortByComparable("li" , 3 , 2.1));
        list.add(new SortByComparable("zhang" , 1 , 3.1));
        list.add(new SortByComparable("wang" , 2 , 1.1));
        list.add(new SortByComparable("zhao" , 4 , 4.1));
        list.add(new SortByComparable("sun" , 5 , 5.1));

        log.info("排序前：{}",list);
        Collections.sort(list , (o1, o2) -> {
            return o2.getAge() - o1.getAge();
        });
        log.info("排序后：{}",list);
    }
}
