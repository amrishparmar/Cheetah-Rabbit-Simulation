package part1.collections;

public class QueueItem<E> {
    private E item; // the actual data we want to store
    private QueueItem<E> next; // a pointer to the next element in the queue

    /* constructor */
    public QueueItem(E item, QueueItem<E> next) {
        this.item = item;
        this.next = next;
    }

    /* getters and setters */

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public QueueItem<E> getNext() {
        return next;
    }

    public void setNext(QueueItem<E> next) {
        this.next = next;
    }
}
