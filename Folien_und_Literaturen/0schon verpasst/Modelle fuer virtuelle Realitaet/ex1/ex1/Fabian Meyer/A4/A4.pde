  float h=0.100;
  float y_curr[] =new float[1001];
  float x_curr[] =new float[1001];
  float x_p;
  float y_p;
  y_curr[0]=2; 
  x_curr[0]=1; 
  for (int i=1;i<=300;i++){
    x_p=x_curr[i-1]+h*(-y_curr[i-1]);
    y_p=y_curr[i-1]+h*(x_curr[i-1]);
    
    x_curr[i]=0.5*x_curr[i-1]+0.5*(x_p+h*(-y_p));
    y_curr[i]=0.5*y_curr[i-1]+0.5*(y_p+h*(x_p));
    println(x_curr[i-1]);
    //println(y_curr[i-1]);
  }
  
