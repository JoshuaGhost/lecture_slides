#!/usr/bin/env python3

# Author: Pascal Urban

import math

DELIMITER = "," # CSV
# DELIMITER = "\t" # LaTex/PGFPlots

def dgl1():
    x_cur = 1
    y_cur = 1
    dt = 0.1

    fx = lambda t, y: -t*y
    fy = lambda t: -t

    print("t{0}x{0}y".format(DELIMITER))
    for t in range(0, 41):
        print("{1:.2f}{0}{2:.10f}{0}{3:.10f}".format(DELIMITER, t*dt, x_cur, y_cur))

        y_cur += dt * fy(y_cur)
        x_cur += dt * fx(x_cur, y_cur)


def dgl2():
    # c'_x = -c_y
    # c'_y = c_x

    x_cur = 1.0
    y_cur = 1.0
    ax = 1.0 # the exact values
    ay = 1.0 # the exact values
    dt = 0.1

    # also prints the results of the analytical function of the dgl
    print("t{0}x{0}y{0}ax{0}ay".format(DELIMITER))
    for t in range(0, 61):
        print("{1:.2f}{0}{2:.10f}{0}{3:.10f}{0}{4:.10f}{0}{5:.10f}".format(DELIMITER, t*dt, x_cur, y_cur, ax, ay))
        x_cur += dt * (-y_cur)
        y_cur += dt * x_cur

        ax = math.cos(dt*(t+1)) - math.sin(dt*(t+1))
        ay = math.sin(dt*(t+1)) + math.cos(dt*(t+1))

dgl1()
print("")
dgl2()
