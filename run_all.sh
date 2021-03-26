#!/bin/bash
set -xe

rm -rf results
mkdir results

enso run enso/microbenchmarks | tee results/enso.csv

python3 python/main.py | tee results/python.csv

cd java
javac Main.java
java Main | tee ../results/java.csv
cd ..

cd cpp
g++ main.cpp -o main.x64
./main.x64 | tee ../results/cpp.csv
cd ..

node js/main.js | tee results/js.csv
