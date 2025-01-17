from pylab import *
from mpl_toolkits.mplot3d import Axes3D

EULER_EXP_INTE = 0
EULER_IMP_INTE = 1

def func_eei(dt, x, y):
	return (-dt*y+x, dt*x+y)

def ei(x0, y0, t_start, t_end, t_nump, method):
	y = y0
	x = x0
	dt=(float)(t_start+t_end)/(float)(t_nump)
	for t in range(t_nump) :
		yield x,y
		x,y = func_eei(dt, x, y)

fig = figure()
ax = fig.gca(projection = '3d')
t_start = 0
t_end = 10
t_nump = 10000

y_start = 0
y_end = 10
x_start = 0
x_end = 10

t = np.linspace(t_start, t_end, t_nump, endpoint=True)
x0 = 1
y0 = 1
x=[]
y=[]
for xt,yt in ei(x0, y0, t_start, t_end, t_nump, EULER_EXP_INTE):
	x.append(xt)
	y.append(yt)

ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('t')
title('2dimensional DGL2 with x0 = 1, y0 = 1')
ax.plot(x, y, t)
show()
