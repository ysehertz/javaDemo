package com.fuchen.collections;

import lombok.Builder;
import lombok.Data;

import java.util.Comparator;

/**
 * ClassName: SortByComparable
 * Package: com.fuchen.collections
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/9
 */
@Data
@Builder
public class SortByComparable implements Comparable {

    private String name;
    private int age;
    private double height;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof SortByComparable){
            SortByComparable s = (SortByComparable) o;
            int result = this.age - s.age;
            if(result == 0){
                return Double.compare(this.height, s.height);
            }
            return this.age - s.age;
        }
        throw new RuntimeException("类型不匹配");
    }
}
