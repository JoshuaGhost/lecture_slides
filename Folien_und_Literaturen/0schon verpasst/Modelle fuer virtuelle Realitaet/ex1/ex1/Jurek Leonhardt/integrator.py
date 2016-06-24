#! /usr/bin/python
# -*- coding: utf-8 -*-


import matplotlib.pyplot as plot
import numpy
import math


# creates exp. euler-integrator for f(t, y) = y'
def create_exp_euler_int(f):
    return lambda t, prev_t, dim, prev_vals: prev_vals[dim] + (t - prev_t) * f(prev_t, prev_vals)


# creates heun-integrator for f(t, y) = y'
def create_heun_int(f):
    def func(t, prev_t, dim, prev_vals):
        h = t - prev_t

        # use exp. euler to approximate y_n+1
        tmp_vals = list(prev_vals)
        tmp_vals[dim] = tmp_vals[dim] + h * f(prev_t, prev_vals)

        return prev_vals[dim] + 1 / 2 * h * (f(prev_t, prev_vals) + f(t, tmp_vals))
    return func


# function(s), init. value(s), interval, subplot
functions = [
    # 1.1) exp. euler, 1-dim.
    [[create_exp_euler_int(lambda t, prev_vals: 1 - 2 * prev_vals[0])],
     [1], numpy.arange(0, 4, 0.01), 421],

    [[create_exp_euler_int(lambda t, prev_vals: prev_vals[0] ** 2)],
     [1], numpy.arange(0, 1.1, 0.01), 422],


    # 1.2) imp. euler, 1-dim.
    [[lambda t, prev_t, dim, prev_vals: (t - prev_t + prev_vals[dim]) / (1 + 2 * (t - prev_t))],
     [1], numpy.arange(0, 4, 0.01), 423],

    [[lambda t, prev_t, dim, prev_vals: (1 - math.sqrt(1 - 4 * prev_vals[dim] * (t - prev_t))) / (2 * (t - prev_t))],
     [1], numpy.arange(0, 0.9, 0.01), 424],


    # 1.3) exp. euler, 2-dim.
    [[create_exp_euler_int(lambda t, prev_vals: -prev_vals[0] * prev_vals[1]),
      create_exp_euler_int(lambda t, prev_vals: -prev_vals[1])],
     [1, 1], numpy.arange(0, 8, 0.01), 425],

    [[create_exp_euler_int(lambda t, prev_vals: -prev_vals[1]),
      create_exp_euler_int(lambda t, prev_vals: prev_vals[0])],
     [1, 1], numpy.arange(0, 20, 0.01), 426],


    # 1.4) heun, 2-dim.
    [[create_heun_int(lambda t, prev_vals: -prev_vals[0] * prev_vals[1]),
      create_heun_int(lambda t, prev_vals: -prev_vals[1])],
     [1, 1], numpy.arange(0, 8, 0.01), 427],

    [[create_heun_int(lambda t, prev_vals: -prev_vals[1]),
      create_heun_int(lambda t, prev_vals: prev_vals[0])],
     [1, 1], numpy.arange(0, 20, 0.01), 428]
]


def integrate(part_funcs, init_vals, interval):
    dimensions = range(0, len(part_funcs))

    x_vals = []
    y_vals = []
    prev_vals = []

    # insert init. values
    for i in dimensions:
        x_vals.append([])
        x_vals[i].append(interval[0])
        y_vals.append([])
        y_vals[i].append(init_vals[i])
        prev_vals.append(init_vals[i])

    # integrate
    prev_param = interval[0]
    for r in interval[1:]:
        new_vals = []

        for i in dimensions:
            new_vals.append(part_funcs[i](r, prev_param, i, prev_vals))
            x_vals[i].append(r)
            y_vals[i].append(new_vals[i])

        prev_vals = new_vals
        prev_param = r

    return [x_vals, y_vals]


if __name__ == '__main__':
    for func in functions:
        part_funcs = func[0]
        init_vals = func[1]
        interval = func[2]
        plot.subplot(func[3])

        result = integrate(part_funcs, init_vals, interval)

        # plot
        dimensions = range(0, len(part_funcs))
        for i in dimensions:
            plot.plot(result[0][i], result[1][i])

    plot.show()
