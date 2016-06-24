#ifndef EQUATION_1_HEUNSMETHOD_H
#define EQUATION_1_HEUNSMETHOD_H
#include "heunsMethod.h"

class equation_1_heunsMethod:public heunsMethod{

public:

    equation_1_heunsMethod(double dt, int steps){
        setDt(dt);
        std::vector<double> a;
        a.push_back(1.0);
        a.push_back(1.0);
        setInitialValue(a);
        setSteps(steps);
    }

    std::vector<double> dfgHeun(std::vector<double> f){
        std::vector<double> lsg;
        lsg.push_back(-f[0]*f[1]);
        lsg.push_back(-f[1]);
        return lsg;
    }

};
#endif // EQUATION_1_HEUNSMETHOD_H
