public class LinkedList<T> {
    Node<T> head ;
    Node<T> current ;
    public LinkedList(){
        head = null;
        current = null ;
    }
    public boolean empty(){
        return head == null ;
    }
    public boolean full(){
        return false ;
    }
    public boolean last(){
        return current.next == null ;
    }
    public void findfirst(){
        current = head ;
    }
    public void findnext(){
        current = current.next ;
    }
    public T retrieve(){
        return current.data ;
    }
    public void update(T data){
        current.data = data;
    }
    public void insert(T data){
        Node<T> tmp ;
        if(empty()){
            current = head = new Node<T>(data) ;
        }
        else{
            tmp = current.next ;
            current.next = new Node<T>(data) ;
            current = current.next ;
            current.next = tmp ;
        }
    }

    public void remove(){
        if(current == head){
            head = head.next ;
        }
        else{
            Node<T> tmp = head ;
            while (tmp.next != current){
                tmp = tmp.next ;
            }
            tmp.next = current.next ;
        }
        if(current.next == null){
            current = head ;
        }
        else{
            current = current.next ;
        }
    }
    
}
