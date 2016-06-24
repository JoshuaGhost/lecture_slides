#!/usr/bin/python
# author Christian Henning - 2843590
# Parameters:
#      - 1. number of tries - How many different values of k should be probed before printing the result (there is an minimum number specified to avoid too small numbers)
#      - 2. epsilon - Break condition to accept and approximation as valid. Thus if the function value is lower as epsilon it is assumed as converging to zero 
import sys
import random
import subprocess

MINIMUM_TRIES = 10

# initial values
t_0 = 0
f_0 = 1

# step size
dt  = 1

if len(sys.argv) == 3:
		tries = int(sys.argv[1])
		epsilon = float(sys.argv[2])

		if tries < MINIMUM_TRIES:
			print "Error: First parameter too small to get valid results!"
			sys.exit(1)

else:
	print "Error: Invalid number of parameters!"
	sys.exit(1)

def testAStability(name, dim):
	result = 1

	for i in range(0, tries):	

		# get random k between 0 and float_max
		k = 0
		while k == 0:
			k = random.random() * sys.float_info.max	

		if dim == 1:
			#os.system(name + " " + str(t_0) + " " + str(f_0) + " -" + str(k) + "*y " + epsilon)
			ret = subprocess.call(["python"] + (name + " " + str(dt) + " " + str(t_0) + " " + str(f_0) + " -" + str(k) + "*y " + str(epsilon)).split())
		elif dim == 2:
			#os.system(name + " " + str(t_0) + " " + str(f_0) + " " + str(f_0) + " -" + str(k) + "*f_x " + " -" + str(k) + "*f_y " + epsilon)
			ret = subprocess.call(["python"] + (name + " " + str(dt) + " " + str(t_0) + " " + str(f_0) + " " + str(f_0) + " -" + str(k) + "*f_x " + " -" + str(k) + "*f_y " + str(epsilon)).split())
		else:
			result = 0
			break

		if ret <> 0:
			result = 0
			break

	if result == 0:
		print name + " is not A-stable."
	else: 
		print name + " is probably A-stable."

		
testAStability("explicit_euler.py", 1)
testAStability("implicit_euler.py", 1)
testAStability("explicit_euler_2D.py", 2)
testAStability("heun_2D.py", 2)		
