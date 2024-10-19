package lab.eight;

import java.util.NoSuchElementException;

public class BST<T> {
    private class Node implements Comparable<Node> {
        T value;
        Node left, right, parent;

        public Node(T v) {
            value = v;
        }

        public Node(T value, Node left, Node right, Node parent) {
            super();
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(Node o) {
            Link ref1 = (Link) this.getValue();
            Link ref2 = (Link) o.getValue();
            return ref1.compareTo(ref2);
        }
    }

    private Node root;
    private int size = 0;

    public BST() {
        root = null;
    }

    public T getElement(T toFind) {
        Node node = search(toFind, root);
        return node == null ? null : node.value;
    }

    public Node search(T toFind, Node node) {
        if (node == null || ((Link) toFind).compareTo((Link) node.value) == 0) {
            return node;
        }
        if (((Link) toFind).compareTo((Link) node.value) > 0) {
            return search(toFind, node.right);
        } else {
            return search(toFind, node.left);
        }
    }


    public T getMin() {
        if (root == null) throw new NoSuchElementException();
        Node node = getMin(root);
        return node.value;
    }

    public T getMax() {
        if (root == null) throw new NoSuchElementException();
        Node node = getMax(root);
        return node.value;
    }

    private Node getMin(Node node) {
        assert (node != null);
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node getMax(Node node) {
        assert (node != null);
        while (node.right != null)
            node = node.right;
        return node;
    }

    public Node successorNode(Node node, T elem) {
        if (node == null) {
            throw new NoSuchElementException();
        }
        if (((Link) elem).compareTo((Link) node.value) == 0) {
            if (node.right != null) {
                return getMin(node.right);
            } else {
                return null;
            }
        } else if (((Link) elem).compareTo((Link) node.value) < 0) {
            Node retNode = successorNode(node.left, elem);
            return retNode == null ? node : retNode;
        } else {
            return successorNode(node.right, elem);
        }
    }

    public T successor(T elem) {
        Node succNode = successorNode(root, elem);
        return succNode == null ? null : succNode.value;
    }

    public String in = "";

    public String inOrderWalk(Node node) {

        if (node != null) {
            inOrderWalk(node.left);
            in = in + ((Link) node.value).toString() + ", ";
            inOrderWalk(node.right);
        }
        return in;
    }

    public String toStringInOrder() {
        in = "";
        String output = inOrderWalk(root);
        if (output.endsWith(", ")) {
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }

    public String pre = "";

    public String preOrderWalk(Node node) {
        if (node != null) {
            pre = pre + ((Link) node.value).toString() + ", ";
            preOrderWalk(node.left);
            preOrderWalk(node.right);
        }
        return pre;
    }

    public String toStringPreOrder() {
        pre = "";
        String output = preOrderWalk(root);
        if (output.endsWith(", ")) {
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }

    public String post = "";

    public String postOrderWalk(Node node) {
        if (node != null) {
            postOrderWalk(node.left);
            postOrderWalk(node.right);
            post = post + ((Link) node.value).toString() + ", ";
        }
        return post;
    }

    public String toStringPostOrder() {
        post = "";
        String output = postOrderWalk(root);
        if (output.endsWith(", ")) {
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }


    public boolean add(T elem) {
        if (getElement(elem) != null)
            return false;
        if (this.root == null) {
            this.root = new Node(elem);
            size++;
            return true;
        }
        Node toAdd = new Node(elem);
        Node newParent = this.root;
        int cmp;
        while (newParent.left != null || newParent.right != null) {
            cmp = ((Comparable<T>) toAdd.value).compareTo(newParent.value);
            if (cmp < 0) {
                if (newParent.left != null)
                    newParent = newParent.left;
                else
                    break;
            }
            if (cmp > 0) {
                if (newParent.right != null)
                    newParent = newParent.right;
                else
                    break;
            }
        }
        toAdd.parent = newParent;
        cmp = ((Comparable<T>) toAdd.value).compareTo(newParent.value);
        if(cmp<0)
            newParent.left=toAdd;
        else
            newParent.right=toAdd;

        size++;
        return true;
    }


    public Node getNode(T value){
        if(value == null){
            return null;
        }
        Node toFind = this.root;
        while(toFind !=null){
            int cmp =((Comparable<T>) toFind.value).compareTo((T)value);
            if(cmp ==0)
                return toFind;
            else if( cmp>0)
                toFind = toFind.left;
            else
                toFind = toFind.right;
        }
        return null;
    }
    public T remove(T value) {
       Node toRemove = getNode(value);
       if(toRemove == null)
           return null;
       if(toRemove.left==null){
           replace(toRemove,toRemove.right);
       }
       else if(toRemove.right == null){
           replace(toRemove, toRemove.left);
       }
       else{
           Node succesor = getNode(successor(toRemove.value));
           if(succesor.parent != toRemove){
               replace(succesor,succesor.right);
               succesor.right=toRemove.right;
               succesor.right.parent = succesor;
           }
           replace(toRemove, succesor);
           succesor.left = toRemove.left;
           succesor.left.parent=succesor;
       }
       size--;
       return toRemove.value;
    }

    private void replace(Node first, Node second){
        if(first.parent == null)
            this.root=second;
        else if (first == first.parent.right)
            first.parent.right=second;
        else
            first.parent.left = second;
        if(second!=null)
            second.parent=first.parent;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public int size() {
        return size;
    }

    public int getLeaves(){
        return getLeaves(root);
    }

    private int getLeaves(Node node){
        if(node ==null)
            return 0;
        if(node.left == null && node.right == null)
            return 1;
        else
            return getLeaves(node.left) + getLeaves(node.right);
    }

}