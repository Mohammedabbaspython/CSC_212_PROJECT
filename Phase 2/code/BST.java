public class BST<K extends Comparable<K>, T> implements IBST<K, T> {
    private BSTNode<K, T> root, current;
    private int count;

    public BST() {
        root = current = null;
        count = 0;
    }

    public boolean empty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    public boolean full() {
        return false;
    }

    public boolean findKey(K key) {
        BSTNode<K, T> p = root, q = root;

        if (empty())
            return false;

        while (p != null) {
            q = p;
            int res = key.compareTo(p.key);
            if (res == 0) {
                current = p;
                return true;
            } else if (res < 0) {
                p = p.left;
            } else {
                p = p.right;
            }

        }

        current = q;
        return false;
    }

    public T retrieve() {
        if (empty()) {
            return null;
        }
        return current.data;
    }

    public boolean insert(K key, T data) {
        BSTNode<K, T> p, q = current;

        if (findKey(key)) {
            current = q;
            return false;
        }

        p = new BSTNode<K, T>(key, data);
        if (empty()) {
            root = current = p;
            count++;
            return true;
        } else {
            if (key.compareTo(current.key) < 0) {
                current.left = p;
            } else {
                current.right = p;
            }
            current = p;
            count++;
            return true;
        }
    }

    public boolean removeKey(K key) {
        BSTNode<K, T> p = root, q = null;
        boolean found = false;
        while (p != null && !found) {
            int res = key.compareTo(p.key);
            if (res == 0)
                found = true;
            else {
                q = p;
                if (res < 0)
                    p = p.left;
                else
                    p = p.right;
            }
            if (found) {
                if (p.left != null && p.right != null) {
                    BSTNode<K, T> min = p.right;
                    q = p;
                    while (min.left != null) {
                        q = min;
                        min = min.left;
                    }
                    p.key = min.key;
                    p.data = min.data;
                    deleteNode(min, q);
                } else {
                    deleteNode(p, q);
                }
                current = root;
                count--;
                return true;

            }
        }
        return false;
    }

    private void deleteNode(BSTNode<K, T> p, BSTNode<K, T> q) {
        BSTNode<K, T> child;
        if (p.left != null) {
            child = p.left;
        } else {
            child = p.right;
        }
        if (q == null) {
            root = child;
        } else {
            if (p.key.compareTo(q.key) < 0) {
                q.left = child;
            } else {
                q.right = child;
            }
        }
    }

    public LinkedList<T> inOrder() {
        LinkedList<T> list = new LinkedList<T>();
        inOrderHelper(root, list);
        return list;
    }

    private void inOrderHelper(BSTNode<K, T> p, LinkedList<T> list) {
        if (p != null) {
            inOrderHelper(p.left, list);
            list.insert(p.data);
            inOrderHelper(p.right, list);
        }

    }

    public LinkedList<T> rangeSearch(K low, K high) {
        LinkedList<T> list = new LinkedList<T>();
        rangeSearchHelper(root, low, high, list);
        return list;
    }

    private void rangeSearchHelper(BSTNode<K, T> p, K low, K high, LinkedList<T> list) {
        if (p == null) {
            return;
        }
        int resLow = low.compareTo(p.key);
        int resHigh = high.compareTo(p.key);
        if (resLow < 0) {
            rangeSearchHelper(p.left, low, high, list);
        }
        if (resLow <= 0 && resHigh >= 0) {
            list.insert(p.data);
        }
        if (resHigh > 0) {
            rangeSearchHelper(p.right, low, high, list);
        }
    }

    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(BSTNode<K, T> p) {
        if (p == null) {
            return 0;
        }
        int leftHeight = heightHelper(p.left);
        int rightHeight = heightHelper(p.right);
        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        } else {
            return rightHeight + 1;
        }
    }

    public int nbKeyComp(K key) {
        return nbKeyCompHelper(root, key);
    }

    private int nbKeyCompHelper(BSTNode<K, T> p, K key) {
        if (p == null) {
            return 0;
        }
        int res = key.compareTo(p.key);
        if (res == 0) {
            return 1;
        } else if (res < 0) {
            return 1 + nbKeyCompHelper(p.left, key);
        } else {
            return 1 + nbKeyCompHelper(p.right, key);
        }
    }

}
