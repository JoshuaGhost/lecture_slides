#ifndef EQUATION_3_H
#define EQUATION_3_H
#include "explicitEuler.h"

class equation_3:public explicitEuler{

public:

    equation_3(double dt, int steps){
        setDt(dt);
        std::vector<double> a;
        a.push_back(1.0);
        a.push_back(1.0);
        setInitialValue(a);
        setSteps(steps);
    }

    std::vector<double> dfg(std::vector<double> f){
        std::vector<double> lsg;
        lsg.push_back(-f[0]*f[1]);
        lsg.push_back(-f[1]);
        return lsg;
    }

};
#endif // EQUATION_3_H
