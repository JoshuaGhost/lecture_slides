#ifndef IINTEGRATOR2D_H
#define IINTEGRATOR2D_H

#include <dgls/dgl2d.h>



class IIntegrator2D
{
public:
    IIntegrator2D();
    ~IIntegrator2D();
    virtual QVector2D getNext(double curx,double cury, double dt)= 0;
    virtual void setDGL(DGL2D* dgl) = 0;
};

#endif // IINTEGRATOR2D_H
