package com.fuchen.OIN;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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

    /**
     * 遍历文件夹中的所有目录
     * @throws IOException
     */
    public void viewDirectory() throws IOException {
        Stream<Path> walk = Files.walk(filePath);
        walk.forEach(path -> {
            int depth = path.getNameCount();
            for (int i = 0; i < depth; i++) {
                System.out.print("---");
            }
            System.out.println(path.getFileName());
        });
    }

    /**
     * 遍历文件夹中的所有目录
     * @throws IOException
     */
    public void viewDirectoryToFileTree() throws IOException {
        Files.walkFileTree(filePath , new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                log.info("找到文件：{}" , file.getFileName());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                log.info("找到文件夹：{}",dir.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
