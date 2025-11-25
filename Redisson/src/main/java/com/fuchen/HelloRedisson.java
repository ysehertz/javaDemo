package com.fuchen;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: HelloRedisson
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/21
 */
@Slf4j
public class HelloRedisson{
    private File configFile;
    private final RedissonClient client;

    public HelloRedisson(String configFile) throws IOException {
        File file = new File(configFile);
        Config config = Config.fromYAML(file);
        this.client = Redisson.create(config);
    }

    public void printClient(){
        log.debug("Hello Redisson");
        if(client == null){
            log.error("客户端未初始化");
            return;
        }
        log.info("RedissonClient: {}", client);
    }
    public void basicCrud(){
        RBucket<String> gomkiri = client.getBucket("gomkiri");
        gomkiri.set("hello redisson");

        log.info("gomkiri: {}", gomkiri.get());
    }

    public void getZset(){
        client.getSet("gomkiri");
        log.info("获取set成功：{}", client.getSet("gomkiri"));
    }

}
