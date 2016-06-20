#!/usr/bin/env python3

# Author: Pascal Urban

import math

DELIMITER = "," # CSV
# DELIMITER = "\t" # LaTex/PGFPlots

def dgl2():
    # c'_x = -c_y
    # c'_y = c_x

    x_cur = 1.0
    y_cur = 1.0
    ax = 1.0
    ay = 1.0
    dt = 0.1

    # also prints the results of the analytical function of the dgl
    print("t{0}x{0}y{0}ax{0}ay".format(DELIMITER))
    for t in range(0, 201):
        print("{1:.2f}{0}{2:.10f}{0}{3:.10f}{0}{4:.10f}{0}{5:.10f}".format(DELIMITER, t*dt, x_cur, y_cur, ax, ay))

        x_tmp_cur = x_cur + dt * (-y_cur)
        y_tmp_cur = y_cur + dt * x_cur

        x_cur = x_cur + dt/2.0 * ((-y_cur) - y_tmp_cur)
        y_cur = y_cur + dt/2.0 * (x_cur + x_tmp_cur)

        ax = math.cos(dt*(t+1)) - math.sin(dt*(t+1))
        ay = math.sin(dt*(t+1)) + math.cos(dt*(t+1))

dgl2()
