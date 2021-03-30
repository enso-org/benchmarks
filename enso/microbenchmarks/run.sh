#!/bin/bash

export LANG=C
echo "language,benchmark,run_id,time" > results.csv
enso run src/BenchTiming.enso | tee -a results.csv
enso run src/BenchSum.enso | tee -a results.csv
enso run src/BenchAllocVector.enso | tee -a results.csv
enso run src/BenchSumVector.enso | tee -a results.csv
enso run src/BenchAllocList.enso | tee -a results.csv
enso run src/BenchSumList.enso | tee -a results.csv
enso run src/BenchAllocFullTree.enso | tee -a results.csv
enso run src/BenchSumTree.enso | tee -a results.csv
