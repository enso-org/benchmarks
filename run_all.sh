#!/bin/bash
set -xe

cd enso
enso run microbenchmarks
cd ..

cd python
python3 main.py
cd ..

cd java
javac Main.java
java Main
cd ..

cd cpp
g++ main.cpp -o main
./main
cd ..

cd js
node main.js
cd ..
