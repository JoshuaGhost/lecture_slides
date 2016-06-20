#include "dgl2d1.h"

DGL2D1::DGL2D1()
{

}

DGL2D1::~DGL2D1()
{

}

QVector2D DGL2D1::getDgl(double curx, double cury)
{
    return QVector2D(-curx*cury,-cury);
}

