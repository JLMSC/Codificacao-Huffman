package Estruturas;

/**
 * Representa uma Fila de Prioridade Ordenada.
 */
public class FilaPrioridadeOrdenada {
    private Node $first;
    private Node $last;
    private int $counter;

    /**
     * Cria a Fila de Prioridade.
     */
    public FilaPrioridadeOrdenada() {
        $first = null;
        $last = null;
        $counter = 0;
    }

    /**
     * Adiciona um elemento, com prioridade, na fila.
     * @param $value (Integer) - Elemento a ser adicionado.
     * @param $priority (Integer) - Prioridade do elemento.
     */
    public void enqueue(Object $value, int $priority) {
        Node $new = new Node($value, $priority);

        if ($first == null) {
            $first = $new;
            $last = $new;
        } else if ($new.priority < $first.priority) {
            $new.address = $first;
            $first.last_address = $new;
            $first = $new;
        } else if ($new.priority >= $last.priority) {
            $last.address = $new;
            $new.last_address = $last;
            $last = $new;
        } else {
            Node $actual = $first;

            while ($actual != null && $new.priority >= $actual.priority) {
                $actual = $actual.address;
            }

            $new.address = $actual;
            $new.last_address = $actual.last_address;
            $actual.last_address = $new;
            $new.last_address.address = $new;
        }
        $counter++;
    }

    /**
     * Remove o elemento do topo da fila.
     * @return (Node) - Nó removido.
     */
    public Node dequeue() {
        if ($first != null) {
            Node $aux = $first;
            $first = $first.address;
            $counter--;
            return $aux;
        }
        return null;
    }

    /**
     * Retorna o primeiro elemento da fila.
     * @return (Node) - Elemento do topo da fila.
     */
    public Node front() {
        if ($first != null) {
            return $first;
        }
        return null;
    }

    /**
     * Mostra o tamanho da fila.
     * @return (Integer) - O tamanho da fila.
     */
    public int size() {
        // Mostra o tamanho da fila.
        return $counter;
    }

    /**
     * Verifica se a fila está vazia.
     * @return (Boolean) - True se estiver vazia, false caso contrário.
     */
    public boolean empty() {
        return $counter == 0;
    }

    /**
     * Mostra os elementos da fila.
     */
    public void elements() {
        Node _indicator = $first;

        while (_indicator != null) {
            System.out.printf("%s ", _indicator.data);
            _indicator = _indicator.address;
        }
        System.out.println();
    }
}
