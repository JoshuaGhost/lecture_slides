#include "heun.h"

Heun::Heun()
{

}

Heun::~Heun()
{

}

QVector2D Heun::getNext(double curx, double cury, double dt)
{
    QVector2D v = QVector2D(curx,cury);
    QVector2D dg = dgl->getDgl(curx,cury);
    QVector2D xp = QVector2D(dg.x()*dt+curx,dg.y()*dt+cury);
    return 0.5*v+0.5*(xp+dt*dgl->getDgl(xp.x(),xp.y()));
}

void Heun::setDGL(DGL2D *dgl)
{
    this->dgl = dgl;
}

