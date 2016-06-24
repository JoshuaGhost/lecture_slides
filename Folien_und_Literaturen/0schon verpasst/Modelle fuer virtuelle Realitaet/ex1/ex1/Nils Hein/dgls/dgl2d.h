#ifndef DGL2D_H
#define DGL2D_H

#include <QVector2D>



class DGL2D
{
public:
    virtual QVector2D getDgl(double curx,double cury) = 0;
};

#endif // DGL2D_H
