package online.qms198.springboot_stu.utils;

import lombok.extern.slf4j.Slf4j;
import online.qms198.springboot_stu.aop.DebounceAspect;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DebounceCleanerUtil {

    private final ConcurrentHashMap<String, Long> requestTimestamps;

    public DebounceCleanerUtil(DebounceAspect debounceAspect) {
        this.requestTimestamps = debounceAspect.getRequestTimestamps();
    }

    @Scheduled(fixedRate = 600000) // 每10分钟执行一次
    public void cleanExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        long expirationTime = 15 * 60 * 1000; // 15分钟
        log.info("10分钟自动清理防抖时间戳Map中的过期条目");
        Iterator<String> iterator = requestTimestamps.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Long lastTime = requestTimestamps.get(key);
            if (currentTime - lastTime > expirationTime) {
                iterator.remove();
            }
        }
    }
}
