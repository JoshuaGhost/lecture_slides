from pylab import *

EULER_EXP_INTE = 0
EULER_IMP_INTE = 1

def funct_eii(dx, y):
	return (1-sqrt(1-4*y*dx))/(2*dx)
	
def funct_eei(dx, y):
	return dx*y*y+y

def ei(y0, x_start, x_end, x_nump, method):
	y = y0
	dx=(float)(x_start+x_end)/(float)(x_nump)
	for x in range(x_nump) :
		yield y
		if method == EULER_EXP_INTE:
			y = funct_eei(dx, y)
		else:
			y = funct_eii(dx, y)

x_start = 0
x_end = 10
x_nump = 10000
y_start = 0
y_end = 1000

x = np.linspace(x_start, x_end, x_nump, endpoint=True)

y0_start = -1
y0_end = -1
stepy0 = 1
for i in range((int)((y0_end-y0_start)*stepy0+1)):
	y0 =(float)(i)/(float)(stepy0)+(float)(y0_start)
	y = [yt for yt in ei(y0, x_start, x_end, x_nump, EULER_EXP_INTE)]
	plot(x, y, color = 'blue', linestyle = '-', label = 'eular explicit Intergration')
	y = [yt for yt in ei(y0, x_start, x_end, x_nump, EULER_IMP_INTE)]
	plot(x, y, color = 'red', linestyle = '-', label = 'eular implicit Intergration')
#ylim(y_start, y_end)
legend(loc = 'upper left')
title('comparition of y\'=y^2 when y0 = -1')
print sqrt(4)
show()