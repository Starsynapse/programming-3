//-----------------------------------------------------------------------------
// Sparse.java
// Receives matrix input and outputs a pre-determined set of operations
// Written by: Eduardo Zamora
// Email: ezamora9@ucsc.edu
// Programming Assignment 3
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Sparse{
    public static void main(String[] args) throws IOException{

        // Arguement checks
	    if(args.length < 2){
	      System.err.println("Usage: Sparse infile outfile");
	      System.exit(1);
	    }

        // Fields
		Scanner in = null;
	    PrintWriter out = null;

        int n;
        int nonZeroRowsA;
        int nonZeroRowsB;

        // Set up file streams
	    in = new Scanner(new File(args[0]));
	    out = new PrintWriter(new FileWriter(args[1]));

        // Collecting matrix size and non zero entry counts
        n = Integer.valueOf(in.next());
        nonZeroRowsA = Integer.valueOf(in.next());
        nonZeroRowsB = Integer.valueOf(in.next());

        // Creating matrix of specified size
        Matrix matrixA = new Matrix(n);
        Matrix matrixB = new Matrix(n);

        // Creating arrays to organize Entry fields
        int[][] matrixPositionsA = new int[nonZeroRowsA][2];
        double[] matrixDataA = new double[nonZeroRowsA];
        int[][] matrixPositionsB = new int[nonZeroRowsB][2];
        double[] matrixDataB = new double[nonZeroRowsB];

        // Taking in the matrix location and data values for matrix A and B
        for(int i = 0; i < nonZeroRowsA; i++){
            for(int j = 0; j < 3; j++){
                if(j < 2){
                    matrixPositionsA[i][j] = Integer.valueOf(in.next());
                }
                else{
                    matrixDataA[i] = Double.valueOf(in.next());
                }
            }
        }
        for(int i = 0; i < nonZeroRowsB; i++){
            for(int j = 0; j < 3; j++){
                if(j < 2){
                    matrixPositionsB[i][j] = Integer.valueOf(in.next());
                }
                else{
                    matrixDataB[i] = Double.valueOf(in.next());
                }
            }
        }

        // Insetring entires into the A and B matrix
        for(int i = 0; i < nonZeroRowsA; i++){
            matrixA.changeEntry(matrixPositionsA[i][0], matrixPositionsA[i][1], matrixDataA[i]);
        }
        for(int i = 0; i < nonZeroRowsB; i++){
            matrixB.changeEntry(matrixPositionsB[i][0], matrixPositionsB[i][1], matrixDataB[i]);
        }

        // The output
        out.println("A has " + nonZeroRowsA + " non-zero entries:");
        out.println(matrixA.toString());

        out.println("B has " + nonZeroRowsB + " non-zero entries:");
        out.println(matrixB.toString());

        out.println("(1.5) *A =");
        out.println(matrixA.scalarMult(1.5));

        out.println("A+B =");
        out.println(matrixA.add(matrixB));

        out.println("A+A =");
        out.println(matrixA.add(matrixA));

        out.println("B-A =");
        out.println(matrixB.sub(matrixA));

        out.println("A-A =");
        out.println(matrixA.sub(matrixA));

        out.println("Transpose (A) =");
        out.println(matrixA.transpose());

        out.println("A*B =");
        out.println(matrixA.mult(matrixB));

        out.println("B*B =");
        out.println(matrixB.mult(matrixB));

        // Close file streams
	    in.close();
	    out.close();
    }
}
