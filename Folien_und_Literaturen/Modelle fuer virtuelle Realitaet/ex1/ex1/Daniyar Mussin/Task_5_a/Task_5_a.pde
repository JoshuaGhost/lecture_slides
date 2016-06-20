float k=1;
void setup()
{
double x0,y0,x,y,h,n,ynew,count;//real numbers for all variables

x0 = 0;//start point of x
y0 = 1;//start point of y
n = 400;//number of points
h = 0.8;//step

x=x0;
y=y0;
count=1;
//println("t; f(t) ");//collumn with time t and function f(t)
//println(x +"; " +y);//first point with start coordinates

for (int i=1;i<n;i++)//Loop for all number of points
{ynew = y;


y=y+h*func(x,y);//Explicit formula of Euler's Method
if (y/ynew<1)//Condition for convergence
{
  count++;
}
x=x+h;//step for x
//println(x +"; " +y);//Printing the aproximation's result, x is time t, y is function f
}
//println(count);
if (count == n) 
{
  println("Integrator is A - stable");
}else 
 println("Integrator is NOT A - stable");

}

double func(double x,double y)//this is for all functions
{
double f;
f=-k*y;//Function that is f' or (df/dt)
return f;
}
  

