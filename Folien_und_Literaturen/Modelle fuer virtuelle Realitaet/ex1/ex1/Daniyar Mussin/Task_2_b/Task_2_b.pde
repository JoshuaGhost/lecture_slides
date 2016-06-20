void setup()
{
float x0,y0,x,y,h,n;//variables

x0 = 0;//start of x
y0 = 1;//start of y
n = 13;//number of points
h = 0.06;//step

x=x0;
y=y0;

println("t; f(t) ");//collumn of time and function
println(x +"; " +y);//printing the first point

for (int i=1;i<n;i++)
{
  x=x+h;//step for x
  
  y=(1-sqrt(1-4*y*h))/(2*h);//the Euler's Implicit method formula
  
println(x +"; " +y);//printing results
}


}

