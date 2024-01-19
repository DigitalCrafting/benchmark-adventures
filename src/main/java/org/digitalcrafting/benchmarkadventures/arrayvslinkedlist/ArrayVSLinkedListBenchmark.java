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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
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
    public TestObject arrayList_getByKnownIndex(MyArrayVSLinkedListState myState) {
        return myState.arrayList.get(543897);
    }

    @Benchmark
    public TestObject linkedList_getByKnownIndex(MyArrayVSLinkedListState myState) {
        return myState.linkedList.get(543897);
    }

    @Benchmark
    public void arrayList_findByReference(MyArrayVSLinkedListState myState) {
        myState.arrayList.contains(myState.array_testObject_543897);
    }

    @Benchmark
    public void linkedList_findByReference(MyArrayVSLinkedListState myState) {
        myState.linkedList.contains(myState.linked_testObject_543897);
    }

    @Benchmark
    public void arrayList_removeByKnownIndex(MyArrayVSLinkedListState myState) {
        myState.arrayList.remove(543897);
    }

    @Benchmark
    public void linkedList_removeByKnownIndex(MyArrayVSLinkedListState myState) {
        myState.linkedList.remove(543897);
    }

    @Benchmark
    public void arrayList_removeByReference(MyArrayVSLinkedListState myState) {
        myState.arrayList.remove(myState.array_testObject_543897);
    }

    @Benchmark
    public void linkedList_removeByReference(MyArrayVSLinkedListState myState) {
        myState.linkedList.remove(myState.linked_testObject_543897);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(ArrayVSLinkedListBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
