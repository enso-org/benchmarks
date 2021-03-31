#!/bin/bash
set -xe

# Compares results of benchmarked functions in each language to verify that implementations are in sync.

rm -rf verify_tmp
mkdir verify_tmp

echo "Benchmarking Enso"
pushd enso/microbenchmarks/
enso run src/Verify.enso | tee ../../verify_tmp/enso.txt
popd

echo "Benchmarking Java"
pushd java/microbenchmarks/
mvn verify
java -cp target/benchmarks.jar org.enso.microbenchmarks.Verify | tee ../../verify_tmp/java.txt
popd

echo "Benchmarking Python"
pushd python/
python3 verify.py | tee ../verify_tmp/python.txt
popd

echo "Benchmarking JS"
pushd js/
node verify.js | tee ../verify_tmp/js.txt
popd

cd verify_tmp
diff -s enso.txt java.txt
diff -s enso.txt python.txt
diff -s enso.txt js.txt

echo "Done"
