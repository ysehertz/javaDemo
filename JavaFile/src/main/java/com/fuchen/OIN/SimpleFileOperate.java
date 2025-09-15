package com.fuchen.OIN;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName: SimpleFileOperate
 * Package: com.fuchen.OIN
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/15
 */
@Slf4j
public class SimpleFileOperate {
    private Path filePath;

    public SimpleFileOperate(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public void GetFile() throws IOException {
        AtomicInteger i = new AtomicInteger(0);
        Stream<String> lines = Files.lines(filePath);
        lines.map(s -> "第"+ i.incrementAndGet() + "行：" + s).forEach(s -> log.info(s));
        lines.close();
    }

    public Map<Integer , String> GetFileToMap() throws IOException {
        AtomicInteger i = new AtomicInteger(0);
        Stream<String> lines = Files.lines(filePath);
        Map<Integer, String> map = lines.collect(Collectors.toMap(s -> i.incrementAndGet(), s -> s));
        lines.close();
        return map;
    }
}
