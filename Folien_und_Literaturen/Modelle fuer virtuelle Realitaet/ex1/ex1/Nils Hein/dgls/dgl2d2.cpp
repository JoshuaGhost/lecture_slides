#include "dgl2d2.h"

DGL2D2::DGL2D2()
{

}

DGL2D2::~DGL2D2()
{

}

QVector2D DGL2D2::getDgl(double curx, double cury)
{
    return QVector2D(-cury,curx);
}

