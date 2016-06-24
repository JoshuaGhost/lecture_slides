  float h=0.100;
  float y_curr[] =new float[301];
  float x_curr[] =new float[301];
  y_curr[0]=2; 
  x_curr[0]=1; 
  for (int i=1;i<=300;i++){
    x_curr[i]=x_curr[i-1]+h*(-y_curr[i-1]);
    y_curr[i]=y_curr[i-1]+h*(x_curr[i-1]);
    //println(x_curr[i-1]);
    //println(y_curr[i-1]);
  }
  
