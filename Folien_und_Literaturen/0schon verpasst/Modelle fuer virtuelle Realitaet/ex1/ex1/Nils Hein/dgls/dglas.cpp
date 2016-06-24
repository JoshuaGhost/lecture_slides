#include "dglas.h"

DGLAS::DGLAS()
{

}

DGLAS::~DGLAS()
{

}

double DGLAS::getDgl(double curval)
{
    return k*curval;
}

void DGLAS::setK(double k)
{
    this->k = k;
}

