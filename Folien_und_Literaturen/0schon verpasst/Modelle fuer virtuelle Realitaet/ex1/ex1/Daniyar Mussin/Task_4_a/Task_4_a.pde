
void setup() {
float x0,y0,x,y,h,n,yx,ynext,yxnext,xprev;//real numbers for all variables
x0 = 0;//start point of x
y0 = 1;//start point of y
n = 10;//number of points
h = 0.1;//step
x=x0;
y=y0;
yx = y0;
println("t; vector f(t) ");//collumn with time t and function f(t)
println(x +"; (" +y+ ", " +yx+")");//first point with start coordinates
for (int i=1;i<n;i++)//Loop for all number of points
{
xprev = x;
x=x+h;//step for x
yxnext = yx + h*funcy(xprev,yx);//Heuns formula for y komponent
yx = yx + (h/2)*(funcy(xprev,yx)+funcy(x,yxnext));//Heuns formula for y 

ynext=y+h*funcx(xprev,y);//Heuns formula for x komponent
 y=y+(h/2)*(funcx(xprev,y)+funcx(x,ynext));//Heuns method for x 

println(x +"; (" +y+ ", " +yx+")");//Printing the aproximation's result, x is time t, y is vector from of function f
}
}
float funcx(float x,float y)//this is for all functions
{
float[] f = {0,0};
float[] c1 = {1,0};
float[] c2 = {0,1};
f[0]=-x*y;//Function that is f' or (df/dt)
return f[0];
}
float funcy(float x,float y)//this is for all functions
{
float[] f = {0,0};
float[] c1 = {1,0};
float[] c2 = {0,1};
f[1]=(-y);//Function that is f' or (df/dt)
return f[1];
}

