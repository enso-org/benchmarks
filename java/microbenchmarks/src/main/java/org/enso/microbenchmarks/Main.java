package org.enso.microbenchmarks;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class Main {

  public static void main(String... args) throws RunnerException {
    Options opts = new OptionsBuilder()
        .include(".*.Bench.*")
        .shouldDoGC(true)
        .mode(Mode.AverageTime)
        .warmupIterations(10)
        .measurementIterations(10)
        .forks(3)
//        .warmupIterations(1)
//        .measurementIterations(1)
//        .forks(1)
        .measurementTime(TimeValue.milliseconds(5000))
        .timeUnit(TimeUnit.MILLISECONDS)
        .result("results.csv")
        .resultFormat(ResultFormatType.CSV)
        .build();
    new Runner(opts).run();
  }
}
