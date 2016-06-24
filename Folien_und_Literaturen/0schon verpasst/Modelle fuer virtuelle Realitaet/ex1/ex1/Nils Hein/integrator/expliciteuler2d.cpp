#include "expliciteuler2d.h"

ExplicitEuler2D::ExplicitEuler2D()
{

}

ExplicitEuler2D::~ExplicitEuler2D()
{

}

QVector2D ExplicitEuler2D::getNext(double curx, double cury, double dt)
{
    QVector2D dg = dgl->getDgl(curx,cury);
    return QVector2D(dg.x()*dt+curx,dg.y()*dt+cury);
}

void ExplicitEuler2D::setDGL(DGL2D *dgl)
{
    this->dgl = dgl;
}

