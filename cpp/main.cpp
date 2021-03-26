#include <chrono>
#include <functional>
#include <iostream>
#include <string>
#include <thread>

void print_header() {
  std::cout << "language, benchmark, run_id, time" << std::endl;
}

void measure(std::function<void(void)> act, std::string label, int num_iters) {
  for (int it_num = 0; it_num < num_iters; it_num++) {
    auto x1 = std::chrono::steady_clock::now();
    act();
    auto x2 = std::chrono::steady_clock::now();
    std::chrono::nanoseconds diff = x2 - x1;
    double millis = diff.count() / 1000000.0;
    std::cout << "cpp, " << label << ", " << it_num << ", " << millis << std::endl;
  }
}

int sum(int n) {
  int acc = 0;
  int i = 1;
  while (i <= n) {
    acc += i;
    i++;
  }
  return acc;
}


int main() {
  print_header();
  // size = 100000000
  // depth = 25
  // n_iters = 10
  int size = 100; // TODO FIXME temporary workaround to quickly test
  int depth = 4;
  int n_iters = 2;

  measure([]() { std::this_thread::sleep_for(std::chrono::milliseconds(100)); }, "100ms", 10);

  measure([&]() { sum(size); }, "sum", n_iters);
}
