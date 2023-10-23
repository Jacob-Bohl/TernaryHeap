class TernaryHeap {
    private int[] storage;
    private int size;
 
    public TernaryHeap() {
        storage = new int[5];
        size = 0;
    }
 
    public void output() {
        for (int i = 0; i < size; i++) {
            System.out.print(storage[i] + " ");
        }
        System.out.println();
    }
 
    /* ------------ Modify/add methods below this line ------------ */
    private int left(int i) {
        return 3*i + 1;
    }

    private int middle(int i) {
        return 3*i + 2;
    }

    private int right(int i) {
        return 3*i + 3;
    }

    private int parent(int i) {
        if(i == 1 || i == 2){
            return 0;
        }
        if((i%3) == 0){
            return ((i-3)/3);
        }
        return((i-(i%3))/3);
    }
    

    public void add(int k) {
        int blankSpace = getBlank();
        storage[blankSpace] = k;
        size++;
        heapifyUp(blankSpace);
    }

    private void heapifyUp(int i) {
        if (i == 0) {return;}
        if (storage[i] > storage[parent(i)]) {
          int tmp = storage[i];
          storage[i] = storage[parent(i)];
          storage[parent(i)] = tmp;
          heapifyUp(parent(i));
        }
    }

    private int getBlank() {
        if (size >= storage.length) {
          makeSpace();
        }
        return size;
    }

    private void makeSpace() {
        int[] newX = new int[storage.length+1];
        for (int i=0; i < storage.length; i++) {
          newX[i] = storage[i];
        }
        storage = newX;
    }
 
    public int peekMax() {
        return storage[0];
    }
 
    public int popMax() {
        int ret = storage[0];
        storage[0] = storage[size-1];
        size--;
        heapifyDown(0);
        return ret;
    }

    private void heapifyDown(int i) {
        int maxChild = getMaxChild(i); 
        if (maxChild == -1) {return;}
        if (storage[i] < storage[maxChild]) {
          promote(maxChild);
          heapifyDown(maxChild);
        }
    }

    private int getMaxChild(int i) {
        if (left(i) >= size) {
            // no children
          return -1;
        }
        if (middle(i) >= size) {
            // only a left
            return left(i);
        }
        if (right(i) >= size){
            // only a left and middle child
            if (storage[middle(i)] > storage[left(i)]) {
                return middle(i);
            }
            return left(i);
        }

        // all three children exist
        if (storage[left(i)] > storage[middle(i)] && storage[left(i)] > storage[right(i)]) {
          return left(i);
        }
        if (storage[middle(i)] > storage[left(i)] && storage[middle(i)] > storage[right(i)]) {
            return middle(i);
        }
        return right(i);
    }

    private void promote(int i) {
        int tmp = storage[i];
        storage[i] = storage[parent(i)];
        storage[parent(i)] = tmp;
    }
 
    public void buildHeap(int[] Y) {
        //finding the height of the tree
        storage = Y;
        size = Y.length;
        for(int i = storage.length-1; i >= 0; i--){
            heapifyDown(i);
        }
    }
 
    public int nodesInLevel(int h) {
        if(size == 0){
            return 0;
        }
        int nodeCounter = 0;
        for(int i = 0; i <= h;i++){
            nodeCounter = (int) (nodeCounter + Math.pow(3, i));
        }

        if(nodeCounter < size){
            return (int) Math.pow(3, h);
        }
        int nodesleft = 0;
        for(int i = 0; i < h;i++){
            nodesleft = (int) (nodesleft + Math.pow(3, i));
        }
        if(size - nodesleft <= 0){
            return 0;
        }
        return size - nodesleft;
    }
    /* ----------------- No mods below this line ------------------ */
 
    public static void main(String[] args) {
        java.util.Scanner myScanner = new java.util.Scanner(System.in);
        TernaryHeap myHeap = new TernaryHeap();
        boolean done = false;
        System.out.println("Type heap commands:");
        while (!done) {
            String[] tokens = myScanner.nextLine().split(" ");
            String operation = "";
            int[] operands = null;
            if (tokens.length > 0) {
                operation = tokens[0];
                operands = new int[tokens.length - 1];
                for (int i = 0; i < operands.length; i++) {
                    operands[i] = Integer.parseInt(tokens[1 + i]);
                }
            }
 
            if (operation.equals("add")) {
                myHeap.add(operands[0]);
            } else if (operation.equals("peekMax")) {
                System.out.println(myHeap.peekMax());
            } else if (operation.equals("popMax")) {
                System.out.println(myHeap.popMax());
            } else if (operation.equals("output")) {
                myHeap.output();
            } else if (operation.equals("buildHeap")) {
                myHeap.buildHeap(operands);
            } else if (operation.equals("nodesInLevel")) {
                System.out.println(myHeap.nodesInLevel(operands[0]));
            } else if (operation.equals("quit")) {
                done = true;
            }
        }
    }
}
 