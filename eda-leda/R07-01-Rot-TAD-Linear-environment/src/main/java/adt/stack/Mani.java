package adt.stack;

public class Mani {
    public static void main(String[] args) {
        StackImpl<Integer> stack = new StackImpl<Integer>(3);
        try {
            stack.push(new Integer(1));
            stack.push(new Integer(2));
            stack.push(new Integer(3));
            System.out.println(stack.isFull());
        }

        catch(StackOverflowException e) {
            System.out.println("Erro overflow");
        }
    }
}
