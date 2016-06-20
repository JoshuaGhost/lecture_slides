#ifndef EQUATION_2_H
#define EQUATION_2_H
#include"explicitEuler.h"


class equation_2:public explicitEuler{

public:

    equation_2(double dt, int steps){
        setDt(dt);
        std::vector<double> a;
        a.push_back(1.0);
        setInitialValue(a);
        setSteps(steps);
    }

    std::vector<double> dfg(std::vector<double> f){
        std::vector<double> lsg;
        lsg.push_back(f[0]*f[0]);
        return lsg;
    }

};
#endif // EQUATION_2_H
