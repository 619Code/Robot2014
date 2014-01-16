package org.carobotics.utils;

/**
 *
 * @author carobotics
 */
public class LinkedList {
    
    private Node first;
    private Node last;
    
    private int size;
    
    public LinkedList() {
        first = null;
        size = 0;
    }
    
    public int size() {
        return size;
    }
    
    public Node getFirst() {
        return first;
    }
    
    public Node getLast() {
        return last;
    }
    
    public void addFirst(Object obj) {
        if(first == null) {
            first = new Node(obj, null, null);
            last = first;
            size = 1;
            return;
        }
        Node f = first;
        first = new Node(obj, null, f);
        f.previous = first;
        size++;
    }
    
    public void addLast(Object obj) {
        if(first == null) {
            first = new Node(obj, null, null);
            last = first;
            size = 1;
            return;
        }
        Node l = last;
        last = new Node(obj, l, null);
        l.next = last;
        size++;
    }
    
    public void empty() {
        first = null;
        last = null;
        size = 0;
    }
    
    public void clear() {
        empty();
    }
    
    public void remove(Object obj) {
        removeNode(findNode(obj));
    }
    
    private Node findNode(Object obj) {
        if(first == null) {
            return null;
        }
        Node n = first;
        do {
            if(obj.equals(n.obj)) {
                return n;
            }
        } while((n = first.next) != null);
        return null;
    }
    
    private void removeNode(Node n) {
        if(n == null) {
            return;
        }
        size--;
        if(size == 0) {
            empty();
            return;
        }
        if(n.next != null) {
            n.next.previous = n.previous;
        } else {
            last = n.previous;
        }
        if(n.previous != null) {
            n.previous.next = n.next;
        } else {
            first = n.next;
        }
    }
    
    public class Node {
        public Object obj;
        
        public Node next;
        public Node previous;
        
        public Node(Object obj, Node next, Node previous) {
            this.obj = obj;
            this.next = next;
            this.previous = previous;
        }
    }
    
}
