package Estruturas;

public class ArvoreBinariaDeBusca {
    public Node source;
    public String code = "";
    public String tree_code = "";
    public String reverse_code = "";

    /**
     * Inicializa a raiz da árvore.
     */
    public ArvoreBinariaDeBusca() {
        source = null;
    }

    /**
     * Adiciona um elemento à árvore.
     * @param element (Object) - Elemento a ser adicionado.
     */
    public void add(Object element) {
        if (this.empty()) source = new Node(element);
        else this.add(element, source);
    }

    /**
     * Adiciona um elemento à raiz.
     * @param element (Object) - Elemento a ser adicionado.
     * @param src (Node) - Raiz onde o elemento vai ser adicionado.
     */
    private void add(Object element, Node src) {
        if (element.toString().compareTo(src.data.toString()) < 0) { // Lado Esquerdo.
            if (src.left_child == null) {
                src.left_child = new Node(element, src);
            } else {
                this.add(element, src.left_child);
            }
        } else if (element.toString().compareTo(src.data.toString()) > 0) { // Lado Direito.
            if (src.right_child == null) {
                src.right_child = new Node(element, src);
            } else {
                this.add(element, src.right_child);
            }
        }
    }

    /**
     * Remove um elemento da árvore.
     * @param element (Object) - Elemento a ser removido.
     */
    public void remove(Object element) {
        if (!this.empty()) {
            remove(element, source);
        }
    }

    /**
     * Remove um elemento a partir da raiz.
     * @param element (Object) - Elemento a ser removido.
     * @param src (Node) - Raiz de onde vai ser removido.
     */
    private void remove(Object element, Node src) {
        if (element == src.data) {
            if (src.left_child == null && src.right_child == null) { // Não tem filho.
                if (src == src.main_source.left_child) { // Filho Esquerdo
                    src.main_source.left_child = null;
                } else { // Filho Direito
                    src.main_source.right_child = null;
                }
            } else if (src.left_child != null && src.right_child == null) { // Só Filho Esquerdo.
                if (src == src.main_source.left_child) { // Filho Esquerdo
                    src.main_source.left_child = src.left_child;
                } else { // Filho Direito
                    src.main_source.right_child = src.left_child;
                }
            } else if (src.left_child == null && src.right_child != null) { // Só Filho Direito
                if (src == src.main_source.left_child) { // Filho Esquerdo
                    src.main_source.left_child = src.right_child;
                } else { // Filho Direito
                    src.main_source.right_child = src.right_child;
                }
            } else if (src.left_child != null && src.right_child != null) { // Dois Filhos
                src.data = this.min(src.right_child);
                this.remove(src.data, src.right_child);
            }
        } else if (element.toString().compareTo(src.data.toString()) < 0) { // Lado Esquerdo.
            if (src.left_child != null) {
                this.remove(element, src.left_child);
            }
        } else if (element.toString().compareTo(src.data.toString()) > 0) { // Lado Direito.
            if (src.right_child != null) {
                this.remove(element, src.right_child);
            }
        }
    }

    /**
     * Percorre a árvore e retorna o menor elemento da raiz.
     * @param src (Node) - Raiz a ser percorrida.
     * @return (Object) - O valor do elemento.
     */

    public Object min(Node src) {
        if (src.left_child == null) {
            return src.data;
        } else {
            return min(src.left_child);
        }
    }

    /**
     * Verifica se a árvore está vazia.
     * @return (Boolean) - True se vazio, false caso contrário.
     */
    public boolean empty() {
        return source == null;
    }

    /**
     * Procura por um determinado elemento da árvore.
     * @param element (Object) - Elemento a ser procurado.
     * @return (Boolean) - True se existir na árvore, False caso contrário.
     */
    public boolean search(Object element) {
        if (this.empty()) return false;
        else return this.search(element, source);
    }

    /**
     * Pesquisa um elemento na raiz.
     * @param element (Object) - Elemento a ser pesquisado.
     * @param src (Node) - Raiz onde o elemento vai ser pesquisado.
     * @return (Boolean) - True se existir na árvore, False caso contrário.
     */
    private boolean search(Object element, Node src) {
        if (element.equals(String.valueOf(src.data))) {
            return true;
        } else if (src.left_child.data.toString().contains(element.toString())) { // Lado Esquerdo (String).
            if (src.left_child == null) {
                return false;
            } else {
                code += "0";
                return this.search(element, src.left_child);
            }
        } else if (src.right_child.data.toString().contains(element.toString())) { // Lado Direito (String).
            if (src.right_child == null) {
                return false;
            } else {
                code += "1";
                return this.search(element, src.right_child);
            }
        }
        return false;
    }

    /**
     * Pega o código da árvore.
     */
    public void get_tree_code() {
        // Pré-Ordem.
        this.get_tree_code(source);
    }

    /** Pré-Ordem
     * Pega o código dos nó da árvore no percurso pré-ordem.
     * @param src (Node) - Raiz do percurso atual da árvore.
     */
    private void get_tree_code(Node src) {
        // Verifica se é folha e adiciona o código e, também, o valor binário do elemento.
        if (src.left_child == null && src.right_child == null) {
            tree_code += "1";
            tree_code += this.to_binary(src.data.toString());
        } else {
            tree_code += "0";
        }

        // Percorre o lado esquerdo.
        if (src.left_child != null) {
            this.get_tree_code(src.left_child);
        }

        // Percorre o lado direito.
        if (src.right_child != null) {
            this.get_tree_code(src.right_child);
        }
    }

    /**
     * Mostra todos os elementos da árvore.
     */
    public void show() {
        // Tipos de Percurso :. Pré-Ordem, Em Ordem, Pós-Ordem.
        this.show(source);
    }

    /** Pré-Ordem
     * Mostra todos os elementos da árvore.
     * @param src (Node) - Raiz do percurso atual da árvore.
     */
    public void show(Node src) {
        System.out.print(src.data + " ");

        if (src.left_child != null) {
            this.show(src.left_child);
        }

        if (src.right_child != null) {
            this.show(src.right_child);
        }
    }

    /**
     * Transforma uma String em binário.
     * @param text (String) - String a ser transformada.
     * @return (String) - O valor binário da string.
     */
    private String to_binary(String text) {
        int len = text.length();
        String reversed_bin = "";
        for (int i = 0; i < len; i++) {
            // Converte o valor do caractere para ASCII.
            int value = Integer.valueOf(text.charAt(i));

            // Converte ASCII para Binário.
            String bin = "";
            while (value > 0) {
                if (value % 2 == 1) {
                    bin += "1";
                } else {
                    bin += "0";
                }
                value /= 2;
            }

            String[] bin_arr = bin.split("");
            for (int chr = bin_arr.length - 1; chr >= 0; chr--) {
                reversed_bin += bin_arr[chr];
            }

            reversed_bin = String.format("%08d", Integer.parseInt(reversed_bin));
        }
        return reversed_bin;
    }
}
