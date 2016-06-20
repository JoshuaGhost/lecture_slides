#! /usr/bin/python
# -*- coding: utf-8 -*-


import integrator
import numpy


def test_a_stab(create_int):
    k_vals = [0.1, 1, 10]
    interval = numpy.arange(0, 10, 1)

    for k in k_vals:

        # integrator for A' = -kA
        integrator_func = create_int(lambda t, last_vals: -k * last_vals[0])

        # [1] => only y-values, [0] => 1-dim.
        result = integrator.integrate([integrator_func], [1], interval)[1][0]

        # check whether values decrease
        for i in range(0, len(result) - 1):
            if result[i] < result[i + 1]:
                return False

    return True


# integrator that only halves last value but passes test
def create_useless_int(f):
    return lambda t, prev_t, dim, prev_vals: prev_vals[dim] / 2


if __name__ == '__main__':
    integrators = {'Exp. Euler': integrator.create_exp_euler_int,
                   'Heun': integrator.create_heun_int,
                   'Useless': create_useless_int}

    for i in integrators:
        if test_a_stab(integrators[i]):
            print(i + ' method is A-stable!')
        else:
            print(i + ' method is not A-stable!')