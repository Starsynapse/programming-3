//-----------------------------------------------------------------------------
// List.java
// A doubly linked list whose nodes contain integer values.
// Written by: Eduardo Zamora
// Email: ezamora9@ucsc.edu
// Programming Assignment 3
//-----------------------------------------------------------------------------

class List {
    private class Node{
        // Fields
        int data;
        Node next;
        Node previous;

        // Constructors
        Node(int data) {this.data = data; next = null; previous = null;}
    }

    // Fields
    private Node front;
    private Node back;
    private int length;
    private Node cursor;
    private int index;

    // Constructor
    List() {
       front = back = null;
       length = 0;
    }

    // Access functions ---------------------------------

    // Returns the number of elements in this List.
    int length(){
            return length;
    }

    // If cursor is defined, returns the index of the cursor element, otherwise returns -1
    int index(){
        if(cursor != null){
            return index;
        }
        else{
            return -1;
        }
    }

    // Returns front element. Pre: length()>0
    int front(){
        if(length <= 0){
            throw new RuntimeException("List ADT / int front() / length() > 0");
        }

        return front.data;
    }

    // Returns back element. Pre: length()>0
    int back(){
        if(length <= 0){
            throw new RuntimeException("List ADT / int back() / length() > 0");
        }

        return back.data;
    }

    // Returns cursor element. Pre: length()>0, index()>=0
    int get(){
        if(length <= 0 && index < 0){
            throw new RuntimeException("List ADT / int get() / length() > 0 && index() >= 0");
        }

        if(length <= 0){
            throw new RuntimeException("List ADT / int get() / length() > 0");
        }

        if(index < 0){
            throw new RuntimeException("List ADT / int get() / index() >= 0");
        }

        return cursor.data;
    }

    // Returns true if and only if this List and L are the same integer sequence.
    // The states of the cursors in the two Lists are not used in determining equality.
    boolean equals(List L){
        Node temp1 = L.front;
        Node temp2 = front;

        if(L.length != length){
            return false;
        }

        while(temp1 != null || temp2 != null){

            if(temp1.data != temp2.data){
                return false;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }

        return true;
    }

    // Manipulation procedures --------------------------

    // Resets this list to its original empty state
    void clear(){
        back = front = cursor = null;
        length = 0;
    }

    // If List is non-empty, places the cursor under the front element, otherwise does nothing
    void moveFront(){

        if(length > 0){
            this.cursor = front;
            index = 0;
        }
    }

    // If List is non-empty, places the cursor under the back element, otherwise does nothing
    void moveBack(){

        if(length > 0){
            cursor = back;
            index = length - 1;
        }
    }

    // If cursor is defined and not at front, moves cursor one step toward
    // front of this List, if cursor is defined and at front, cursor becomes
    // undefined, if cursor is undefined does nothing
    void movePrev(){

        if(cursor != front && cursor != null){
            this.cursor = cursor.previous;
            index = index - 1;
        }

        else if(cursor == front){
            this.cursor = null;
        }
    }

    // If cursor is defined and not at back, moves cursor one step toward
    // back of this List, if cursor is defined and at back, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void moveNext(){

        if(cursor != back && cursor != null){
            this.cursor = cursor.next;
            index = index + 1;
        }

        else if(cursor == back && cursor != null){
            this.cursor = null;
        }
    }

    // Insert new element into this List. If List is non-empty, insertion takes place before front element
    void prepend(int data){
        Node N = new Node(data);

        if (length == 0){
            front = back = N;
        }

        else{
            front.previous = N;
            N.next = front;
            front = N;
            if (cursor != null){
                index = index + 1;
            }
        }
        length++;
    }

    // Insert new element into this List. If List is non-empty, insertion takes place after back element
    void append(int data) {
        Node N = new Node(data);

        if (length == 0){
            front = back = N;
        }

        else{
            back.next = N;
            N.previous = back;
            back = N;
        }
        length++;
    }

    // Insert new element before cursor.
    // Pre: length()>0, index()>=0
    void insertBefore(int data){
        if(length <= 0 && index < 0){
            throw new RuntimeException("List ADT / void insertBefore(int data) / length() > 0 && index() >= 0");
        }

        if(length <= 0){
            throw new RuntimeException("List ADT / void insertBefore(int data) / length() > 0");
        }

        if(index < 0){
            throw new RuntimeException("List ADT / void insertBefore(int data) / index() >= 0");
        }

        Node N = new Node(data);

        if(cursor != front){
            Node temp = cursor.previous;
            temp.next = N;
            cursor.previous = N;
            N.previous = temp;
            N.next = cursor;
        }

        else if(cursor == front){
            front = N;
            N.next = cursor;
            cursor.previous = N;
        }

        index = index + 1;
        length++;
    }

    // Inserts new element after cursor.
    // Pre: length()>0, index()>=0
    void insertAfter(int data){
        if(length <= 0 && index < 0){
            throw new RuntimeException("List ADT / void insertAfter(int data) / length() > 0 && index() >= 0");
        }

        if(length <= 0){
            throw new RuntimeException("List ADT / void insertAfter(int data) / length() > 0");
        }

        if(index < 0){
            throw new RuntimeException("List ADT / void insertAfter(int data) / index() >= 0");
        }

        Node N = new Node(data);

        if(cursor != back){
            Node temp = cursor.next;
            temp.previous = N;
            cursor.next = N;
            N.previous = cursor;
            N.next = temp;
        }

        else if(cursor == back){
            back = N;
            N.previous = cursor;
            cursor.next = N;
        }

        length++;
    }

    // Deletes the front element. Pre: length()>0
    void deleteFront(){
        if(length <= 0){
            throw new RuntimeException("List ADT / void deleteFront() / length() > 0");
        }

        if(front.next != null){
            front = front.next;
            if(cursor == front.previous){
                cursor = null;
            }
            front.previous = null;
            length = length - 1;

            if(cursor != null){
                index = index - 1;
            }
        }

        else{
            front = back = cursor = null;
            length = length - 1;
        }
    }

    // Deletes the back element. Pre: length()>0
    void deleteBack(){
        if(length <= 0){
            throw new RuntimeException("List ADT / void deleteBack() / length() > 0");
        }

        if(back.previous != null){
            back = back.previous;

            if(cursor == back.next){
                cursor = null;
            }
            back.next = null;
            length = length - 1;
        }

        else{
            back = front = cursor = null;
            length = length - 1;
        }
    }

    // Deletes cursor element, making cursor undefined.
    // Pre: length()>0, index()>=0
    void delete(){

        if(length <= 0 && index < 0){
            throw new RuntimeException("List ADT / void delete() / length() > 0 && index() >= 0");
        }

        if(length <= 0){
            throw new RuntimeException("List ADT / void delete() / length() > 0");
        }

        if(index < 0){
            throw new RuntimeException("List ADT / void delete() / index() >= 0");
        }

        if(cursor == front){
            if(front.next != null){
                front = front.next;
                front.previous = null;
            }
            else{
                front = null;
            }
        }

        else if(cursor == back){
            if(back.previous != null){
                back = back.previous;
                back.next = null;
            }
            else{
                back = null;
            }
        }

        else{
            cursor.previous.next = cursor.next;
            cursor.next.previous = cursor.previous;
        }

        cursor = null;
        length = length - 1;

    }

    // Other methods

    // Overrides Object's toString method. Returns a String
    // representation of this List consisting of a space
    // separated sequence of integers, with front on left.
    public String toString(){
        String string1 = "";
        Node temp = front;

        while(temp != null){
            string1 = string1.concat(Integer.toString(temp.data) + " ");
            temp = temp.next;
            }

        return string1;
    }

    // Returns a new List representing the same integer sequence as this
    // List. The cursor in the new list is undefined, regardless of the
    // state of the cursor in this List. This List is unchanged
    List copy(){
        List L = new List();

        Node temp1 = front;

        while(temp1 != null){
            L.append(temp1.data);
            temp1 = temp1.next;
        }

        return L;
    }
}
