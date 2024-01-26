package org.digitalcrafting.benchmarkadventures.arrayvslinkedlist;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayVSLinkedListBenchmark {
    private record TestObject(String name, Integer id) {
    }

    @State(Scope.Thread)
    public static class MyArrayVSLinkedListState {
        List<TestObject> arrayList;
        List<TestObject> linkedList;

        /* We want to have a test object that we know of beforehand,
         *  but we want it different for both lists, so that cache is cold in both benchmarks.
         *  */
        TestObject array_testObject_543897;
        TestObject linked_testObject_543897;

        @Setup(Level.Trial)
        public void setUp() {
            arrayList = new ArrayList<>();
            linkedList = new LinkedList<>();
            for (int i = 0; i < 1_000_000; i++) {
                if (i == 543897) {
                    array_testObject_543897 = new TestObject("name_" + i, i);
                    linked_testObject_543897 = new TestObject("name_" + i, i);

                    arrayList.add(array_testObject_543897);
                    linkedList.add(linked_testObject_543897);
                } else {
                    arrayList.add(new TestObject("name_" + i, i));
                    linkedList.add(new TestObject("name_" + i, i));
                }
            }
        }
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void arrayList_iterateOver(MyArrayVSLinkedListState myState) {
        int i = 0;
        for (TestObject obj : myState.arrayList) {
            i++;
        }
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void linkedList_iterateOver(MyArrayVSLinkedListState myState) {
        int i = 0;
        for (TestObject obj : myState.linkedList) {
            i++;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject arrayList_removeFirst(MyArrayVSLinkedListState myState) {
        return myState.arrayList.remove(0);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject linkedList_removeFirst(MyArrayVSLinkedListState myState) {
        return myState.linkedList.remove(0);
    }

     @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject arrayList_removeInTheMiddle(MyArrayVSLinkedListState myState) {
        return myState.arrayList.remove(543899);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject linkedList_removeInTheMiddle(MyArrayVSLinkedListState myState) {
        return myState.linkedList.remove(543899);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject arrayList_removeLast(MyArrayVSLinkedListState myState) {
        return myState.arrayList.remove(myState.arrayList.size() - 1);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public TestObject linkedList_removeLast(MyArrayVSLinkedListState myState) {
        return myState.linkedList.remove(myState.linkedList.size() - 1);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public TestObject arrayList_getByKnownIndex(MyArrayVSLinkedListState myState) {
        return myState.arrayList.get(543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public TestObject linkedList_getByKnownIndex(MyArrayVSLinkedListState myState) {
        return myState.linkedList.get(543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void arrayList_addFirst(MyArrayVSLinkedListState myState) {
        myState.arrayList.add(0, new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void linkedList_addFirst(MyArrayVSLinkedListState myState) {
        myState.linkedList.add(0, new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void arrayList_addInTheMiddle(MyArrayVSLinkedListState myState) {
        myState.arrayList.add(543899, new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void linkedList_addInTheMiddle(MyArrayVSLinkedListState myState) {
        myState.linkedList.add(543899, new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void arrayList_addLast(MyArrayVSLinkedListState myState) {
        myState.arrayList.add(new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 5, batchSize = 10)
    @Measurement(iterations = 5, batchSize = 10)
    public void linkedList_addLast(MyArrayVSLinkedListState myState) {
        myState.linkedList.add(new TestObject("name_1000000", 1000000));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void arrayList_findByReference(MyArrayVSLinkedListState myState) {
        myState.arrayList.contains(myState.array_testObject_543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void linkedList_findByReference(MyArrayVSLinkedListState myState) {
        myState.linkedList.contains(myState.linked_testObject_543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void arrayList_removeByKnownIndex(MyArrayVSLinkedListState myState) {
        myState.arrayList.remove(543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void linkedList_removeByKnownIndex(MyArrayVSLinkedListState myState) {
        myState.linkedList.remove(543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void arrayList_removeByReference(MyArrayVSLinkedListState myState) {
        myState.arrayList.remove(myState.array_testObject_543897);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    public void linkedList_removeByReference(MyArrayVSLinkedListState myState) {
        myState.linkedList.remove(myState.linked_testObject_543897);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(ArrayVSLinkedListBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
