#!/usr/bin/env python3

# Author: Pascal Urban

DELIMITER = "," # CSV
# DELIMITER = "\t" # LaTex/PGFPlots

def explicit_euler(f, y_0, dt):
    y_cur = y_0
    while True:
        yield y_cur
        y_cur = y_cur + f(y_cur) * dt

def dgl1():
    # f' = 1 - 2*f

    dt = 0.1
    steps = 31
    dgl = lambda x: 1 - 2 * x
    euler = explicit_euler(dgl, 1, dt)

    print("x{}y".format(DELIMITER))
    for t in range(0, steps):
        print("{1:.2f}{0}{2:.10f}".format(DELIMITER, t*dt, next(euler)))

def dgl2():
    # y' = y^2

    dt = 0.05
    steps = 23
    dgl = lambda x: x * x
    euler = explicit_euler(dgl, 1, dt)

    print("x{}y".format(DELIMITER))
    for t in range(0, steps):
        print("{1:.2f}{0}{2:.10f}".format(DELIMITER, t*dt, next(euler)))

dgl1()
print()
dgl2()

