#!/usr/bin/python
# author Christian Henning - 2843590
from mpl_toolkits.mplot3d import Axes3D
import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
import os
import math

#########################
### input values
# we are considering differential equations like: f' = g(f, t) with f is a vector function

# step size
dt = 0.1
numberOfSteps = 100

t_0 = 1
f_0 = np.array([1,1])
########################

# estimate the quality of our solution compared to the exact solution
def qualityOfSolution(t, f):
	residualAvg = np.array([1,1])
	residual = list()

	print 'Quality of Solution:'

	# exact solution is
	# f = c*[cos(t+d),sin(t+d)]
	d = math.asin(math.sqrt(1.0/(1.0 + (f_0[0]/f_0[1])**2))) - t_0
	c = f_0[0] / math.cos(t_0 + d)
	print d
	print c

	for i in range(0,len(t)):
		# use exact equation
		f_exact = c*np.array([math.cos(t[i] + d),math.sin(t[i] + d)])

		residual.append(np.absolute(f_exact - f[i]))
		residualAvg = residualAvg + residual[i]

		print 'Residual for t=' + str(t[i]) + ' is (' + str(residual[i][0]) + ',' + str(residual[i][1]) + ')'

	residualAvg = residualAvg / len(t)
	print 'Average residual is (' + str(residualAvg[0]) + ',' + str(residualAvg[1]) + ')'

	r_x=list();
	r_y=list();

	for i in range(0,len(f)):
		r_x.append(residual[i][0])
		r_y.append(residual[i][1])

	# plot residual
	fig = plt.figure()
	ax = fig.gca(projection='3d')
	ax.plot(t,r_x,r_y)
	ax.set_xlabel('t')
	ax.set_ylabel('res_x')
	ax.set_zlabel('res_y')
	plt.show()

# method to write results into csv file
def writeCSVFile(t,f):
	# the csv file will have the same name as this script but with the extension '.csv'
	fileName, fileExtension = os.path.splitext(os.path.basename(__file__))
	csv = open(fileName + '.csv', "w")

	for i in range(0,len(t)):
  		csv.write("%f\t%f\t%f\n" % (t[i], f[i][0], f[i][1]))

	csv.close()

t = list([t_0])
f = list([f_0])

for i in range(1,numberOfSteps):
	t.append(t[i-1] + dt)

	# explicit euler approach
	f.append(f[i-1] + dt * np.array([-f[i-1][1],f[i-1][0]]))


writeCSVFile(t,f);

x=list();
y=list();

for i in range(0,len(f)):
	x.append(f[i][0])
	y.append(f[i][1])

# plot the solution
fig = plt.figure()
ax = fig.gca(projection='3d')
ax.plot(t,x,y)
ax.set_xlabel('t')
ax.set_ylabel('f_x')
ax.set_zlabel('f_y')
plt.show()

# Get an approximation of the average residual compared to the exact solution
qualityOfSolution(t,f)
