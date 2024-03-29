package org.digitalcrafting.benchmarkadventures.streamvsloop;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class StreamVSLoopBenchmark {
    @State(Scope.Thread)
    public static class MyStreamVSLoopState {
        List<Integer> numbers;

        @Setup(Level.Trial)
        public void setUp() {
            numbers = new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                numbers.add(i);
            }
        }
    }

    @Benchmark
    public int forLoopBenchmark(MyStreamVSLoopState state) {
        int sum = 0;
        for (int number : state.numbers) {
            if (number % 2 == 0) {
                sum = sum + (number * number);
            }
        }
        return sum;
    }

    @Benchmark
    public int parallelStreamBenchMark(MyStreamVSLoopState state) {
        return state.numbers.parallelStream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * number)
                .reduce(0, Integer::sum);
    }

    @Benchmark
    public int concurrentForLoopBenchmark(MyStreamVSLoopState state) throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Callable<Integer>> tasks = new ArrayList<>();
        int chunkSize = state.numbers.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? state.numbers.size() : (i + 1) * chunkSize;
            tasks.add(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    int number = state.numbers.get(j);
                    if (number % 2 == 0) {
                        sum = sum + (number * number);
                    }
                }
                return sum;
            });
        }

        int totalSum = 0;
        for (Future<Integer> result : executorService.invokeAll(tasks)) {
            totalSum += result.get();
        }

        executorService.shutdown();
        return totalSum;
    }

    @Benchmark
    public int streamBenchMark(MyStreamVSLoopState state) {
        return state.numbers.stream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * number)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(StreamVSLoopBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
