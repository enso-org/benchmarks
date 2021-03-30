#!/bin/bash

echo "language;benchmark;run_id;time" > results.csv
enso run src/BenchTiming.enso | tee -a results.csv
enso run src/BenchSum.enso | tee -a results.csv
enso run src/BenchAllocVector.enso | tee -a results.csv
enso run src/BenchSumVector.enso | tee -a results.csv
