#!/bin/bash
set -xe

export BENCH_SIZE=1000000
export BENCH_DEPTH=20
export BENCH_ITER=80

rm -rf results
mkdir results

cd java
javac Main.java
java Main | tee ../results/java.csv
cd ..

cd cpp
g++ main.cpp -o main.x64
./main.x64 | tee ../results/cpp.csv
cd ..

python3 python/main.py | tee results/python.csv

enso run enso/microbenchmarks | tee results/enso.csv

node js/main.js | tee results/js.csv
