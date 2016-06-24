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
	dx = 0.1
	numberOfSteps = 100

	# initial values
	x_0 = 0
	y_0 = 1

	# state the differential equation f(y, x).
	# y' = f(y x)
	derivative = 1 - 2 * y
	#derivative = y**2
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

#Note, that for the implicit euler method you have to exchange the function y by its next value y_next
derivative = derivative.subs(x, x_next)
derivative = derivative.subs(y, y_next)

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
equation = Eq(y_next, eulerMethod)
# solve equation for y_next
# FIXME: just the first solution is used here, but there might be more than 1
#nextValue = solve(equation, y_next)[0]
temp = solve(equation, y_next)
if len(temp) <> 1:
	print 'Warning: the implicit euler method allows more than one solution'

nextValue = temp[0]
nextValue

x = list([x_0])
y = list([y_0])

i = 0;
while stabilityTest == 1 or i < numberOfSteps:
	i = i+1
	x.append(x[i-1] + dx)

	tempExpr = nextValue.subs(x_next, x[i]);
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

