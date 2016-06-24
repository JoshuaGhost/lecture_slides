#ifndef HEUNSMETHOD_H
#define HEUNSMETHOD_H
#include <vector>

class heunsMethod{

private:
    double dt;
    int steps;
    std::vector<double> initialValue;

public:

    virtual std::vector<double> dfgHeun(std::vector<double> f)=0;
    std::vector<std::vector< double > > integrate(){
        std::vector<std::vector< double > > lsgOuter;
        std::vector<double> lsgInner;
        std::vector<double> fcur=initialValue;
        lsgInner.push_back(0);
        for(int j=0;j<fcur.size();j++){
            lsgInner.push_back(fcur[j]);
        }
        lsgOuter.push_back(lsgInner);
        for(int i=1; i<steps;i++){
            lsgInner.clear();
            std::vector<double> intermediateF;
            intermediateF=add(fcur,mult(dfgHeun(fcur), dt));
            lsgInner.push_back(i*dt);
            fcur=add(fcur,mult(add(dfgHeun(fcur),dfgHeun(intermediateF)), dt/2));
            for(int j=0;j<fcur.size();j++){
                lsgInner.push_back(fcur[j]);
            }
            lsgOuter.push_back(lsgInner);
        }
        return lsgOuter;
    }

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
    double getDt(){
        return dt;
    }

    void setSteps(int steps){
        this->steps=steps;
    }

    void setInitialValue(std::vector<double> initialValue){
        this->initialValue=initialValue;
    }




};
#endif // HEUNSMETHOD_H
