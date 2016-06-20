#ifndef EXPLICITEULER2D_H
#define EXPLICITEULER2D_H

#include "iintegrator2d.h"

#include <QVector2D>



class ExplicitEuler2D : public IIntegrator2D
{
public:
    ExplicitEuler2D();
    ~ExplicitEuler2D();
    QVector2D getNext(double curx,double cury, double dt);
    void setDGL(DGL2D* dgl);
private:
        DGL2D* dgl = 0;
};

#endif // EXPLICITEULER2D_H
