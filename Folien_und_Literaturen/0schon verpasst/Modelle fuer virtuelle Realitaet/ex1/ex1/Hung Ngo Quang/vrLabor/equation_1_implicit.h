#ifndef EQUATION_1_IMPLICIT_H
#define EQUATION_1_IMPLICIT_H
#include"implicitEuler.h"


class equation_1_implicit:public implicitEuler{

public:

    equation_1_implicit(double dt, int steps){
        setDt(dt);
        setInitialValue(1);
        setSteps(steps);
    }

    double nextValue(double f){
        return (f/(1-getDt()));
    }

};
#endif // EQUATION_1_IMPLICIT_H
