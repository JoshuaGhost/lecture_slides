#ifndef FAKEINTEGRATOR_H
#define FAKEINTEGRATOR_H

#include "iintegrator.h"



class FakeIntegrator : public IIntegrator
{
public:
    FakeIntegrator();
    ~FakeIntegrator();

    // IIntegrator interface
public:
    double getNext(double curval, double dt);
    void setDGL(DGL *dgl);
};

#endif // FAKEINTEGRATOR_H
