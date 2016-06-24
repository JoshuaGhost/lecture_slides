#!/usr/bin/env python3

# Author: Pascal Urban

def a_stability(integrator, dt):
    for k in [1, 2, 2.0/dt, 2.0/dt ** 2, 2.0/dt ** 3]:
        prev_res = 2 # first value is always 1 (something^0)
        for n in range(0, 21):
            next_res = integrator(n, dt, k)
            if next_res > prev_res:
                return k
            prev_res = next_res

    return None


explicit_integrator = lambda n, dt, k: (1.0 - dt*k) ** n
k = a_stability(explicit_integrator, 0.1)
if k == None:
    print("Explicit euler is a-stable")
else:
    print("Explicit euler is not a-stable (k: {:.1f})".format(k))

implicit_integrator = lambda n, dt, k: (1.0 / (1 + dt * k)) ** n
k = a_stability(implicit_integrator, 0.1)
if k == None:
    print("Implicit euler is a-stable")
else:
    print("Implicit euler is not a-stable (k: {:.1f})".format(k))

