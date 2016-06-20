  size(100, 100);
  float h=0.03125;
  float y_curr[] =new float[1001];
  y_curr[0]=1;
  for (int i=1;i<=30;i++){
    println(y_curr[i-1]);
    y_curr[i]=y_curr[i-1]+h*(sq(y_curr[i-1]));
  }
  
