package adt.queue;

public class Mani {
    public static void main(String[] args) {
        QueueImpl<Integer> queue = new QueueImpl<Integer>(4);
        try {
            queue.enqueue(1);
            queue.enqueue(2);
            queue.enqueue(3);
            System.out.println(queue.dequeue());
            System.out.println(queue.dequeue());
        } catch(QueueOverflowException e) {
            System.out.println("Ops");
        }
        catch (QueueUnderflowException e) {
            System.out.println("UNDERFLOW exception");
        }
    }
}
