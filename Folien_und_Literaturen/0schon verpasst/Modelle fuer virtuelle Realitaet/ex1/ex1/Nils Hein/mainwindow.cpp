#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "dgls/dgl1.h"
#include "dgls/dgl2.h"
#include "dgls/dgl3.h"
#include "integrator/expliciteuler.h"
#include "integrator/impliziteuler.h"
#include <QVector2D>
#include <QString>
#include <QGraphicsLineItem>
#include <QKeyEvent>
#include <integrator/expliciteuler2d.h>
#include <integrator/fakeintegrator.h>
#include <integrator/heun.h>
#include <dgls/dgl2d1.h>
#include <dgls/dgl2d2.h>
#include <dgls/dglas.h>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    dgls.append(new DGL1());
    dgls.append(new DGL2());
    dgls.append(new DGL3());

    ui->cbDGL->addItem("dgl-1: f");
    ui->cbDGL->addItem("dgl-2: 1-2f");
    ui->cbDGL->addItem("dgl-3: f^2");

    dgl2ds.append(new DGL2D1());
    dgl2ds.append(new DGL2D2());

    ui->cbDGL_2->addItem("dgl2D-1: (-fx*fy,-fy)");
    ui->cbDGL_2->addItem("dgl2D-2:(-cy,cx)");

    integrator.append(new ExplicitEuler());
    integrator.append(new ImplizitEuler());
    integrator.append(new FakeIntegrator());

    ui->cbIntegrator->addItem("Explicit Euler");
    ui->cbIntegrator->addItem("Implizit Euler");
    ui->cbIntegrator->addItem("Fake Integrator");

    integrator2d.append(new ExplicitEuler2D());
    integrator2d.append(new Heun());

    ui->cbIntegrator_2->addItem("Explicit Euler");
    ui->cbIntegrator_2->addItem("Heun");

    connect(ui->pushButton,SIGNAL (released()), this, SLOT (handleButton()));
    connect(ui->pushButton_2,SIGNAL (released()), this, SLOT (handle2D()));
    connect(ui->btnAs,SIGNAL (released()), this, SLOT (calcAStability()));
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::handleButton()
{
    calcDGL();
}
//Berechnung von 2D DGL
void MainWindow::handle2D()
{
    ui->listWidget_2->clear();

    //Den in der Combobox ausgewählten Integrator mit der ausgewählten DGL laden
    integrator2d.at(ui->cbIntegrator_2->currentIndex())->setDGL(dgl2ds.at(ui->cbDGL_2->currentIndex()));

    //Zeitschritt setzen
    double dt = ui->sbDt_2->value();
    double total = ui->sbEt_2->value()/dt;

    //Ergebnisliste vorbereiten und startwerte setzen
    QVector<double> x,y,t;
    x.append(1.0);
    y.append(1.0);
    t.append(0.0);
    ui->progressBar->setMinimum(0);
    ui->progressBar->setMaximum(total);

    //Liste mit Ergebnissen des Integrators füllen
    for(int i = 0; i< total; ++i){
        t.append(t[i]+dt);
        QVector2D cur = integrator2d.at(ui->cbIntegrator_2->currentIndex())->getNext(x[i],y[i],dt);
        x.append(cur.x());
        y.append(cur.y());

        //Guielemente updaten
        ui->listWidget_2->addItem("" + QString::number(t[i])+ ": " + QString::number(x[i]) + " ; " + QString::number(y[i]));
        ui->progressBar->setValue(i);
    }
    ui->progressBar->setValue(total);

    //Graph zeichnen
    ui->customPlot_2->addGraph();
    ui->customPlot_2->graph(0)->setData(x,y);
    ui->customPlot_2->setInteraction(QCP::iRangeDrag, true);
    ui->customPlot_2->setInteraction(QCP::iRangeZoom, true);
    ui->customPlot_2->replot();
}

//Berechnung der Eindimensionalen DGL
void MainWindow::calcDGL()
{
    ui->listWidget->clear();

    //Den in der Combobox ausgewählten Integrator mit der ausgewählten DGL laden
    integrator.at(ui->cbIntegrator->currentIndex())->setDGL(dgls.at(ui->cbDGL->currentIndex()));

    //Zeitschritt setzen
    double dt = ui->sbDt->value();
    double total = ui->sbEt->value()/dt;

    //Ergebnisliste erzeugen
    QVector<double> x,y;
    x.append(0.0);
    y.append(0.1);
    ui->progressBar->setMinimum(0);
    ui->progressBar->setMaximum(total);

    //Liste mit ergebnissen des Integrators füllen
    for(int i = 0; i< total; ++i){
        x.append(x[i]+dt);
        y.append(integrator.at(ui->cbIntegrator->currentIndex())->getNext(y[i],dt));

        //Gui elemente updaten
        ui->listWidget->addItem("" + QString::number(x[i]) + " ; " + QString::number(y[i]));
        ui->progressBar->setValue(i);
    }
    ui->progressBar->setValue(total);
    //Graph zeichnen
    ui->listWidget->addItem("" + QString::number(x[total]) + " ; " + QString::number(y[total]));
    ui->customPlot->addGraph();
    ui->customPlot->graph(0)->setData(x,y);
    ui->customPlot->setInteraction(QCP::iRangeDrag, true);
    ui->customPlot->setInteraction(QCP::iRangeZoom, true);
    ui->customPlot->replot();
}
//A-Stability berechnung
void MainWindow::calcAStability()
{
    //Zeitschritt setzen
    double dt = ui->sbDt->value();
    double total = ui->sbEt->value()/dt;
    //Gefährliche k werte sind 1<k*dt daher k genau so setzen
    double k = -2/dt;

    //A-Stability test dgl laden
    DGLAS* dg = new DGLAS();
    dg->setK(k);

    integrator.at(ui->cbIntegrator->currentIndex())->setDGL(dg);

    //Ergebnissliste vorbereiten
    QVector<double> x,y;
    x.append(0.0);
    y.append(1.0);
    ui->progressBar->setMinimum(0);
    ui->progressBar->setMaximum(total);
    //Berechnung durchführen
    //sobald ein wert nicht Monoton fallend ist abbruch und "nicht Stabil" ausgeben
    for(int i = 0; i< total; ++i){
        x.append(x[i]+dt);
        y.append(integrator.at(ui->cbIntegrator->currentIndex())->getNext(y[i],dt));
        if(y[i+1]>y[i]){
            ui->lbAs->setText("nicht Stabil");
            ui->progressBar->setValue(total);
            return;
        }
        ui->progressBar->setValue(i);
    }
    ui->lbAs->setText("Stabil");
}
