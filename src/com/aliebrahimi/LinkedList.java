package com.aliebrahimi;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class LinkedList<T> {

    private final Class<T> tClass;
    private static class Node<T> {

        private T value;
        private Node<T> next;


        private Node(T value) {
            this.value = value;
            this.next = null;


        }

        private Node(Node<T> node) {
            this.value = node.value;
            this.next = node.next;
        }


        private void setNext(Node<T> next){
            this.next = next;
        }
        private Node<T> getNext(){
            return this.next;
        }
        private String getHexIdentity(){
            return super.toString();
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;
    private final Set<Node<T>> storage = new HashSet<>();


    public LinkedList(Class<T> tClass){
        this.tClass = tClass;
    }


    public void info(){

        System.out.println(
                this
                        + "  Head : " + this.getHead()
                        + "\tH.next : " + this.head.next
                        + "\t| Tail : " + this.getTail()
                        + "\tTail.next : " + this.tail.next
                        + "\t-> Length : " + this.size()
                        + "\n"
        );


    }
    @SuppressWarnings("unchecked")
    public void of(T... values){

         for (T value : values){
             this.push(value);
         }


    }
    public void push(T value) {

        size++;
        Node<T> newNode = new Node<>(value);

        if (size == 1) {

            this.head = newNode;
            this.tail = this.head;
            this.storage.add(this.head);


        } else {

            this.tail.setNext(newNode);
            this.tail = newNode;

            this.storage.add(tail);


        }


    }
    private Node<T> getHead(){
        return this.head;

    }
    private Node<T> getTail(){
        return this.tail;

    }
    public int indexOf(T value) {

        Node<T> currentNode = this.head;

        for (int i = 0; i < this.storage.size(); i++) {

            if (currentNode.value == value) {
                return i;
            }

            currentNode = currentNode.getNext();

        }

        return -1;

    }
    public final T[] toArray(){

        Node<T> currentNode = this.head;

        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(this.tClass, this.size);

        for (int i = 0; i < this.storage.size(); i++) {

            array[i] = currentNode.value;
            currentNode = currentNode.getNext();

        }

        return array;

    }
    public void forEach(Consumer<T> e){

        for (int i = 0; i < toArray().length; i++) {
            e.accept(toArray()[i]);
        }
    }
    public void pop(){

        Node<T> currentNode = this.head;

        for (int i = 0; i < this.storage.size(); i++){

            if (currentNode.getNext() == this.tail){
                this.storage.remove(this.tail);
                currentNode.setNext(null);
                this.tail = currentNode;
                this.size--;
                return;

            } else if (this.head == this.tail) {
                this.storage.remove(head);
                this.head = null;
                this.tail = null;
                this.size--;
                return;
            }

            currentNode = currentNode.getNext();
        }




    }
    public int size(){
        return this.size;
    }
    public void unShift(T value){

        Node<T> newHead = new Node<>(value);
        newHead.setNext(this.head);
        this.storage.add(newHead);
        this.size++;
        this.head = newHead;

        if (size == 1){
            this.tail = this.head;
        }



    }
    public void shift(){

        if (this.size != 0){
            Node<T> newHead = this.head.getNext();
            this.storage.remove(this.head);
            this.head = newHead;

            if (this.size == 1){
                this.tail = this.head;
            }
            this.size--;

        }

    }
    public T getFirst(){
        if (size == 0){
            return null;
        }
        return this.head.value;

    }
    public T getLast(){
        if (size == 0){
            return null;
        }

        return this.tail.value;

    }
    public T getByIndex(int index){

        if (this.size == 0) {
            throw new IndexOutOfBoundsException("can not access by index from empty linked list");

        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                    "index " + index +
                    " is out of bounds for size : " + this.size
            );

        }

        Node<T> currentNode = this.head;

        for (int i = 0; i < index; i++){

            currentNode = currentNode.getNext();

        }

        return currentNode.value;


    }
    public void set(int index, T value){

        if (this.size == 0) {
            throw new IndexOutOfBoundsException("can not perform set() on empty linked list");

        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                    "index " + index +
                            " is out of bounds for size : " + this.size
            );
        }


        Node<T> currentNode = this.head;

        for (int i = 0; i < index; i++){

            currentNode = currentNode.getNext();

        }

        currentNode.value = value;


    }
    public void insert(int index, T value){


        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(
                    "index " + index
                            + " is out of bounds for size : " + this.size + " OR or for size : "
                            + (this.size + 1) + " As The New size hence pushing a new element"
            );
        }


        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = this.head;
        Node<T> targetNode = this.head;
        Node<T> targetPrevNode = targetNode;

        for (int i = 0; i < index; i++){

            currentNode = currentNode.next;

            if (i == index - 2) {
                targetPrevNode = currentNode;
            }
        }

        targetNode = currentNode;


        if (targetPrevNode != targetNode) {

            targetPrevNode.setNext(newNode);
            newNode.setNext(targetNode);

            if (this.size == index) {
                this.tail.setNext(newNode);
                this.tail = newNode;

            }

        } else {
                newNode.setNext(this.head);
                this.head = newNode;

            if (this.size == 0) {
                this.tail = this.head;

            }

        }


        this.storage.add(newNode);
        this.size++;
    }
    public int countElements(){

        if (this.head == null && this.tail == null){
            return 0;

        } else if (this.head == this.tail) {
            return 1;

        }


        Node<T> currentNode = this.head;
        int counter = 1;

        while (currentNode.next != null) {

            counter++;
            currentNode = currentNode.getNext();
        }

        return counter;

    }
    public void clear(){

       if (this.size == 0) return;

       Node<T> currentNode = this.head;

       while (currentNode != null) {
           this.storage.remove(currentNode);
           currentNode = currentNode.getNext();
       }

       this.size = 0;
       this.head = null;
       this.tail = null;


    }
    public void reverse(){

        Node<T> temp = this.head;
        this.head = this.tail;
        this.tail = temp;

        Node<T> a = this.tail;
        Node<T> b = a.next;
        Node<T> c = b.next;


        for (int i = 0; i < this.size - 1; i++){

            b.next = a;
            a = b;
            b = c;

            c = c.next != null ? c.next : c;
        }


        this.tail.next = null;

    }
    public T getByKThInd(int k){

        if (this.head == null) {
            throw new IndexOutOfBoundsException("can not perform getByKth() on empty liked list");
        }

        if (this.head.next == null) return this.head.value;

        if (k == 0) return this.tail.value;


        Node<T> kThNode = this.head;
        Node<T> tempNode;

        int a = 0;
        int b;

        for (int i = 1; i <= k; i++) {

            kThNode = kThNode.next;
            a++;
        }


        b = a;

        while ((b - a) != k) {

            kThNode = kThNode.next;
            a++;
            tempNode = kThNode;
            b = a;

            if (tempNode == null) {
                throw new IndexOutOfBoundsException("invalid input");

            }

            while (tempNode.next != null) {
                tempNode = tempNode.next;
                b++;
            }
        }


        return kThNode.value;


    }
    public T kTh(int k){

        if (this.head == null) {
            throw new IndexOutOfBoundsException("can not perform kTh() on empty liked list");
        }

        if (k == 0 || k < 0) {
            throw new IllegalArgumentException("Invalid Input");
        }

        Node<T> a = this.head;
        Node<T> b = this.head;

        for (int i = 0; i < k - 1; i++){

            b = b.next;

            if (b == null) {
                throw new IllegalArgumentException("Invalid Input");
            }
        }


        while (b != this.tail) {

            a = a.next;
            b = b.next;


        }

        return a.value;


    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");


        Node<T> current = this.head;

        for (int i = 0; i < this.size; i++){

            stringBuilder.append(current.value);
            if (current.next != null) stringBuilder.append(", ");
            current = current.next != null ? current.next : current;

        }

        stringBuilder.append("]");

        return stringBuilder.toString();


    }

}
