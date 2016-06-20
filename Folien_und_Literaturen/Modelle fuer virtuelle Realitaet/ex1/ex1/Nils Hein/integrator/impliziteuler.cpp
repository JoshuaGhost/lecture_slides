#include "impliziteuler.h"

ImplizitEuler::ImplizitEuler()
{

}

ImplizitEuler::~ImplizitEuler()
{
    delete dgl;
}

double ImplizitEuler::getNext(double curval, double dt)
{
    double result = curval;
    double h = 0.00000001;
    double ih =1.0/h;
    for (int i = 0; i < 100; ++i) {
        double top = (dgl->getDgl(result)*dt+curval-result);
        double bot = ((dgl->getDgl(result+h)*dt+curval-(result+h))-(dgl->getDgl(result)*dt+curval-result))*ih;
        result = result - top/bot;
    }
    return result;
}


void ImplizitEuler::setDGL(DGL *dgl)
{
    this->dgl = dgl;
}

