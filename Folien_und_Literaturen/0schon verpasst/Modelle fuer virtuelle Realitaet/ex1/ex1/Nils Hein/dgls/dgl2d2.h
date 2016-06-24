#ifndef DGL2D2_H
#define DGL2D2_H

#include "dgl2d.h"



class DGL2D2 : public DGL2D
{
public:
    DGL2D2();
    ~DGL2D2();

    // DGL2D interface
public:
    QVector2D getDgl(double curx, double cury);
};

#endif // DGL2D2_H
