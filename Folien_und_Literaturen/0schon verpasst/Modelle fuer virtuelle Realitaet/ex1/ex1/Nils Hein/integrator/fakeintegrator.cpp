#include "fakeintegrator.h"

FakeIntegrator::FakeIntegrator()
{

}

FakeIntegrator::~FakeIntegrator()
{

}

double FakeIntegrator::getNext(double curval, double dt)
{
    return 0.9*curval;
}

void FakeIntegrator::setDGL(DGL *dgl)
{

}

