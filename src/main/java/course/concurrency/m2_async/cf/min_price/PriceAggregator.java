package course.concurrency.m2_async.cf.min_price;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        // здесь будет ваш код
        List<CompletableFuture<Double>> completableFutureList =
                shopIds.stream().map(sId ->
                    CompletableFuture
                            .supplyAsync(() -> priceRetriever.getPrice(itemId, sId))
                            .completeOnTimeout(Double.POSITIVE_INFINITY, 2900, TimeUnit.MILLISECONDS)
                            .handle((result, exc) -> result != null ? result : Double.POSITIVE_INFINITY)
                ).collect(Collectors.toList());
        CompletableFuture.allOf(completableFutureList.toArray(CompletableFuture[]::new)).join();
        return completableFutureList.stream().mapToDouble(CompletableFuture::join).filter(Double::isFinite).min().orElse(Double.NaN);
    }
}
