#!/usr/bin/python
# author Christian Henning - 2843590
from sympy import *
from sympy.parsing.sympy_parser import parse_expr
import matplotlib.pyplot as plt
import os
import math
import sys

y_cur, y_next, y, x_cur, x_next, x, dx = symbols('y_cur, y_next, y, x_cur, x_next, x, dx')
stabilityTest = 0 # used when probing for A stability
epsilon = 0 # used when probing for A stability (break condition)

if len(sys.argv) <> 6:

#########################
### input values
	# step size
	dx = 0.01
	numberOfSteps = 100

	# initial values
	x_0 = 0
	y_0 = 1

	# state the differential equation f(y, x). 
	# y' = f(y, x)
	#derivative = 1 - 2 * y
	derivative = y**2
########################

# command line inputs for A stability tester
# exit codes: 0 - test passed
#             1 - invalid input arguments
#             2 - method not strictly monotonic decreasing
#             3 - no A stability test was running or test did not finished properly (maybe bacause of the occurence of complex number)
else:
	try:
		dx = float(sys.argv[1])
		numberOfSteps = 0 # just to avoid uninitialized variables
		x_0 = float(sys.argv[2])
		y_0 = float(sys.argv[3])
		derivative = parse_expr(sys.argv[4])
		epsilon = float(sys.argv[5])		

		stabilityTest = 1

	except:
		print 'Error: Invalid input arguments.'
		sys.exit(1)

#Note, that for the explicit euler method you have to exchange the function y by its current value y_cur
derivative = derivative.subs(x, x_cur)
derivative = derivative.subs(y, y_cur)

# method to write results into csv file
def writeCSVFile(x,y):
	# the csv file will have the same name as this script but with the extension '.csv'
	fileName, fileExtension = os.path.splitext(os.path.basename(__file__))
	csv = open(fileName + '.csv', "w")

	for i in range(0,len(x)):
  		csv.write("%f\t%f\n" % (x[i], y[i]))

	csv.close()

# we are considering differential equations like: y' = f(y, x)

eulerMethod = y_cur + dx * derivative

x = list([x_0])
y = list([y_0])

i = 0;
while stabilityTest == 1 or i < numberOfSteps:
	i = i+1
	x.append(x[i-1] + dx)

	tempExpr = eulerMethod.subs(x_cur, x[i-1]);
	tempExpr = tempExpr.subs(y_cur, y[i-1]);

	try:
		# add next value
		y.append(float(tempExpr))
		
	except:
		print 'Warning: Result seems to be complex. Approximation stopped.'
		del x[-1]
		break

	if stabilityTest == 1:

		# function is not strictly monotonic decreasing			
		if y[i] >= y[i-1]:
			sys.exit(2)

		if math.fabs(y[i]) < epsilon:
			sys.exit(0)

if stabilityTest <> 1:
	writeCSVFile(x,y);

	# plot the solution
	plt.plot(x,y)
	plt.xlabel('x')
	plt.ylabel('y(x)')
	plt.show()

sys.exit(3)

