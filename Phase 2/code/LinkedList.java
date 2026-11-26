public class LinkedList<T> {
    Node<T> head, tail;
    Node<T> current;

    public LinkedList() {
        tail = head = null;
        current = null;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean full() {
        return false;
    }

    public boolean last() {
        if (empty()) {
            return false;
        }
        return current.next == null;
    }

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        if (empty() || last()) {
            return;
        }
        current = current.next;
    }

    public T retrieve() {
        if (empty()) {
            return  null;
        }
        return current.data;
    }

    public void update(T data) {
        if (empty()) {
            return;
        }
        current.data = data;
    }

    public void insert(T data) {
        Node<T> tmp;
        if (empty()) {
            current = head = tail = new Node<T>(data);
        } else {
            tmp = current.next;
            current.next = new Node<T>(data);

            if (current == tail) {
                tail = current.next;
            }

            current = current.next;
            current.next = tmp;
        }
    }

    public void remove() {
        if (empty()) {
            return;
        }
        if (current == head) {
            head = head.next;
            if (current == tail) {
                tail = null;
            }
        } else {
            Node<T> tmp = head;
            while (tmp.next != current) {
                tmp = tmp.next;
            }
            tmp.next = current.next;
            if (current == tail) {
                tail = tmp;
            }
        }
        if (current.next == null) {
            current = head;
        } else {
            current = current.next;
        }
    }

    public int size() {
        int count = 0;
        Node<T> tmp = head;
        while (tmp != null) {
            count++;
            tmp = tmp.next;
        }
        return count;
    }

}
