void setup()
{
float x0,y0,x,y,h,n;//variables

x0 = 0;//start point of x
y0 = 1;//start point of y
n = 100;//number of points
h = 0.1;//step

x=x0;
y=y0;

println("t; f(t) ");//collumns t is a time, f(t) is a function
println(x +"; " +y);//printing the first point

for (int i=1;i<n;i++)
{
  x=x+h;//step for x
  y=(y+h)/(1+2*h);//Euler's Implicit method

println(x +"; " +y);//results
}


}



