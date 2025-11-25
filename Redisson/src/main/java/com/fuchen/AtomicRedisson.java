package com.fuchen;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: AtomicRedssion
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/21
 */
@Slf4j
public class AtomicRedisson {
    private File configFile;
    private final RedissonClient client;

    public AtomicRedisson(String configFile) throws IOException {
        File file = new File(configFile);
        Config config = Config.fromYAML(file);
        this.client = Redisson.create(config);
    }

    public void atomicLongI(String key, Long value){
        RAtomicLong atomicLong = client.getAtomicLong(key);

        atomicLong.set(value);
        long l = atomicLong.incrementAndGet();
        log.info("将value加一得到:{}",l);
    }
}
