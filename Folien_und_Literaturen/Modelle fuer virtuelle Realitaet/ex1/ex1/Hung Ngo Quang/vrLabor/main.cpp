#include "equation_1.h"
#include "equation_1_heunsMethod.h"
#include "equation_1_implicit.h"
#include "equation_2.h"
#include "equation_2_heunsMethod.h"
#include "equation_2_implicit.h"
#include "equation_3.h"
#include "equation_3_implicit.h"
#include "equation_4.h"
#include "a_stability.h"
#include <math.h>

int main(int argc, char *argv[])
{
   equation_1 equation1= equation_1(0.1, 100);
   equation_2 equation2= equation_2(0.1, 100);
   equation_3 equation3= equation_3(0.1, 100);
   equation_4 equation4= equation_4(0.1, 100);
   equation_1_implicit equation1_implicit= equation_1_implicit(0.1, 100);
   equation_2_implicit equation2_implicit= equation_2_implicit(0.1, 100);
   equation_3_implicit equation3_implicit= equation_3_implicit(0.1, 100);
   equation_1_heunsMethod equation1_heun= equation_1_heunsMethod(0.1, 100);
   equation_2_heunsMethod equation2_heun= equation_2_heunsMethod(0.1, 100);
   a_stability stability_test=a_stability(0.1,100);


   std::vector<std::vector<double > > lsg;
   //Test der Integratoren
/*
   std::cout<<"Expliziter Eulerverfahren" <<"\n"<<"\n";

   lsg=equation1.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\t"<<"Vergleichswert" << "\tFehler"<<  "\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<<0.5*exp(-2*lsg[i][0])+0.5<<"\t"<<fabs(lsg[i][1]-(0.5*exp(-2*lsg[i][0])+0.5)) <<"\n";
   }


   std::cout<<"\n";

   lsg=equation2.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\t"<<"Vergleichswert" << "Fehler" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<<1/(1-lsg[i][0])<<"\t"<<(lsg[i][1]-1/(1-lsg[i][0]))<<"\n";
   }

   std::cout<<"\n";

   lsg=equation3.integrate();
   std::cout<<"t"<<"\t"<< "X\tY" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<< lsg[i][2]<<"\n";
   }

   std::cout<<"\n";

   lsg=equation4.integrate();
   std::cout<<"t"<<"\t"<< "X" << "\tY" <<"\t"<<"Vergleichswert" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<< lsg[i][2]<<"\t"<< cos(lsg[i][0])<<"\t"<< sin(lsg[i][0])<<"\n";
   }
   std::cout<<"\n"<<"\n";


   std::cout<<"Impliziter Eulerverfahren" <<"\n"<<"\n";


   lsg=equation1_implicit.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\t"<<"Vergleichswert" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<<"\t"<<exp(lsg[i][0])<<"\n";
   }

   std::cout<<"\n";
   lsg=equation2_implicit.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\t"<<"Vergleichswert" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<<"\t"<<0.5*exp(-2*lsg[i][0])+0.5<<"\n";
   }

   std::cout<<"\n";
   lsg=equation3_implicit.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\t"<<"Vergleichswert" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<<"\t"<<1/(1-lsg[i][0])<<"\n";
   }

   std::cout<<"\n"<<"\n";


   std::cout<<"Heuns Verfahren" <<"\n"<<"\n";


   lsg=equation1_heun.integrate();
   std::cout<<"t"<<"\t"<< "Integratorwert"<<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\n";
   }

   std::cout<<"\n";

   lsg=equation2_heun.integrate();
   std::cout<<"t"<<"\t"<< "X\tY" <<"\t"<<"Vergleichswert" <<"\n";
   for(int i=0; i<lsg.size();i++){
       std::cout<<lsg[i][0]<<"\t"<<lsg[i][1] <<"\t"<< lsg[i][2]<<"\t"<< cos(lsg[i][0])<<"\t"<< sin(lsg[i][0])<<"\n";
   }
   std::cout<<"\n"<<"\n";
*/
   //A-Stabilitätstest alller drei Verfahren;

   stability_test.stabilityTest();
/**/
}
