package part2.collections;

public class LinkedList<E> {
    private ListItem<E> head; // the first element of the list
    private ListItem<E> tail; // the last element of the list

    /* constructor - creates an empty list */
    public LinkedList() {
        head = null;
    }

    /* add a new element to the front of the list */
    public void add(E item) {
        // if the list is empty set the head to a new ListItem
        if (isEmpty()) {
            head = new ListItem<>(item);
            tail = head;
        }
        else {
            head = new ListItem<>(item, head);
        }
    }

    public ListItem<E> getAtIndex(int index) {
        if (!isEmpty()) {
            int currentIndex = 0;
            ListItem<E> current = head;

            do {
                if (currentIndex == index) {
                    return current;
                }
                ++currentIndex;
            } while (current.getNext() != null);
        }
        return null;
    }

    /* print out the values of all items in the list */
    public void printAll() {
        ListItem<E> current = head;
        while (current != null) {
            System.out.println(current.getItem().toString());
            current = current.getNext();
        }
    }

    /* if the list is empty, return true or false otherwise */
    public boolean isEmpty() {
        return head == null;
    }

    /* getters and setters */

    public ListItem<E> getHead() {
        return head;
    }

    public ListItem<E> getTail() {
        return tail;
    }
}
