 float h=0.100;
  float y_curr[] =new float[301];
  y_curr[0]=1;  
  for (int i=1;i<=300;i++){
    y_curr[i]=(y_curr[i-1]+h)/(1+2*h);
    println(y_curr[i-1]);
  }
  
