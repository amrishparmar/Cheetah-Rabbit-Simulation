package part1.collections;

import java.util.NoSuchElementException;

public class Queue<E> {
    private QueueItem<E> front; // pointer to the front item of the queue
    private QueueItem<E> back; // pointer to the back item of the queue

    /* constructor - creates and empty queue */
    public Queue() {
        front = null;
        back = null;
    }

    /* add a new element to the queue */
    public void enqueue(E item) {
        QueueItem<E> newItem = new QueueItem<>(item, null);

        // if the queue is empty set the front and back to the same item
        if (isEmpty()) {
            front = newItem;
            back = front;
        }
        // otherwise set the next property of the current back to the new item and then set current back to the new item
        else {
            back.setNext(newItem);
            back = back.getNext();
        }
    }

    /* removes the front element from the queue and returns it */
    public E dequeue() {
        // if the queue is not empty
        if (!isEmpty()) {
            // if there is only one element set the back pointer to null
            if (front == back) {
                back = null;
            }
            // store the old front so that we don't lose track of it
            QueueItem<E> oldFront = front;
            // set the new front to the next item in the queue
            front = front.getNext();
            // return the original front
            return oldFront.getItem();
        }
        else {
            throw new NoSuchElementException();
        }
    }

    /* look at front element of the queue without removing it */
    public E peek() {
        // if the queue is not empty
        if (!isEmpty()) {
            return front.getItem();
        }
        else {
            throw new NoSuchElementException();
        }
    }

    /* checks whether the queue contains any elements, returns true if no elements are in the queue, false otherwise */
    public boolean isEmpty() {
        return front == null && back == null;
    }
}
