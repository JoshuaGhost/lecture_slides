#!/bin/bash

for i in `cat filelist`;
do
	wget http://www.kbs.uni-hannover.de/Lehre/pers15/wp-content/uploads/$i;
done;