#include "expliciteuler.h"

ExplicitEuler::ExplicitEuler()
{

}

ExplicitEuler::~ExplicitEuler()
{
    delete dgl;
}

double ExplicitEuler::getNext(double curval, double dt)
{
    return (dgl->getDgl(curval)*dt)+curval;
}

void ExplicitEuler::setDGL(DGL* dgl)
{
    this->dgl = dgl;
}

