#ifndef DGL2D1_H
#define DGL2D1_H

#include "dgl2d.h"



class DGL2D1 : public DGL2D
{
public:
    DGL2D1();
    ~DGL2D1();
    QVector2D getDgl(double curx, double cury);
};

#endif // DGL2D1_H
