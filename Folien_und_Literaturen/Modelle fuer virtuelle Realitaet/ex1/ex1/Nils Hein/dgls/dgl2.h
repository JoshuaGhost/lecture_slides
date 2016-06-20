#ifndef DGL2_H
#define DGL2_H

#include "dgl.h"



class DGL2 : public DGL
{

public:

    DGL2();
    ~DGL2();

    // DGL interface
public:
    double getDgl(double curval);
};

#endif // DGL2_H
