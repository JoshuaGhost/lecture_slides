#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include "integrator/iintegrator.h"
#include <QGraphicsScene>
#include <QMainWindow>
#include <dgls/dgl2d.h>
#include <integrator/iintegrator2d.h>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

public slots:
    void handleButton();
    void handle2D();
    void calcDGL();
    void calcAStability();
private:
    Ui::MainWindow *ui;
    QVector<DGL*> dgls;
    QVector<DGL2D*> dgl2ds;
    QVector<IIntegrator*> integrator;
    QVector<IIntegrator2D*> integrator2d;
};

#endif // MAINWINDOW_H
