#!/bin/bash
set -xe

# Prerequisites:
# - installed Java and Maven (`mvn` on PATH)
# - installed latest Enso engine with a launcher (`enso` on PATH)
# - installed CPython 3.8+ (`python3` on PATH)
# - installed Node JS (`node` on PATH)

rm -rf results
mkdir results

echo "Enso:" >> results/versions.txt
enso --version >> results/versions.txt

echo "Java:" >> results/versions.txt
java --version >> results/versions.txt
mvn --version >> results/versions.txt

echo "Python:" >> results/versions.txt
python3 --version >> results/versions.txt

echo "JS:" >> results/versions.txt
node --version >> results/versions.txt

cat results/versions.txt

echo "Benchmarking Enso"
pushd enso/microbenchmarks/
./run.sh
popd
cp enso/microbenchmarks/results.csv results/enso.csv

echo "Benchmarking Java"
pushd java/microbenchmarks/
mvn verify
java -cp target/benchmarks.jar org.enso.microbenchmarks.Main
popd
cp java/microbenchmarks/results.csv results/java.csv

echo "Benchmarking Python"
pushd python/
python3 main.py | tee results.csv
popd
cp python/results.csv results/python.csv

echo "Benchmarking JS"
pushd js/
./run.sh
popd
cp js/results.csv results/js.csv

echo "Done"
