import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

import static choco.Choco.*;

public class POV {
    public static void main(String[] args) {

        //Ensemble des voitures à produire
        int V = 25;
        //Ensemble des options disponibles
        int O = 5;


        // Initialisation de l'instance
        int[][] r = {
                {0,1,0,0,0},
                {1,0,1,0,1},
                {1,1,0,0,0},
                {0,1,0,1,0},
                {0,1,0,0,1},
                {0,0,0,1,0},
                {1,1,1,0,0},
                {1,0,0,1,0},
                {1,0,1,0,0},
                {0,0,1,0,0},
                {0,1,1,0,0},
                {1,1,0,1,0}
                };


        //Nombre de categories
        int catg = 12;
        //Nombre de voitures dans chaque demande
        int dem[] = {3,1,2,4,3,3,2,1,1,2,2,1};


        int p[] = {1,2,1,2,1};
        int q[] = {2,3,3,5,5};



        //Declaration du modèle
        Model m = new CPModel();



        //Déclaration des variables et des domaines
        IntegerVariable[] classementVoiture = makeIntVarArray("classement Voiture", V, 1, catg);

        IntegerVariable[][] solution =makeIntVarArray("solution", O, V, 0, 1);





        //Déclaration des contraintes

       // C1 Occurrence des voiture:
        for (int j = 0; j < catg; j++) {
            m.addConstraint(Choco.occurrence(dem[j], classementVoiture, j+1));
        }


        //C2: chaque voiture d'une categorie à des options specifique
        for (int cat = 0; cat < catg; cat++)
            for (int car = 0; car < V; car++) {
                Constraint[] C = new Constraint[O];
                for (int op = 0; op < O; op++)
                    C[op] = eq(solution[op][car], r[cat][op]);

                m.addConstraint(implies(eq(classementVoiture[car], cat+1),and(C)));
            }


        // C3 pq contraints
        for(int i = 0; i < O; i++) {
            for(int j = 0; j < V-q[i]+1; j++) {
                IntegerExpressionVariable somme = ZERO ;
                for(int c = 0; c < q[i]; c++) {
                    somme = Choco.plus(somme,solution[i][j+c]);
                }
                m.addConstraint(Choco.leq(somme,p[i]));
            }
        }


        //Declaration du Solver
        Solver s = new CPSolver();


        //Lecture du model par le Solver
        s.read(m);


        //Recherche de la premier solution
        s.solve();

        //Affichage des resultats
        int t = 1;
        do {

            System.out.println("Solution : "+t);


            // Affichage 1
            /*System.out.println("Classe || Options requises");
            for (int i = 0; i < V; i++) {
                System.out.print("  "+s.getVar(classementVoiture[i]).getVal() +"\t   ||\t");
                for (int j = 0; j < O; j++) {
                    System.out.print(s.getVar(solution[j][i]).getVal() +" ");
                }
                System.out.println("");
            }*/


            // Affichage 2
            System.out.print("   \t");
            for (int i = 0; i < V; i++) {
                System.out.print(s.getVar(classementVoiture[i]).getVal() +"\t");
            }
            System.out.println("");
            System.out.print("   \t");
            for (int i = 0; i < V; i++) {
                System.out.print("_\t");
            }
            System.out.println("");
            for (int i = 0; i < O; i++) {
                System.out.print(i +" |\t");
                for (int j = 0; j < V; j++) {
                    System.out.print(s.getVar(solution[i][j]).getVal() +"\t");
                }
                System.out.println("");
            }


            t++;
        }while (s.nextSolution());


    }
}
