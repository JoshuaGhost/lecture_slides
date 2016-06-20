#!/usr/bin/python
# author Christian Henning - 2843590
import matplotlib.pyplot as plt
import os
import math

#########################
### input values
dx = 0.1
numberOfSteps = 100

x_0 = 0
y_0 = 1
########################

# estimate the quality of our solution compared to the exact solution
def qualityOfSolution(x, y):
	residualAvg = 0
	residual = list()

	print 'Quality of Solution:'

	# exact solution from WolframAlpha
	# y = c * exp(-2*x) + 0.5
	# compute constant with initial values
	c = (y_0 - 0.5)/math.exp(-2*x_0)

	for i in range(0,len(x)):
		# use exact equation from WolframALpha
		y_exact = c * math.exp(-2 * x[i]) + 0.5

		residual.append(math.fabs(y_exact - y[i]))
		residualAvg = residualAvg + residual[i]

		print 'Residual for x=' + str(x[i]) + ' is ' + str(residual[i]);

	print 'Average residual is ' + str(residualAvg / len(x))

	plt.plot(x,residual)
	plt.xlabel('x')
	plt.ylabel('residual')
	plt.show()

# method to write results into csv file
def writeCSVFile(x,y):
	# the csv file will have the same name as this script but with the extension '.csv'
	fileName, fileExtension = os.path.splitext(os.path.basename(__file__))
	csv = open(fileName + '.csv', "w")

	for i in range(0,len(x)):
  		csv.write("%f\t%f\n" % (x[i], y[i]))

	csv.close()

# we are considering differential equations like: y' = f(y, x)

x = list([x_0])
y = list([y_0])

for i in range(1,numberOfSteps):
	x.append(x[i-1] + dx)

	y.append((y[i-1] + dx) / (1 + 2 * dx))


writeCSVFile(x,y);

# plot the solution
plt.plot(x,y)
plt.xlabel('x')
plt.ylabel('y(x)')
plt.show()

# Get an approximation of the average residual compared to the exact solution
qualityOfSolution(x,y)

