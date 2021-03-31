#!/bin/bash
TEN_MILLION=10000000
MILLION=1000000
TREE_DEPTH=17

OPTS="--expose-gc"

export LANG=C
echo "language,benchmark,run_id,time" > results.csv
node $OPTS benchTiming.js | tee -a results.csv
node $OPTS benchSum.js $TEN_MILLION | tee -a results.csv
node $OPTS benchAllocVector.js $MILLION | tee -a results.csv
node $OPTS benchSumVector.js $MILLION | tee -a results.csv
node $OPTS benchAllocList.js $MILLION | tee -a results.csv
node $OPTS benchSumList.js $MILLION | tee -a results.csv
node $OPTS benchAllocFullTree.js $TREE_DEPTH | tee -a results.csv
node $OPTS benchSumTree.js $TREE_DEPTH | tee -a results.csv
