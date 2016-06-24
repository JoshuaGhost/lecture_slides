#ifndef IINTEGRATOR_H
#define IINTEGRATOR_H

#include "./dgls/dgl.h"



class IIntegrator
{
public:
    ~IIntegrator();
    virtual double getNext(double curval, double dt)= 0;
    virtual void setDGL(DGL* dgl) = 0;
};

#endif // IINTEGRATOR_H
