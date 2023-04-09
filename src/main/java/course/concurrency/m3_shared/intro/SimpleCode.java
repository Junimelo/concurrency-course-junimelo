package course.concurrency.m3_shared.intro;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SimpleCode {

//    public static void main(String[] args) {
//        final int size = 50_000_000;
//        Object[] objects = new Object[size];
//        for (int i = 0; i < size; ++i) {
//            objects[i] = new Object();
//        }
//    }


    public void auf() {
        Executor executor = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(8), new ThreadPoolExecutor.DiscardPolicy());
    }

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
//        ForkJoinTask t = forkJoinPool.submit(() -> System.out.println(
//                IntStream.range(0,10000000).average().getAsDouble()
//        ));
//        t.get();
//    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                1L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        Future f = executor.submit(() -> System.out.println(
                IntStream.range(0,10000000).average().getAsDouble()
        ));
    }
}
