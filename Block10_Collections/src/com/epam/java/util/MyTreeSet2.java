package com.epam.java.util;

import java.util.*;

/**
 * Created by  Pidhurska Tetiana on 07.06.2016.
 */
public class MyTreeSet2<T extends Comparable<T>> implements  Set<T>, Iterable<T> {
    private Node<T> root = new Node<>(null);
    private int size = 1;



  //  Comparable<T>,
//  @Override
//    public int compareTo(T o) {
//        return 0;
//    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

     @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        Node<T> current = root;
        Node<T> newElem = new Node<>(t);
        while (true) {
            if (root == null) {
                return false;
            } else {
                int cmp = t.compareTo(current.value);
                if (cmp == 0) {
                    return true;
                } else if (cmp < 0) {
                    if (current.left != null) {
                        current = current.left;
                    } else {
                        current.left = newElem;
                        current.left.parent = current;
                        size++;
                        break;
                    }
                } else {
                    if (current.right != null) {
                        current = current.right;
                    } else {
                        current.right = newElem;
                        current.right.parent = current;
                        size++;
                        break;
                    }
                }

            }
        }
        return true;
    }



    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    class Node<T> {
        private Node<T> left;
        private Node<T> right;
         private Node<T> parent;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

//        public Node<T> getParent() {
//            return parent;
//        }
//
//        public void setParent(Node<T> parent) {
//            this.parent = parent;
//        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

//        @Override
//        public String toString() {
//            String sleft, sright;
//            if (left == null && right == null) {
//                return value.toString();
//            }
//            if (left != null) {
//                sleft = left.toString();
//            } else {
//                sleft = "";
//            }
//            if (right != null) {
//                sright = right.toString();
//            } else {
//                sright = "";
//            }
//
//            return value.toString() + "[" +
//                    sleft + "," + sright + "]";
//
//        }

        public String recurrentToString() {
            String sleft, sright;
            if (left != null) {
                sleft = left.recurrentToString();
            } else {
                sleft = "";
            }
            if (left == null && right == null) {
                return value.toString();
            }
            if (right != null) {
                sright = right.recurrentToString();
            } else {
                sright = "";
            }
            return "[" +
                    sleft + "," + value.toString() + "," + sright + "]";
        }

        @Override
        public String toString() {
          return this.value.toString();

        }

        public List<Node> toList() {
            List<Node> tempList = new LinkedList<>();
            if (left == null && right == null) {
                tempList.add(this);
                return tempList;
            }
//            List<Node<T>> leftList, rightList;
            if (left != null) {
                tempList.addAll(left.toList());
            }
            tempList.add(this);
            if (right != null) {
                tempList.addAll(right.toList());
            }
            return tempList;

        }
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Iterator iterator() {
//        Iterator<Node> itr=  getRoot().toList().iterator();
//        return  itr;
        return   new TreeIterator();

    }

    public boolean remove(Object o) {
       Node<T> current = root;
        if (root.value.compareTo((T) o) ==0 && root.left==null && root.right==null) {
            System.out.println("Trying delete root. Cannnot delete root "+root.value);
            return false;
        }
        while (current != null) {
            int resOfCompare = current.value.compareTo((T) o);
            if (resOfCompare > 0) {
                current = current.left;
            } else if (resOfCompare < 0) {
                current = current.right;
            } else {
                if (current.left == null && current.right == null) {
                    if (current.parent.left == current) {
                        current.parent.left = null;
                    } else current.parent.right = null;
                } else if (current.left != null) {
                    Node<T> maxLeft = current.left;
                    while (maxLeft.right != null) {
                        maxLeft = maxLeft.right;
                    }
                    current.value = maxLeft.value;
                    if (maxLeft.parent.right == maxLeft) {
                        if (maxLeft.left != null) {
                            maxLeft.parent.right = maxLeft.left;
                        } else {
                            maxLeft.parent.right = null;
                        }
                    } else {
                        maxLeft.parent.left = maxLeft.left;
                    }
                } else {
                    Node<T> maxRight = current.right;
                    while (maxRight.left != null) {
                        maxRight = maxRight.left;
                    }
                    current.value = maxRight.value;
                    if (maxRight.parent.left == maxRight) {
                        if (maxRight.right != null) {
                            maxRight.parent.left = maxRight.right;
                        } else {
                            maxRight.parent.left = null;
                        }
                    } else {
                        maxRight.parent.right = maxRight.right;
                    }
                }
                return true;
            }
        }
        return false;
    }
    private class TreeIterator implements Iterator {
        Node<T> returned = null;
        //list = getRoot().toList();
        private Iterator<Node> listitr =  getRoot().toList().iterator();

        @Override
        public boolean hasNext() {
            return listitr.hasNext();
        }

        @Override
        public Node next() {
            returned = listitr.next();
            return returned;
        }

        @Override
        public void remove() {
            MyTreeSet2.this.remove(returned.getValue());
            //listitr =  getRoot().toList().iterator();
        }
    }
    @Override
    public String toString() {
        return    getRoot().recurrentToString();
    }
}
