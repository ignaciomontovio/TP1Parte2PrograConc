import random
import numpy
import threading
import time

MAX_THREADS = 5
ROW = 5
COLUMN = 5
MAX_NUMBER = 32
MIN_NUMBER = -32

CS = numpy.empty(
    (ROW, COLUMN),
    int,
)
CC = numpy.empty(
    (ROW, COLUMN),
    int,
)


def get_number():
    return random.randrange(MIN_NUMBER, MAX_NUMBER)


def get_sum(row_a, row_b, row_number):
    for i in range(COLUMN):
        CC[row_number][i] = row_a[i] + row_b[i]


def sum_sec(m1, m2):
    for i in range(ROW):
        for j in range(COLUMN):
            CS[i][j] = m1[i][j] + m2[i][j]


def sum_con(m1, m2):
    threads = []
    CC = numpy.empty((ROW, COLUMN), int)
    for i in range(MAX_THREADS):
        threads.append(
            threading.Thread(
                target=get_sum,
                args=(m1[i], m2[i], i),
                daemon=False,
            )
        )
    for i in range(MAX_THREADS):
        threads[i].start()
    for i in range(MAX_THREADS):
        threads[i].join()


def compare():
    if numpy.equal(CS, CC).all():
        print(
            "Las matrices resultantes son iguales",
        )
        return
    print(
        "Las matrices resultantes no son iguales",
    )
    return


def show_data(m1, m2):
    print(
        "Matriz 1",
        "\n",
        m1,
        "\n",
    )
    print(
        "Matriz 2",
        "\n",
        m2,
        "\n",
    )
    print(
        "Matriz CS",
        "\n",
        CS,
        "\n",
    )
    print(
        "Matriz CC",
        "\n",
        CC,
        "\n",
    )
    compare()


def set_data():
    m = numpy.array(
        [
            [
                get_number(),
                get_number(),
                get_number(),
                get_number(),
                get_number(),
            ],
            [
                get_number(),
                get_number(),
                get_number(),
                get_number(),
                get_number(),
            ],
            [
                get_number(),
                get_number(),
                get_number(),
                get_number(),
                get_number(),
            ],
            [
                get_number(),
                get_number(),
                get_number(),
                get_number(),
                get_number(),
            ],
            [
                get_number(),
                get_number(),
                get_number(),
                get_number(),
                get_number(),
            ],
        ]
    )
    return m


def main():
    m1 = set_data()
    m2 = set_data()
    sum_sec(m1, m2)
    sum_con(m1, m2)
    show_data(m1, m2)


if __name__ == "__main__":
    main()
