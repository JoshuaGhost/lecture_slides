//Heun's method for function dy/dt=2-exp(-4*x)-2*y;
void setup()
{
float x0,y0,x,y,h,n,xprev,ynext;//real numbers for all variables

x0 = 0;//start point of x
y0 = 1;//start point of y
n = 6;//number of points
h = 0.1;//step

x=x0;
y=y0;

println("t; f(t) ");//collumn with time t and function f(t)
println(x +"; " +y);//first point with start coordinates

for (int i=1;i<n;i++)//Loop for all number of points
{ xprev=x;
  x=x+h;//step for x
ynext=y+h*(func(xprev,y));
y=y+(h/2)*(func(xprev,y)+func(x,ynext));//Explicit formula of Euler's Method

println(x +"; " +y);//Printing the aproximation's result, x is time t, y is function f
}


}

float func(float x,float y)//this is for all functions
{
float f;
f=2-exp(-4*x)-2*y;//Function that is f' or (df/dt)
return f;
}
  

