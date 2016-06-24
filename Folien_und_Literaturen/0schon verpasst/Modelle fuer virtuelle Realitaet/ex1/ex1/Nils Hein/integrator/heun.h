#ifndef HEUN_H
#define HEUN_H

#include "iintegrator2d.h"



class Heun : public IIntegrator2D
{
public:
    Heun();
    ~Heun();

    // IIntegrator2D interface
public:
    QVector2D getNext(double curx, double cury, double dt);
    void setDGL(DGL2D *dgl);
private:
        DGL2D* dgl = 0;
};

#endif // HEUN_H
