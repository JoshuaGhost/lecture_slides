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

t_0 = 0
f_0 = np.array([1,1])
########################

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
	f.append(f[i-1] + dt * np.array([-f[i-1][0]*f[i-1][1],-f[i-1][1]]))


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
