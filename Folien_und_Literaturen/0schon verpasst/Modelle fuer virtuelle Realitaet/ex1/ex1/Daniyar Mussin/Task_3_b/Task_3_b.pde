void setup() {
float x0,y0,x,y,h,n, yx,ynew ;//real numbers for all variables

x0 = 0;//start point of x
y0 = 1;//start point of y
n = 10;//number of points
h = 0.1;//step

x=x0;
y=y0;
yx = y0;
println("t; vector c ");//collumn with time t and function f(x,y)
println(x +"; (" +y+", "+yx+")");//first point with start coordinates

for (int i=1;i<n;i++)//Loop for all number of points
{ynew=y;
y=y+h*funcx(x,y);//Explicit formula of x coordinate
yx = yx + h*funcy(x,ynew);//Explicit formula of y coordinate
x=x+h;//step for x
println(x +"; ("+y+", " + yx+")");//Printing the aproximation's result, x is time t, y is function f
}

}

float funcx(float x,float y)//this is for x 
{
float[] f = {0,0};
float[] c1 = {1,0};
float[] c2 = {0,1};
f[0]=x*c2[0]-y*c1[0];//Function for dx/dt

//Function that is f' or (df/dt)
return f[0];
}

float funcy(float x,float y)//this is for y
{
float[] f = {0,0};
float[] c1 = {1,0};
float[] c2 = {0,1};

f[1]=x*c2[1]-y*c1[1];//Function for dy/dt
//Function that is f' or (df/dt)
return f[1];
}
