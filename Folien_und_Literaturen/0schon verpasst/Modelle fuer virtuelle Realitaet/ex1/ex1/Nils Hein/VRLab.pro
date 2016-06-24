#-------------------------------------------------
#
# Project created by QtCreator 2015-04-18T14:08:21
#
#-------------------------------------------------

QT       += core gui
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

TARGET = VRLab
TEMPLATE = app

INCLUDEPATH += extern
SOURCES += main.cpp\
        mainwindow.cpp \
    dgls/dgl.cpp \
    dgls/dgl1.cpp \
    dgls/dgl2.cpp \
    dgls/dgl3.cpp \
    extern/qcustomplot.cpp \
    integrator/expliciteuler.cpp \
    integrator/iintegrator.cpp \
    integrator/impliziteuler.cpp \
    dgls/dgl2d1.cpp \
    dgls/dgl2d2.cpp \
    integrator/expliciteuler2d.cpp \
    integrator/iintegrator2d.cpp \
    integrator/heun.cpp \
    dgls/dglas.cpp \
    integrator/fakeintegrator.cpp

HEADERS  += mainwindow.h \
    dgls/dgl.h \
    dgls/dgl1.h \
    dgls/dgl2.h \
    dgls/dgl3.h \
    extern/qcustomplot.h \
    integrator/expliciteuler.h \
    integrator/iintegrator.h \
    integrator/impliziteuler.h \
    dgls/dgl2d.h \
    dgls/dgl2d1.h \
    dgls/dgl2d2.h \
    integrator/expliciteuler2d.h \
    integrator/iintegrator2d.h \
    integrator/heun.h \
    dgls/dglas.h \
    integrator/fakeintegrator.h

FORMS    += mainwindow.ui

DISTFILES += \
    LoesungBlatt1.txt
