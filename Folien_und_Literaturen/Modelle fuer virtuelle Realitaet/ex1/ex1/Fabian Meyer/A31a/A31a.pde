  float h=0.100;
  float y_curr[] =new float[301];
  float x_curr[] =new float[301];
  y_curr[0]=4; 
  x_curr[0]=4; 
  for (int i=1;i<=300;i++){
    x_curr[i]=x_curr[i-1]+h*(-y_curr[i-1]*x_curr[i-1]);
    y_curr[i]=y_curr[i-1]+h*(-y_curr[i-1]);
    //println("X : " + x_curr[i-1] + " Y : "+ y_curr[i-1]);
  }
  
