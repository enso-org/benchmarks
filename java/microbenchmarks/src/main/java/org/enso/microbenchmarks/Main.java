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
        .mode(Mode.AverageTime)
        .warmupIterations(10)
        .measurementIterations(10)
        .measurementTime(TimeValue.milliseconds(5000))
        .timeUnit(TimeUnit.MILLISECONDS)
        .forks(3)
        .result("results.csv")
        .resultFormat(ResultFormatType.CSV)
        .build();
    new Runner(opts).run();
  }
}
