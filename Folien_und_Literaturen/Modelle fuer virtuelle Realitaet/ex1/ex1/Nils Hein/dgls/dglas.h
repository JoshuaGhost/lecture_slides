#ifndef DGLAS_H
#define DGLAS_H

#include "dgl.h"



class DGLAS : public DGL
{
public:
    DGLAS();
    ~DGLAS();

    // DGL interface
public:
    double getDgl(double curval);
    void setK(double k);
    double k;
};

#endif // DGLAS_H
