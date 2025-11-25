package com.fuchen.oomTest;

import lombok.Data;

import java.awt.print.PrinterAbortException;
import java.util.Random;

/**
 * ClassName: MyTask
 * Package: com.fuchen.oomTest
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/10
 */
@Data
public class MyTask {
    private Integer sum;
    private Integer index;

    public MyTask(int index){
        this.sum = new Random().nextInt(1000);
        this.index = index;
    }

    public Integer doIt(){
        Integer result = sum * index;
        System.out.println("任务" + index + "计算结果：" + result);
        return result;
    }
}
