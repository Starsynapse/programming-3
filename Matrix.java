//-----------------------------------------------------------------------------
// Matrix.java
// An array of lists (from List.java) and various matrix operations
// Written by: Eduardo Zamora
// Email: ezamora9@ucsc.edu
// Programming Assignment 3
//-----------------------------------------------------------------------------

//ScalarMult is not within theta values

class Matrix{
    //private class Entry{
    private class Entry{
        // Fields
        public int column;
        public double data;

        // Constructors
        Entry(int column, double data) {this.column = column; this.data = data;}

        // toString():  overrides Object's toString() method
        public String toString() {
            return ("(" + String.valueOf(column) + ", " + String.valueOf(data) + ")");
        }

        // equals(): overrides Object's equals() method
        public boolean equals(Object x){
            boolean eq = false;
            Entry that;
            if(x instanceof Entry){
                that = (Entry) x;
                eq = (this.data==that.data && this.column==that.column);
            }
            return eq;
        }
    }

    // Fields
    List rowArray[];


    // Constructor

    // Makes a new n x n zero Matrix. pre: n>=1
    Matrix(int n){
        if(n < 1){
            throw new RuntimeException("Matrix ADT / Matrix() / pre: n>=1");
        }

        rowArray = new List[n+1];

        for(int i = 1; i < n+1; i++){
            rowArray[i] = new List();
        }
    }

    // Access functions -------------------------------------------------------

    // Returns n, the number of rows and columns of this Matrix
    int getSize(){
        return (rowArray.length - 1);
    }

    // Returns the number of non-zero entries in this Matrix
    int getNNZ(){
        int size = 0;
        for(int i = 1; i < rowArray.length; i++){
            size += rowArray[i].length();
        }
        return size;
    }

    // overrides Object's equals() method
    public boolean equals(Object x){
        Matrix xMatrixCast = (Matrix)x;

        if(this == xMatrixCast){
            return true;
        }

        if(this.rowArray.length != xMatrixCast.rowArray.length) {
        	return false;
        }


        for(int i = 1; i < rowArray.length; i++){
        	if(this.rowArray[i].length() != xMatrixCast.rowArray[i].length()) {
        		return false;
        	}

            rowArray[i].moveFront();
            xMatrixCast.rowArray[i].moveFront();
            while(rowArray[i].index() >= 0){

                if(!(((Entry)(this.rowArray[i].get())).equals((Entry)(xMatrixCast.rowArray[i].get())))){
                    return false;
                }

                rowArray[i].moveNext();
                xMatrixCast.rowArray[i].moveNext();
            }
        }
        return true;
    }

    // Manipulation procedures ------------------------------------------------

    // sets this Matrix to the zero state
    void makeZero(){
        for(int i = 1; i < rowArray.length; i++){
            this.rowArray[i].clear();
        }
    }

    // returns a new Matrix having the same entries as this Matrix
    Matrix copy(){
        Matrix copyM = new Matrix(rowArray.length - 1);

        for(int i = 1; i < rowArray.length; i++){
            rowArray[i].moveFront();

            while(rowArray[i].index() >= 0){
                Entry hi = new Entry(((Entry)(this.rowArray[i].get())).column, ((Entry)(this.rowArray[i].get())).data);

                copyM.rowArray[i].append(hi);

                rowArray[i].moveNext();
            }
        }
        return copyM;
    }

    // changes ith row, jth column of this Matrix to x
    // pre: 1<=i<=getSize(), 1<=j<=getSize()
    void changeEntry(int i, int j, double x){
        if(!(1<=i && i <=getSize())){
            throw new RuntimeException("Matrix ADT / changeEntry / pre: 1<=i<=getSize()");
        }
        if(!(1<=j && j <=getSize())){
            throw new RuntimeException("Matrix ADT / changeEntry / pre: 1<=j<=getSize()");
        }

        this.rowArray[i].moveFront();
        Entry hi = new Entry(j, x);
        while(rowArray[i].index() >= 0)
        {
            if(((Entry)(this.rowArray[i].get())).column == j){
                if(x == 0){
                    this.rowArray[i].delete();
                }

                else
                    ((Entry)(this.rowArray[i].get())).data = x;
                return;
            }
            else if(((Entry)(this.rowArray[i].get())).column > j){
                if(x == 0){
                    return;
                }
                this.rowArray[i].insertBefore(hi);
                return;
            }
            this.rowArray[i].moveNext();
        }
        if(x != 0){
            this.rowArray[i].append(hi);
        }
    }

    // returns a new Matrix that is the scalar product of this Matrix with x
    Matrix scalarMult(double x){
        Matrix returnMatrix = new Matrix(this.rowArray.length - 1);

        for(int i = 1; i < rowArray.length; i++){
            this.rowArray[i].moveFront();
            while(rowArray[i].index() >= 0){
                 returnMatrix.changeEntry(i, ((Entry)(this.rowArray[i].get())).column, ((Entry)(this.rowArray[i].get())).data * x);
                 this.rowArray[i].moveNext();
            }
        }
        return returnMatrix;
    }

    List addList(List A, List B){
    	List C = new List();
    	A.moveFront();
    	B.moveFront();

    	while(A.index() >= 0 && B.index() >= 0){
    		Entry a = (Entry) A.get();
    		Entry b = (Entry) B.get();
    		Entry c;

    		if(a.column == b.column){
    			c = new Entry(a.column, a.data + b.data);
    			if(!(c.data == 0)) {
	    			C.append(c);
    			}
    			A.moveNext();
    			B.moveNext();
    		}
    		else if(a.column < b.column){
    			C.append(new Entry(a.column, a.data));
    			A.moveNext();
    		}
    		else{
    			C.append(new Entry(b.column, b.data));
    			B.moveNext();
    		}
    	}
    	while(A.index() >= 0){
    		Entry a = (Entry) A.get();
    		C.append(new Entry(a.column, a.data));
    		A.moveNext();
    	}
    	while(B.index() >= 0){
    		Entry b = (Entry) B.get();
    		C.append(new Entry(b.column, b.data));
    		B.moveNext();
    	}

    	return C;
    }

    List subList(List A, List B){
    	List C = new List();
    	A.moveFront();
    	B.moveFront();

    	while(A.index() >= 0 && B.index() >= 0){
    		Entry a = (Entry) A.get();
    		Entry b = (Entry) B.get();
    		Entry c;

    		if(a.column == b.column){
    			c = new Entry(a.column, a.data - b.data);
    			if(!(c.data == 0)){
	    			C.append(c);
    			}
    			A.moveNext();
    			B.moveNext();
    		}
    		else if(a.column < b.column){
    			C.append(new Entry(a.column, a.data));
    			A.moveNext();
    		}
    		else{
    			C.append(new Entry(b.column, -b.data));
    			B.moveNext();
    		}
    	}
    	while(A.index() >= 0){
    		Entry a = (Entry) A.get();
    		C.append(new Entry(a.column, a.data));
    		A.moveNext();
    	}
    	while(B.index() >= 0){
    		Entry b = (Entry) B.get();
    		C.append(new Entry(b.column, -b.data));
    		B.moveNext();
    	}

    	return C;
    }

    double dotProduct(List A, List B){
    	A.moveFront();
    	B.moveFront();
    	double dotSum = 0;

    	while(A.index() >= 0 && B.index() >= 0){
    		Entry a = (Entry) A.get();
    		Entry b = (Entry) B.get();

    		if(a.column == b.column){
    			dotSum = dotSum + (a.data * b.data);
    			A.moveNext();
    			B.moveNext();
    		}
    		else if(a.column < b.column){
    			A.moveNext();
    		}
    		else{
    			B.moveNext();
    		}
    	}

    	return dotSum;
    }

    // returns a new Matrix that is the sum of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix add(Matrix M){
        Matrix returnMatrix = new Matrix(this.rowArray.length - 1);

        if(this == M) {
        	return scalarMult(2);
        }

        for(int i = 1; i < rowArray.length; i++) {
        	returnMatrix.rowArray[i] = addList(this.rowArray[i], M.rowArray[i]);
        }

        return returnMatrix;
    }

    // returns a new Matrix that is the difference of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix sub(Matrix M){
        Matrix returnMatrix = new Matrix(this.rowArray.length - 1);

        if(this == M) {
        	return scalarMult(0);
        }

        for(int i = 1; i < rowArray.length; i++) {
        	returnMatrix.rowArray[i] = subList(this.rowArray[i], M.rowArray[i]);
        }

        return returnMatrix;
    }

    // returns a new Matrix that is the transpose of this Matrix
    Matrix transpose(){
        Matrix returnMatrix = new Matrix(this.rowArray.length - 1);

        for(int i = 1; i < rowArray.length; i++)
        {
            this.rowArray[i].moveFront();
            while(rowArray[i].index() >= 0){
                Entry hi = new Entry(i, ((Entry)(this.rowArray[i].get())).data);
                returnMatrix.rowArray[((Entry)(this.rowArray[i].get())).column].append(hi);
                this.rowArray[i].moveNext();
            }
        }
        return returnMatrix;
    }

    // returns a new Matrix that is the product of this Matrix with M
    // pre: getSize()==M.getSize()
    Matrix mult(Matrix M){
        if(!(getSize()==M.getSize())){
            throw new RuntimeException("Matrix ADT / mult / pre: getSize()==M.getSize()");
        }

        Matrix returnMatrix = new Matrix(this.rowArray.length - 1);

        M = M.transpose();

        for(int i = 1; i < rowArray.length; i++){
        	for(int j = 1; j < rowArray.length; j++){
        		Entry hi = new Entry(j ,dotProduct(this.rowArray[i], M.rowArray[j]));
        		if(hi.data != 0){
        			returnMatrix.rowArray[i].append(hi);
        		}
        	}
        }

        return returnMatrix;
    }

    // Other functions --------------------------------------------------------

    // overrides Object's toString() method
    public String toString(){
    	String theString = "";
    	for(int i = 1; i < rowArray.length; i++){
    		this.rowArray[i].moveFront();
    		if(this.rowArray[i].length() > 0){
    			theString += i + ":";

	    		while(rowArray[i].index() >= 0){
	    			theString += " " + ((Entry)(this.rowArray[i].get())).toString();
	    			this.rowArray[i].moveNext();
	    		}
	    		theString += "\r\n";
    		}
    	}
    	return theString;
    }
}
