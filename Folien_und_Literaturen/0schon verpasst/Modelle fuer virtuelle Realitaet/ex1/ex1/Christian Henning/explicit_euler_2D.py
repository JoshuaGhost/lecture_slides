#!/usr/bin/python
# author Christian Henning - 2843590
from mpl_toolkits.mplot3d import Axes3D
from sympy import *
from sympy.parsing.sympy_parser import parse_expr
import matplotlib as mpl
import matplotlib.pyplot as plt
import numpy as np
import os
import math
import sys

f_cur_x, f_cur_y, f_next_x, f_next_y, f_x, f_y, t_cur, t_next, t, dt = symbols('f_cur_x, f_cur_y, f_next_x, f_next_y, f_x, f_y, t_cur, t_next, t, dt')
stabilityTest = 0 # used when probing for A stability
epsilon = 0 # used when probing for A stability (break condition)

if len(sys.argv) <> 8:

#########################
### input values
# we are considering differential equations like: f' = g(f, t) with f is a vector function
	# step size
	dt = 0.1
	numberOfSteps = 100

	t_0 = 0
	f_0 = np.array([1,1])

	# state the differential equation f' = g(f_cur, t).
	#derivative_x = -f_cur_x * f_cur_y
	#derivative_y = -f_cur_y
	derivative_x = -f_cur_y
	derivative_y = f_cur_x
########################

# command line inputs for A stability tester
# exit codes: 0 - test passed
#             1 - invalid input arguments
#             2 - method not strictly monotonic decreasing
#             3 - no A stability test was running or test did not finished properly (maybe bacause of the occurence of complex number)
else:
	try:
		dt = float(sys.argv[1])
		numberOfSteps = 0 # just to avoid uninitialized variables
		t_0 = float(sys.argv[2])
		f_0 = np.array([float(sys.argv[3]),float(sys.argv[4])])
		derivative_x = parse_expr(sys.argv[5])
		derivative_y = parse_expr(sys.argv[6])
		epsilon = float(sys.argv[7])		

		stabilityTest = 1

	except:
		print 'Error: Invalid input arguments.'
		sys.exit(1)

#Note, that for the explicit euler method you have to exchange the function f by its current value f_cur
derivative_x = derivative_x.subs(t, t_cur)
derivative_x = derivative_x.subs(f_x, f_cur_x)
derivative_x = derivative_x.subs(f_y, f_cur_y)
derivative_y = derivative_y.subs(t, t_cur)
derivative_y = derivative_y.subs(f_x, f_cur_x)
derivative_y = derivative_y.subs(f_y, f_cur_y)

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

eulerMethod_x = f_cur_x + dt * derivative_x
eulerMethod_y = f_cur_y + dt * derivative_y

i = 0;
while stabilityTest == 1 or i < numberOfSteps:
	i = i+1
	t.append(t[i-1] + dt)

	tempExpr_x = eulerMethod_x.subs(f_cur_x, f[i-1][0]);
	tempExpr_x = tempExpr_x.subs(f_cur_y, f[i-1][1]);
	tempExpr_x = tempExpr_x.subs(t_cur, t[i-1]);
	tempExpr_y = eulerMethod_y.subs(f_cur_x, f[i-1][0]);
	tempExpr_y = tempExpr_y.subs(f_cur_y, f[i-1][1]);
	tempExpr_y = tempExpr_y.subs(t_cur, t[i-1]);


	try:
		# add next value
		f.append(np.array([float(tempExpr_x), float(tempExpr_y)]))
		
	except:
		print 'Warning: Result seems to be complex. Approximation stopped.'
		del t[-1]
		break

	if stabilityTest == 1:

		# function is not strictly monotonic decreasing			
		if np.greater_equal(f[i],f[i-1]).all():
			sys.exit(2)

		if np.less(np.absolute(f[i]), np.array([epsilon,epsilon])).all():
			sys.exit(0)

if stabilityTest <> 1:
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

sys.exit(3)
