#include <chrono>
#include <cstdint>
#include <cstdlib>
#include <functional>
#include <iostream>
#include <string>
#include <thread>

void print_header() {
  std::cout << "language, benchmark, run_id, time" << std::endl;
}

void measure(std::function<void(void)> act, std::string label, int64_t num_iters) {
  for (int64_t it_num = 0; it_num < num_iters; it_num++) {
    auto x1 = std::chrono::steady_clock::now();
    act();
    auto x2 = std::chrono::steady_clock::now();
    std::chrono::nanoseconds diff = x2 - x1;
    double millis = diff.count() / 1.0e6;
    std::cout << "cpp, " << label << ", " << it_num << ", " << millis << std::endl;
  }
}

int64_t sum(int64_t n) {
  int64_t acc = 0;
  int64_t i = 1;
  while (i <= n) {
    acc += i;
    i++;
  }
  return acc;
}

int64_t* alloc_vector(int64_t n) {
  int64_t *res = new int64_t[n];
  for (int64_t i = 0; i < n; ++i) {
    res[i] = i;
  }
  return res;
}

int64_t sum_vector(const int64_t *vec, size_t size) {
  int64_t acc = 0;
  for (size_t i = 0; i < size; ++i) {
    acc += vec[i];
  }
  return acc;
}

struct Cons {
  int64_t head;
  Cons *tail;
};

Cons* alloc_list(int64_t n) {
  Cons *res = nullptr;
  for (int64_t i = n; i >= 0; --i) {
    res = new Cons{i, res};
  }
  return res;
}

int64_t sum_list(Cons *list) {
  int64_t acc = 0;
  while (list != nullptr) {
    acc += list->head;
    list = list->tail;
  }
}

struct Tree {
  Tree *left;
  int64_t elem;
  Tree *right;
};

Tree* allocate_helper(int64_t& ix, int64_t remaining_depth) {
  if (remaining_depth == 0) {
    return nullptr;
  } else {
    auto l = allocate_helper(ix, remaining_depth - 1);
    int64_t e = ix++;
    auto r = allocate_helper(ix, remaining_depth - 1);
    return new Tree{l, e, r};
  }
}

Tree* alloc_full_tree(int64_t depth) {
  int64_t ix = 0;
  return allocate_helper(ix, depth);
}

int64_t sum_tree(Tree* tree) {
  if (tree == nullptr) {
    return 0;
  } else {
    int64_t l = sum_tree(tree->left);
    int64_t r = sum_tree(tree->right);
    return l + tree->elem + r;
  }
}

int main() {
  print_header();
  int64_t size = std::stoi(std::getenv("BENCH_SIZE"));
  int64_t depth = std::stoi(std::getenv("BENCH_DEPTH"));
  int64_t n_iters = std::stoi(std::getenv("BENCH_ITER"));

  std::cerr << "Size = " << size << std::endl;
  std::cerr << "Depth = " << depth << std::endl;
  std::cerr << "Iterations = " << n_iters << std::endl;

  measure([]() { std::this_thread::sleep_for(std::chrono::milliseconds(100)); }, "100ms", 10);

  measure([&]() { sum(size); }, "sum", n_iters);

  measure([&]() { alloc_vector(size); }, "alloc_vector", n_iters);
  auto vec = alloc_vector(size);
  measure([&]() { sum_vector(vec, size); }, "sum_vector", n_iters);

  measure([&]() { alloc_list(size); }, "alloc_list", n_iters);
  auto list = alloc_list(size);
  measure([&]() { sum_list(list); }, "sum_list", n_iters);

  measure([&]() { alloc_full_tree(depth); }, "alloc_full_tree", n_iters);
  auto tree = alloc_full_tree(depth);
  measure([&]() { sum_tree(tree); }, "sum_tree", n_iters);
}
