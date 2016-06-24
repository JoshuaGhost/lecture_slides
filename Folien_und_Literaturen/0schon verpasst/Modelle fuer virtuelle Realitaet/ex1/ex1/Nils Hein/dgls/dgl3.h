#ifndef DGL3_H
#define DGL3_H

#include "dgl.h"



class DGL3 : public DGL
{
public:
    DGL3();
    ~DGL3();

    // DGL interface
public:
    double getDgl(double curval);
};

#endif // DGL3_H
