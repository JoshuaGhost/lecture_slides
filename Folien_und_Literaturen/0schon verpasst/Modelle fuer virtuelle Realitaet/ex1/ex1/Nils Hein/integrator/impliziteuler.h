#ifndef IMPLIZITEULER_H
#define IMPLIZITEULER_H

#include "iintegrator.h"



class ImplizitEuler : public IIntegrator
{
public:
    ImplizitEuler();
    ~ImplizitEuler();

    // IIntegrator interface
public:
    double getNext(double curval, double dt);
    void setDGL(DGL *dgl);
private:
        DGL* dgl = 0;
};

#endif // IMPLIZITEULER_H
