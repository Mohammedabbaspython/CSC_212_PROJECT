public class NonUniqueIndexHelper<KOuter extends Comparable<KOuter>, KInner extends Comparable<KInner>, T> {
    private IBST<KOuter, IBST<KInner, T>> indexIBST;

    public NonUniqueIndexHelper(IBST<KOuter, IBST<KInner, T>> indexIBST) {
        this.indexIBST = indexIBST;
    }

    public boolean add(KOuter ko, KInner ki, T data) {
        // navigate to key node and add key if does not exist and initialize inner bst
        if (!indexIBST.findKey(ko)) {
            indexIBST.insert(ko, new BST<KInner, T>());
        }

        // insert into inner bst
        return indexIBST.retrieve().insert(ki, data);
    }

    public boolean remove(KOuter ko, KInner ki) {
        if (!indexIBST.findKey(ko)) {
            return false;
        }

        IBST<KInner, T> inner = indexIBST.retrieve();

        if (!inner.removeKey(ki)) {
            return false;
        }

        // remove key of inner bst if it is empty
        if (inner.empty()) {
            indexIBST.removeKey(ko);
        }
        
        return true;

    }

    // returns null if key doesn't exit
    public IBST<KInner, T> get(KOuter ko) {
        if (!indexIBST.findKey(ko)) {
            return null;
        }

        return indexIBST.retrieve();
    }
}
