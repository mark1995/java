package com.jing.mutil.thread;

import javafx.scene.paint.Stop;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description: cow 用法
 * @author: GXK
 * @create: 2021-12-17 15:19
 **/

public class CopyOnWriteListSimple {


    /*
        比较 并发写，

        result: 在大量并发写的过程中，synchronized明显优于cow机制 （100-1000倍 2个数量级，3个数量级）
        StopWatch '': running time = 67446802899 ns  = 67.446802899 s
        ---------------------------------------------
        ns         %     Task name
        ---------------------------------------------
        67421348999  100%  cow add elem
        025453900  000%  synchronized add elem

     */
    public void compareParallelWrite() {
        List<Integer> cowArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        int loopWatch = 500000;
        stopWatch.start("cow add elem");
        IntStream.rangeClosed(1, loopWatch).parallel().forEach(__ -> cowArrayList.add(ThreadLocalRandom.current().nextInt(loopWatch)));
        stopWatch.stop();
        stopWatch.start("synchronized add elem");
        IntStream.rangeClosed(1, loopWatch).parallel().forEach(__ -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopWatch)));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());

        Map<String,Integer> result = new HashMap<>();
        result.put("cowArrayList", cowArrayList.size());
        result.put("synchronizedList", synchronizedList.size());
        System.out.println(result);
    }


    public void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList()));
    }


    /*
        并发读取
        并发读取的时候， cow的速度优于synchronized list (5倍)
        StopWatch '': running time = 139949601 ns = 0.1399s
        ---------------------------------------------
        ns         %     Task name
        ---------------------------------------------
        021306300  015%  cow parallel read
        118643301  085%  synchronized parallel read
     */
    public void compareParallelRead() {
        List<Integer> cowReadList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        addAll(cowReadList);
        addAll(synchronizedList);

        int loopCount = 1000000;
        int size = cowReadList.size();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("cow parallel read");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> cowReadList.get(ThreadLocalRandom.current().nextInt(size)));
        stopWatch.stop();

        stopWatch.start("synchronized parallel read");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> synchronizedList.get(ThreadLocalRandom.current().nextInt(size)));
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeSeconds());
    }





    public static void main(String[] args) {
        CopyOnWriteListSimple simple = new CopyOnWriteListSimple();
        simple.compareParallelRead();
    }
}


