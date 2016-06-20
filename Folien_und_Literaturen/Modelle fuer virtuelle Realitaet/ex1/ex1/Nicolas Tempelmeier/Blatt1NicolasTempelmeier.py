#!/usr/bin/python
# -*- coding: utf-8 -*-

import math

#Aufgabe 1.1
#f ist eine Lambda-Funktion, die der DGL entspricht
def expEuler(dt, t_end, f, startPoint):
    points = [startPoint]
    last = points[0]
    t = last[0]
    while t  <= t_end:
        t += dt
        f_t = last[1] + dt * f(last[1])
        last = (t, f_t)
        points.append(last)
    return points

#Aufgabe 1.2
#f ist eine Lambda-Funktion, die einer algebraisch
#aufgeloesten Funktion für f(t1)=y1 entspricht
def impEuler(dt, t_end, f, startPoint):
    points = [startPoint]
    last = points[0]
    t = last[0]
    while t  <= t_end:
        t += dt
        #print("dt: "+str(dt)+", y0: "+str(last[1])+", t:"+str(t))
        f_t = f(last[1], dt)
        #print(str(f_t))
        last = (t, f_t)
        points.append(last)
    return points

#Aufgabe 1.3
#fx ist eine Lambda-Funktion, die der x-Komponente der DGL entspricht
#fy ist eine Lambda-Funktion, die der y-Komponente der DGL entspricht
def expEuler2D(dt, t_end, fx, fy, startPoint):
    points = [startPoint]
    last = points[0]
    t = last[0]
    while t  <= t_end:
        t += dt
        fx_t = last[1][0] + dt * fx(last[1])
        fy_t = last[1][1] + dt * fy(last[1])
        last = (t, (fx_t, fy_t))
        points.append(last)
    return points


#Aufgabe 1.4
#fx ist eine Lambda-Funktion, die der x-Komponente der DGL entspricht
#fy ist eine Lambda-Funktion, die der y-Komponente der DGL entspricht
def heun2D(dt, t_end, fx, fy, startPoint):
    points = [startPoint]
    last = points[0]
    t = last[0]
    while t  <= t_end:
        t += dt
        values = last[1]

        fx_t_p = values[0] + dt * fx(values)
        fy_t_p = values[1] + dt * fy(values)

        fx_t = 0.5 * values[0] + 0.5 * (fx_t_p + dt * fx((fx_t_p, fy_t_p)))
        fy_t = 0.5 * values[1] + 0.5 * (fy_t_p + dt * fy((fx_t_p, fy_t_p)))

        last = (t, (fx_t, fy_t))
        points.append(last)
    return points

def exactSolution(dt, t_end, f, t_start):
    #Loesung fuer F1 laut Wolfram Alpha: f(x) = c * e^(-2x) + 0.5
    #Fuer Startpunkt 0, 0 ist c = -0.5
    points = [(t_start, f(t_start))]
    t = t_start
    while t <= t_end:
        t += dt
        f_t = f(t)
        points.append((t, f_t))
    return points

def compareResults(p1, p2, run):
    dev = 0
    for a, b in zip(p1, p2):
        dev += math.fabs(a[1] - b[1])
    dev /= len(p1)
    print("Durchschnittliche Abweichung fuer "+run+": "+str(dev))

#computes the average euclidean distance between two lists of points
def compareResults2D(p1, p2, run):
    dev = 0
    for a, b in zip(p1, p2):
        dist = math.sqrt(math.pow(a[1][0] - b[1][0], 2) + math.pow(a[1][1] - b[1][1], 2))
        
        dev += dist
    dev /= len(p1)
    print("Durchschnittliche Abweichung für "+run+": "+str(dev))

#Aufgabe 1.5
#Konvergenz wird (für diese Funktion) durch zwei Eigenschaften überprüft:
#1. Die Werte sind monoton fallend
#2. Der letzte Wert ist nahe an 0
def aStability(integrator, mode):
    dt = 0.1
    tend = 1e5

    kValues = [0.9, 0.8, 2/dt, 0.1]

    start = (0, 1)

    for k in kValues:
        if mode == "exp":
            f = lambda x: -k * x
        elif mode == "imp":
            f = lambda x, dt: x / (1 + dt* k)
        results = integrator(dt,tend, f, start)
        
        #Monotonie überprüfen
        current = float("Inf")
        for i in results:
            if current < i[1]:
                return False
            current = i[1]
        
        #Nähe zur Null überprüfen
        if math.fabs(results[-1][1]) > 1e-6:
            return False

    return True


if __name__ == "__main__":
    #Aufgabe 1.1
    #dgl f1
    dglF1 = lambda x: 1-2*x
    #Loesung fuer F1 laut Wolfram Alpha: f(x) = c * e^(-2x) + 0.5
    #Fuer Startpunkt 0, 0 ist c = -0.5
    exactF1 = lambda x:  -0.5 * math.exp(-2*x) + 0.5

    #dgl f2
    dglF2 = lambda x: pow(x, 2)
    #Loesung fuer F2 laut Wolfram Alpha f(x) = 1 / (c - x)
    #Fuer Stratpunkt 1, 1 ist c = 2
    exactF2 = lambda x: 1/(2 -x)

    ee1 = expEuler(0.1, 5, dglF1, (0, 0))
    ef1  = exactSolution(0.1, 5, exactF1, 0)
    compareResults(ee1, ef1, "expliziter Euler DGL1")

    ee2 = expEuler(0.0001, 1.9, dglF2, (1, 1))
    ef2  = exactSolution(0.0001, 1.9, exactF2, 1)
    compareResults(ee2, ef2, "expliziter Euler DGL2")

    #Aufgabe 1.2

    #Funktion fuer impliziten Euler: y1 = y0 +dt *(1 - 2*y1) nach y1 aufgeloest (Wolfram Alpha)
    impF1 = lambda y1, dt: (y1 + dt) / (1 + 2*dt)
    ie1 = impEuler(0.1, 5, impF1, (0, 0))
    compareResults(ie1, ef1, "implizierter Euler DGL1")

    #Funktion für impliziten Euler: y1 = y0 + dt * y1^2 nach y1 aufgeloest
    impF2 = lambda y1, dt: 1/(2*dt) - math.sqrt(math.pow(1/(2*dt), 2) - (1/dt)*y1)
    ie2 = impEuler(0.0001, 1.9, impF2, (1, 1))
    compareResults(ie2, ef2, "implizierter Euler DGL2")


    #Aufgabe 1.3
    dglF3x = lambda x: -x[0] * x[1]
    dglF3y = lambda x: -x[1]
    ee3 = expEuler2D(0.01, 100, dglF3x, dglF3y, (0, (1, 1)))

    dglF4x  = lambda x: -x[1]
    dglF4y = lambda x: x[0]
    exactF4 = lambda x: (math.cos(x), math.sin(x))
    ee4 = expEuler2D(0.1 ,5, dglF4x, dglF4y, (0, (1, 0  )))
    ef4 = exactSolution(0.1, 5, exactF4, 0)
    compareResults2D(ee4, ef4, "explizierter Euler 2D DGL4")

    #Aufgabe 1.4
    he4 = heun2D(0.1 ,5, dglF4x, dglF4y, (0, (1, 0)))
    compareResults2D(he4, ef4, "Heun 2D DGL4")
    
    #Aufgabe 1.5
    print("A-Stabilität expliziter Euler: "+str(aStability(expEuler, "exp")))
    print("A-Stabilität impliziter Euler: "+str(aStability(impEuler, "imp")))