package Estruturas;

/**
 * Representa uma Lista Dinâmica.
 */
public class ListaDinamica {
    public Node _first_attribute;
    public Node _second_attribute;

    /**
     * Cria uma Linked List.
     */
    public ListaDinamica() {
        _first_attribute = null;
        _second_attribute = null;
    }

    /**
     * Adiciona um elemento na lista.
     * @param _value (Object) - Elemento a ser adicionado.
     */
    public void push(Object _value) {
        Node _node = new Node(_value);

        if (_first_attribute == null) {
            _first_attribute = _node;
        } else {
            _second_attribute.address = _node;
        }
        _second_attribute = _node;
    }

    /**
     * Adiciona um elemento, na lista, em uma determinada posição.
     * @param _index (Integer) - Posição em que o elemento vai ser adicionado.
     * @param _value (Object) - Elemento a ser adicionado.
     */
    public void push(int _index, Object _value) {
        if (_index < 0 || _index > this.size()) System.out.println("Invalid position.");

        if (this.size() == 0) {
            this.push(_value);
        } else if (this.size() == 1) {
            Node _node = new Node(_value);

            _node.address = _first_attribute;
            _first_attribute = _node;
        } else if (_index == this.size()) {
            Node _node = new Node(_value);

            _second_attribute.address = _node;
            _second_attribute = _node;
        } else {
            int counter = 0;
            Node _indicator = _first_attribute;
            Node _node = new Node(_value);

            // Posição 0
            if (_index == counter) {
                _node.address = _first_attribute;
                _first_attribute = _node;
            }

            while (counter < _index) {
                // ArrayOutOfBoundsException
                if (_indicator == null) return;

                if (counter == _index - 1) {
                    _node.address = _indicator.address;
                    _indicator.address = _node;
                }

                counter++;
                _indicator = _indicator.address;
            }
        }
    }

    /**
     * Mostra o tamanho da lista.
     * @return (Integer) - O tamanho da lista.
     */
    public int size() {
        int counter = 0;
        Node _indicator = _first_attribute;

        while (_indicator != null) {
            _indicator = _indicator.address;
            counter++;
        }

        return counter;
    }

    /**
     * Verifica se o elemento existe na lista.
     * @param _value (Object) - Elemento a ser pesquisado.
     * @return (Boolean) - True se estiver na lista, false caso contrário.
     */
    public boolean contains(Object _value) {
        Node _indicator = _first_attribute;

        while (_indicator != null) {
            if (_indicator.data.equals(_value)) {
                return true;
            }
            _indicator = _indicator.address;
        }

        return false;
    }

    /**
     * Remove o último elemento da lista.
     */
    public void remove() {
        if (_first_attribute != null) {
            int counter = 0;
            Node _indicator = _first_attribute;

            while (counter < this.size() - 1) {
                counter++;
                _indicator = _indicator.address;
            }

            _indicator.address = null;
            _second_attribute = _indicator;
        } else {
            System.out.println("Empty List");
        }
    }

    /**
     * Remove um elemento em uma determinada posição.
     * @param _index (Integer) - Posição a ser removida da lista.
     */
    public void remove(int _index) {
        if (_index < 0 || _index > this.size()) System.out.println("Invalid position.");

        int counter = 0;
        Node _indicator = _first_attribute;

        while (counter <= _index) {
            if (counter == _index && _index == 0) {
                _first_attribute = _indicator.address;
            }

            if (_indicator.address == null) return;

            if (counter == _index - 1) {
                _indicator.address = _indicator.address.address;
            }

            counter++;
            _indicator = _indicator.address;
        }
    }

    /**
     * Remove a primeira ocorrência da lista.
     * @param _value (Object) - Elemento a ser removido.
     */
    public void removeItem(Object _value) {
        int counter = 0;
        Node _indicator = _first_attribute;

        while (!_indicator.data.equals(_value)) {
            counter++;
            _indicator = _indicator.address;
        }

        this.remove(counter);
    }

    /**
     * Procura por um elemento na lista.
     * @param _value (Object) - Elemento a ser pesquisado.
     * @return (Integer) - A posição do elemento se encontrado, -1 caso contrário.
     */
    public int find(Object _value) {
        int counter = 0;
        Node _indicator = _first_attribute;

        while (_indicator != null) {
            if (_indicator.data.equals(_value)) return counter;

            _indicator = _indicator.address;
            counter++;
        }
        return -1;
    }

    /**
     * Mostra o elemento em uma determinada posição da lista.
     * @param _position (Integer) - Posição em que será mostrada o valor armazenado.
     * @return (Object) - O elemento naquela posição.
     */
    public Object index(int _position) {
        if (_position < 0 || _position > this.size()) {
            System.out.println("Invalid position");
            return -1;
        } else {
            int counter = 0;
            Node _indicator = _first_attribute;

            while (counter < _position) {
                if (_indicator == null) return -1;

                _indicator = _indicator.address;
                counter++;
            }

            _indicator.counter++;
            return _indicator.data;
        }
    }

    /**
     * Mostra os elementos da lista.
     */
    public void show() {
        Node _indicator = _first_attribute;

        while (_indicator != null) {
//            System.out.printf("%s:%d, ", _indicator.data, _indicator.counter);
            System.out.printf("%s ", _indicator.data);
            _indicator = _indicator.address;
        }
        System.out.println();
    }

    /**
     * Limpa a lista.
     */
    public void clear() {
        _first_attribute = null;
        _second_attribute = null;
    }
}
