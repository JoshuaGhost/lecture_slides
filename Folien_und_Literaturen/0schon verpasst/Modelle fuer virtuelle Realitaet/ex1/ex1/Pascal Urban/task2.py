#!/usr/bin/env python3

# Author: Pascal Urban

DELIMITER = "," # CSV
# DELIMITER = "\t" # LaTex/PGFPlots

# Idee aus dem Buch "Grundlagen der Numerischen Mathematik
# und des Wissenschaftlichen Rechnens", Seite 565
def implicit_euler(f, y_0, dt):
    ycur = y_0
    while True:
        yield ycur
        y = lambda x: dt * f(x) - x + ycur
        y_derivative_approx = 1 - dt * ycur
        ycur = newton_iteration(y, y_derivative_approx, ycur)

def newton_iteration(f, f_derivative_approx, y):
    approx_y = y
    for k in range(0, 20):
        approx_y += f(approx_y) / f_derivative_approx
    return approx_y

def dgl1():
    dt = 0.1
    steps = 31

    dgl = lambda x: 1 - 2 * x
    euler = implicit_euler(dgl, 1, dt)

    print("x{}y".format(DELIMITER))
    for t in range(0, steps):
        print("{1:.2f}{0}{2:.10f}".format(DELIMITER, t*dt, next(euler)))

def dgl2():
    dt = 0.05
    steps = 16

    dgl = lambda x: x * x
    euler = implicit_euler(dgl, 1, dt)

    print("x{}y".format(DELIMITER))
    for t in range(0, steps):
        print("{1:.2f}{0}{2:.10f}".format(DELIMITER, t*dt, next(euler)))

dgl1()
print("")
dgl2()
