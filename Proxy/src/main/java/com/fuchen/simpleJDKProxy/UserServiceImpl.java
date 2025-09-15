package com.fuchen.simpleJDKProxy;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: UserServiceImpl
 * Package: com.fuchen.simpleJDKProxy
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/15
 */
@Slf4j
public class UserServiceImpl implements UserService{
    @Override
    public void save() {
        log.info("执行save操作");
    }

    @Override
    public void delete() {
        log.info("执行delete操作");
    }

    @Override
    public void update() {
        log.info("执行update操作");
    }
}
