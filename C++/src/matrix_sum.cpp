#include <cstdlib>
#include <ctime>
#include <iomanip>
#include <iostream>
#include <thread>

#define ROW_SIZE 5
#define PAD_WIDTH 4
#define UPPER_BOUND 32
#define LOWER_BOUND -32

typedef int int_array[ROW_SIZE];
typedef int int_matrix[ROW_SIZE][ROW_SIZE];

void generateMatrix(int_matrix &mx);
void concurrentSum(int_array &a, int_array &b, int_array &r);
void printMatrix(int_matrix &ma, int_matrix &mb, int_matrix &mr);
void sequentialSum(int_matrix &ma, int_matrix &mb, int_matrix &mr);

int main()
{
  int_matrix matrix_a;
  int_matrix matrix_b;
  int_matrix matrix_cs;
  int_matrix matrix_cc;

  std::thread threads[ROW_SIZE];

  srand(time(0));

  generateMatrix(matrix_a);
  generateMatrix(matrix_b);

  sequentialSum(matrix_a, matrix_b, matrix_cs);

  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    threads[i] = std::thread(concurrentSum, std::ref(matrix_a[i]),
                             std::ref(matrix_b[i]), std::ref(matrix_cc[i]));
  }

  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    threads[i].join();
  }

  printMatrix(matrix_a, matrix_b, matrix_cc);

  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      if (matrix_cs[i][j] != matrix_cc[i][j])
      {
        std::cout << "[Error] - Matriz CS es distinta a la matriz CC."
                  << std::endl;
        return EXIT_FAILURE;
      }
    }
  }

  std::cout << "Matriz CS es igual a la matriz CC." << std::endl;
  return EXIT_SUCCESS;
}

void generateMatrix(int_matrix &mx)
{
  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      int new_value = (rand() % (UPPER_BOUND - LOWER_BOUND + 1)) + LOWER_BOUND;
      mx[i][j] = new_value;
    }
  }
}

void sequentialSum(int_matrix &ma, int_matrix &mb, int_matrix &mr)
{
  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      mr[i][j] = ma[i][j] + mb[i][j];
    }
  }
}

void concurrentSum(int_array &a, int_array &b, int_array &r)
{
  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    r[i] = a[i] + b[i];
  }
}

void printMatrix(int_matrix &ma, int_matrix &mb, int_matrix &mr)
{
  for (size_t i = 0; i < ROW_SIZE; i++)
  {
    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      std::cout << std::setw(PAD_WIDTH) << ma[i][j];
    }

    (i == ROW_SIZE / 2) ? std::cout << "   +  " : std::cout << "      ";

    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      std::cout << std::setw(PAD_WIDTH) << mb[i][j];
    }

    (i == ROW_SIZE / 2) ? std::cout << "   =  " : std::cout << "      ";

    for (size_t j = 0; j < ROW_SIZE; j++)
    {
      std::cout << std::setw(PAD_WIDTH) << mr[i][j];
    }

    std::cout << std::endl;
  }
}
