#!/bin/bash
set -xe

export BENCH_SIZE=1000000
export BENCH_DEPTH=17
#export BENCH_ITER=1000

rm -rf results
mkdir results

# cd java
# javac Main.java
# java Main | tee ../results/java.csv
# cd ..

# cd cpp
# g++ main.cpp -o main.x64
# ./main.x64 | tee ../results/cpp.csv
# cd ..

export BENCH_ITER=500
enso run enso/microbenchmarks | tee results/enso.csv

export BENCH_ITER=100
python3 python/main.py | tee results/python.csv

# node js/main.js | tee results/js.csv
