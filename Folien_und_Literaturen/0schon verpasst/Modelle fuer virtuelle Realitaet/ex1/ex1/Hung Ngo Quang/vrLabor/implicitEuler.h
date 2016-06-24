#ifndef IMPLICITEULER_H
#define IMPLICITEULER_H
#include <vector>

class implicitEuler{

private:
    double dt;
    int steps;
    double initialValue;

public:

    virtual double nextValue(double f)=0;
    std::vector<std::vector< double > > integrate(){
        std::vector<std::vector< double > > lsgOuter;
        double fcur=initialValue;
        std::vector<double> lsgInner;
        lsgInner.push_back(0);
        lsgInner.push_back(fcur);
        lsgOuter.push_back(lsgInner);
        for(int i=1; i<steps;i++){
            lsgInner.clear();
            lsgInner.push_back(i*dt);
            fcur=nextValue(fcur);
            lsgInner.push_back(fcur);
            lsgOuter.push_back(lsgInner);
        }
        return lsgOuter;
    }

    void setDt(double dt){
        this->dt=dt;
    }

    void setSteps(int steps){
        this->steps=steps;
    }

    void setInitialValue(double initialValue){
        this->initialValue=initialValue;
    }

    double getDt(){
        return dt;
    }
};
#endif // IMPLICITEULER_H
