package com.github.register;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
/**
 * @author sniper
 * @date
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Sql(scripts = {"classpath:db/mysql/select.sql"})
//@Sql(scripts = {"classpath:db/mysql/schema.sql", "classpath:db/mysql/data.sql"})
@SpringBootTest(classes = RegisterApplication.class)
public class DBRollbackBase {

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void evictAllCaches() {
        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear();
        }
    }
}
