#ifndef EQUATION_2_IMPLICIT_H
#define EQUATION_2_IMPLICIT_H
#include"implicitEuler.h"
#include<math.h>
#include<cmath>


class equation_2_implicit:public implicitEuler{

public:

    equation_2_implicit(double dt, int steps){
        setDt(dt);
        setInitialValue(1.0);
        setSteps(steps);
    }

    double nextValue(double f){
        return ((f+getDt())/(1+2*getDt()));
    }

};
#endif // EQUATION_2_IMPLICIT_H
