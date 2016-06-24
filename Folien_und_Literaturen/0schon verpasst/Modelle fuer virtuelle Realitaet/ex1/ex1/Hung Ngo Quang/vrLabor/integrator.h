#ifndef INTEGRATOR_H
#define INTEGRATOR_H
#include <vector>

class integrator{

private:
    double dt;
    int steps;
    std::vector<double> initialValue;

public:

    virtual std::vector<double> dfg(std::vector<double> f)=0;
    virtual std::vector<std::vector< double > > integrate()=0;

    std:: vector<double> mult(std::vector<double> a, double c){
        std::vector<double> result;
        for(int i=0; i<a.size(); i++){
            result.push_back(a[i]*c);
        }
        return result;
    }

    std:: vector <double> add(std::vector<double> a, std::vector<double> b){
        std::vector<double> result;
        if(a.size()==b.size()){
            for(int i=0; i<a.size(); i++){
                result.push_back(a[i]+b[i]);
            }
            return result;
        }
    }

    void setDt(double dt){
        this->dt=dt;
    }

    void setSteps(int steps){
        this->steps=steps;
    }

    void setInitialValue(std::vector<double> initialValue){
        this->initialValue=initialValue;
    }




};
#endif // INTEGRATOR_H
