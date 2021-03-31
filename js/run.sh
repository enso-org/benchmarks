#!/bin/bash
TEN_MILLION=10000000
MILLION=1000000
TREE_DEPTH=17

export LANG=C
echo "language,benchmark,run_id,time" > results.csv
node benchTiming.js | tee -a results.csv
node benchSum.js $TEN_MILLION | tee -a results.csv
node benchAllocVector.js $MILLION | tee -a results.csv
node benchSumVector.js $MILLION | tee -a results.csv
node benchAllocList.js $MILLION | tee -a results.csv
node benchSumList.js $MILLION | tee -a results.csv
node benchAllocFullTree.js $TREE_DEPTH | tee -a results.csv
node benchSumTree.js $TREE_DEPTH | tee -a results.csv
