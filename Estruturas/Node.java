package Estruturas;

/**
 * Representa um Nó.
 */
public class Node {
    public Object data;
    public Node address;
    public Node last_address;
    public int priority;
    public int counter;
    public Node left_child;
    public Node right_child;
    public Node main_source;

    /**
     * Representa um Nó.
     * @param data (Object) - O valor do elemento.
     */
    public Node(Object data) {
        this.data = data;
        this.address = null;
        this.counter = 1;
    }

    /**
     * Representa um Nó com pai.
     * @param data (Object) - O valor do elemento.
     * @param main_source (Node) - O pai do elemento.
     */
    public Node(Object data, Node main_source) {
        this.data = data;
        this.address = null;
        this.main_source = main_source;
    }

    /**
     * Representa um Nó com prioridade.
     * @param data (Object) - O valor do elemento.
     * @param priority (Integer) - O nível de prioridade do elemento.
     */
    public Node(Object data, int priority) {
        this.data = data;
        this.priority = priority;
        this.address = null;
        this.last_address = null;
    }

    /**
     * Representa uma alocação encadeada para Árvores Binárias.
     * @param data (Object) - O valor do elemento.
     * @param left_child (Node) - Nó da árvore pai.
     * @param right_child (Node) - Nó da árvore pai.
     */
    public Node(Object data, Node left_child, Node right_child) {
        this.data = data;
        this.left_child = null;
        this.right_child = null;
    }
}
