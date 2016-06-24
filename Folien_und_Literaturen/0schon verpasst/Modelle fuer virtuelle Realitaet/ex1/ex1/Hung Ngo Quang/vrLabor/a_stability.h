#ifndef A_STABILITY_H
#define A_STABILITY_H

#include "explicitEuler.h"
#include "implicitEuler.h"
#include "heunsMethod.h"
#include <iostream>;


class a_stability:explicitEuler,implicitEuler,heunsMethod{

private:

    double k=1;

public:
    a_stability(double dt, int steps){
        std::vector<double> a;
        a.push_back(1.0);
        explicitEuler::setDt(dt);
        explicitEuler::setSteps(steps);
        explicitEuler::setInitialValue(a);
        implicitEuler::setDt(dt);
        implicitEuler::setSteps(steps);
        implicitEuler::setInitialValue(1.0);
        heunsMethod::setDt(dt);
        heunsMethod::setSteps(steps);
        heunsMethod::setInitialValue(a);
    }

    std::vector<double> dfg(std::vector<double> f){
        std::vector<double> lsg;
        lsg=heunsMethod::mult(f,-k);
        return lsg;
    }

    double nextValue(double f){
        return (f/(1+implicitEuler::getDt()*k));
    }

    std::vector<double> dfgHeun(std::vector<double> f){
        std::vector<double> lsg;
        lsg=heunsMethod::mult(f,-k);
        return lsg;
    }

    void stabilityTest(){

        bool passed=true;
        double firstValue;
        std::vector<std::vector<double> > lsg;
        for(double j=10;j<400;j=2*j){
            lsg=explicitEuler::integrate();
            firstValue=lsg[0][1];
            for(int i=0; i<lsg.size();i++){
                if(firstValue<lsg[i][1] || lsg[i][1]<0){
                    passed=false;
                }
                firstValue=lsg[i][1];
            }
            setK(j);
        }
        if(passed){
            std::cout<<"Expliziter Euler-Verfahren ist A-stabil"<<"\n";
        }else{
            std::cout<<"Expliziter Euler-Verfahren ist nicht A-stabil"<<"\n";
        }

        setK(1);
        passed=true;
        for(double j=10;j<400;j=2*j){
            lsg=implicitEuler::integrate();
            firstValue=lsg[0][1];
            for(int i=0; i<lsg.size();i++){
                if(firstValue<lsg[i][1] || lsg[i][1]<0){
                    passed=false;
                }
                firstValue=lsg[i][1];
            }
            setK(j);
        }
        if(passed){
            std::cout<<"Impliziter Euler-Verfahren ist A-stabil"<<"\n";
        }else{
            std::cout<<"Impliziter Euler-Verfahren ist nicht A-stabil"<<"\n";
        }

        setK(1);
        passed=true;
        for(double j=10;j<400;j=2*j){
            lsg=heunsMethod::integrate();
            firstValue=lsg[0][1];
            for(int i=0; i<lsg.size();i++){
                if(firstValue<lsg[i][1] || lsg[i][1]<0){
                    passed=false;
                }
                firstValue=lsg[i][1];
            }
            setK(j);
        }
        if(passed){
            std::cout<<"Heuns Verfahren ist A-stabil"<<"\n";
        }else{
            std::cout<<"Heuns Verfahren ist nicht A-stabil"<<"\n";
        }
    }

    void setK(double k){
        this->k=k;
    }

};

#endif // A_STABILITY_H
