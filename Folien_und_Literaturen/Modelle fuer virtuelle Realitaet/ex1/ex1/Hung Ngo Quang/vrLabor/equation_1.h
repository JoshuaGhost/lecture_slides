#ifndef EQUATION_1_H
#define EQUATION_1_H
#include"explicitEuler.h"
#include <vector>


class equation_1:public explicitEuler{

public:

    equation_1(double dt, int steps){
        setDt(dt);
        std::vector<double> a;
        a.push_back(1.0);
        setInitialValue(a);
        setSteps(steps);
    }

    std::vector<double> dfg(std::vector<double>f){
        std::vector<double> lsg;
        std::vector<double> a;
        a.push_back(1.0);
        lsg=add(a, mult(f,-2));
        return lsg;
    }

};

#endif // EQUATION_1_H
