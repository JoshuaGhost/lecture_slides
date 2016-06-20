#ifndef EXPLICITEULER_H
#define EXPLICITEULER_H
#include "iintegrator.h"

class ExplicitEuler : public IIntegrator
{
public:
    ExplicitEuler();
    ~ExplicitEuler();
    double getNext(double curval, double dt);
    void setDGL(DGL* dgl);
private:
        DGL* dgl = 0;
};

#endif // EXPLICITEULER_H
