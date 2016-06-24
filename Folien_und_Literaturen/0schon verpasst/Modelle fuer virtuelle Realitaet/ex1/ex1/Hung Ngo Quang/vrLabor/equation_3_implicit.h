#ifndef EQUATION_3_IMPLICIT_H
#define EQUATION_3_IMPLICIT_H
#include"implicitEuler.h"
#include<cmath>


class equation_3_implicit:public implicitEuler{

public:

    equation_3_implicit(double dt, int steps){
        setDt(dt);
        setInitialValue(1);
        setSteps(steps);
    }

    double nextValue(double f){
        return (-sqrt((1/(4*getDt()*getDt()))-(f/getDt()))+(1/(2*getDt())));
    }

};
#endif // EQUATION_3_IMPLICIT_H
