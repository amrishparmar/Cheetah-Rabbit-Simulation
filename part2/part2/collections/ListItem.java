package part2.collections;

public class ListItem<E> {
    private E item; // the data that we are storing
    private ListItem next; // the next element in the list

    /* constructor - creates a new element with a pointer to the next item in the list */
    public ListItem(E item, ListItem next) {
        this.item = item;
        this.next = next;
    }

    /* constructor - creates an element that points to null */
    public ListItem(E item) {
        this(item, null);
    }

    /* getters and setters */

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public ListItem getNext() {
        return next;
    }

    public void setNext(ListItem next) {
        this.next = next;
    }
}
