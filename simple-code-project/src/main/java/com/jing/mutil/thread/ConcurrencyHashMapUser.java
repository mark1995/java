package com.jing.mutil.thread;

import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: GXK
 * @create: 2021-12-17 11:57
 **/

public class ConcurrencyHashMapUser {
    /*
       this is thread count
    */
    private static final Integer THREAD_COUNT = 10;

    /*
        loop count
     */
    private static final Integer LOOP_COUNT = 10000000;


    /*
        item count
     */
    private static final Integer ITEM_COUNT = 10;


    /*
        calc rand key count
        normal use
     */
    private Map<String, Long> normalUse() throws InterruptedException {
        ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            synchronized (concurrentHashMap) {
                if (concurrentHashMap.containsKey(key)){
                    concurrentHashMap.put(key, concurrentHashMap.get(key)+1);
                } else {
                    concurrentHashMap.put(key, 1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return concurrentHashMap;
    }


    public Map<String, Long> casUse() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                concurrentHashMap.computeIfAbsent(key, k -> new LongAdder()).increment();
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return concurrentHashMap.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue().longValue()
        ));
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrencyHashMapUser user = new ConcurrencyHashMapUser();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normal use");
        Map<String, Long> normalUse = user.normalUse();
        stopWatch.stop();

        Assert.isTrue(normalUse.size() == ITEM_COUNT, "normal size error");
        Assert.isTrue(normalUse.entrySet().stream().mapToLong(
                item -> item.getValue()
        ).reduce(0, Long::sum) == LOOP_COUNT, "normal count error");
        stopWatch.start("cas use");
        Map<String, Long> casUse = user.casUse();
        stopWatch.stop();

        Assert.isTrue(casUse.size() == ITEM_COUNT, "casUse size error");
        Assert.isTrue(casUse.entrySet().stream().mapToLong(
                item -> item.getValue()
        ).reduce(0, Long::sum) == LOOP_COUNT, "casUse count error");
        System.out.println(stopWatch.prettyPrint());
    }
}


