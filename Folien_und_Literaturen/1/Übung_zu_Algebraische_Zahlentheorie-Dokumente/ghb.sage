import itertools
R.<x>=QQ[]

def ghb(poly):
  assert poly.is_irreducible() #Minimalpolynome sind irreduzibel
  assert poly.denominator()==1 and poly.leading_coefficient()==1 #wir wollen mit einem ganzen Element starten
  M=matrix.companion(poly)     #Darstellungsmatrix von (mal x) bzgl der Basis 1,x,x^2,...x^(n-1)
  n=poly.degree()  
  basis=[]
  for k in range(0,n):
    basis.append(x^k)
  disc=(-1)^binomial(n,2)*(poly.derivative()(x=M).det()) #discriminante der Basis 1,x,...,x^(n-1)
  ######
  #zwei alternative Möglichkeiten die Discriminante zu berechnen
  #disc1=(-1)^binomial(n,2)*poly.derivative().resultant(poly)
  #disc2=poly.discriminant()
  ######
  for pr in list(disc.factor()): #Die Diskriminante wird minimiert. Eine ganze QQ-basis mit minimaler Diskriminante ist ghb.
    if mod(pr[1],2)==0:  #Es genügt die quadratischen Teiler zu überprüfen
      temp=extend_basis(M,basis,disc,pr[0])
      basis_neu=temp[0]
      disc_neu=temp[1]
      while abs(disc)>abs(disc_neu):
        temp=extend_basis(M,basis_neu,disc_neu,pr[0])
        basis=basis_neu
        disc=disc_neu
        basis_neu=temp[0]
        disc_neu=temp[1]
  assert disc==QQ.extension(poly,'a').discriminant(), "Diskriminanten von Sage und ghb verschieden. Bug in ghb?"
  return([basis,disc,poly])

def is_integral(M,f):
  return(f(M).characteristic_polynomial().denominator()==1)

def extend_basis(M,basis,disc,p): #prüfe Repräsentanten von <1/b*basis>/<basis> auf Ganzheit
  n=len(basis)
  for coordinate in itertools.product(range(0,p),repeat=n):
    f=0 #der Repräsentant
    for k in range(0,n):
      f=f+coordinate[k]*basis[k]/p
    if f!=0 and is_integral(M,f):
      for i in range(0,n):
        if coordinate[i]!=0:
          basis[i]=f
          return([basis,disc/p^2]) #ganzes Element gefunden, Basis ergänzt
  return([basis,disc]) #nichts Neues

def trafo_matrix(basis):
  #Rückgabe: Matrix mit Spalten=Koordinatendarstellung der Basis bzgl. 1,x,x^2,...,x^(n-1)
  n=len(basis)
  koeffizienten_matrix=matrix(QQ,n,n)
  for k in range(0,n):
    b=basis[k]
    b=matrix(b.coefficients(sparse=false))
    b=b.augment(matrix.zero(1,n-b.ncols()))
    koeffizienten_matrix[:,k]=b.transpose()
  return(koeffizienten_matrix)

def darstellungsmatrizen(basis,poly):
  M=matrix.companion(poly)
  darstellungs_liste=[]
  T=trafo_matrix(basis)
  for b in basis:
    darstellungs_liste.append(T.inverse()*b(M)*T)
  return(darstellungs_liste)
    
def spurform(darstellung):
  n=len(darstellung)
  G=matrix.zero(n,n)
  for i in range(0,n):
    for j in range(0,n):
      G[i,j]=(darstellung[i]*darstellung[j]).trace()
  return(G)


def generic(gb,D):
  S.<a,b,c,d>=QQ[]
  n=len(D)
  if n==3:
    A=a*D[0]+b*D[1]+c*D[2]
  if n==4:
    A=a*D[0]+b*D[1]+c*D[2]+d*D[3]
  print("Norm:")
  print(A.det())
  print("Spur")
  print(A.trace())
  
def print_ghb(poly):
  gb=ghb(poly)
  print("Minimalpolynom des primitiven Elements: " + str(poly))
  print("Diskriminante: " + str(gb[1].factor()))
  print("Ganzheitsbasis: " + str(gb[0]))
  dar=darstellungsmatrizen(gb[0],gb[2])
  G=spurform(dar)
  assert det(G)==gb[1] #sanity check
  print("Grammatrix der Spurform bzgl. der GhB:")
  print(G)
  generic(gb,dar)
  print("Darstellungsmatrizen:")
  for D in dar:
    print(" ")
    print(D)
 
print("Blatt 4. Aufgabe 1.")
print("a)")
print("QQ(sqrt(2),sqrt(3))")
print("Primitives element. sqrt(2)+sqrt(3)")
print_ghb(x^4-10*x^2+1)
print("b)")
print("QQ(sqrt(-1),sqrt(2))")
print("Primitives element(sqrt(-1)+sqrt(2)")
print_ghb(x^4-2*x^2+9)
print("c)")
print("QQ(2^(1/3))")
print_ghb(x^3-2)
print("d)")
print("QQ(2^(1/4))")
print_ghb(x^4-2)
print("e)")
print("QQ[x]/x^3+x^2-2x+8")
print_ghb(x^3+x^2-2*x+8)
