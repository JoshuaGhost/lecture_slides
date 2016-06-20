  float h=0.01;
  float y_curr1[] =new float[96];
  float y_curr2[] =new float[96];
  y_curr1[0]=1;
  y_curr2[0]=1;
  for (int i=1;i<=95;i++){
    y_curr1[i]=(1-sqrt(1-4*y_curr1[i-1]*h))/(2*h);
    y_curr2[i]=(1+sqrt(1-4*y_curr2[i-1]*h))/(2*h);
    println(y_curr1[i-1]);
    //println(y_curr2[i-1]);
  }
  
