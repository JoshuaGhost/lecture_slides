#ifndef DGL1_H
#define DGL1_H
#include "dgl.h"

class DGL1 : public DGL
{
public:

    DGL1();
    ~DGL1();

    // DGL interface
public:
    double getDgl(double curval);
};

#endif // DGL1_H
