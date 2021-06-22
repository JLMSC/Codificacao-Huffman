import Estruturas.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    /**
     * Faz a leitura de um arquivo.
     * @param file_dir (String) - Diretório do arquivo.
     * @return (FilaPrioridadeOrdenada) - As linhas lidas.
     */
    public static FilaPrioridadeOrdenada ReadFile(String file_dir) throws IOException {
        // Lista que vai armazenar as linhas lidas e retornar ambas.
        FilaPrioridadeOrdenada filaPrioridadeOrdenada = new FilaPrioridadeOrdenada();

        // Vai no diretório do arquivo e lê duas linhas especificas (1 e 2).
        Path file = Paths.get(file_dir);

        // Armazena os valores, prioridades diferentes para não bagunçar a ordem.
        filaPrioridadeOrdenada.enqueue(Files.readAllLines(file).get(0), 0);
        filaPrioridadeOrdenada.enqueue(Files.readAllLines(file).get(1), 1);

        return filaPrioridadeOrdenada;
    }

    /**
     * Faz a leitura de um arquivo, caractere por caractere, e armazena-o em uma String junto de sua frequência.
     * @param file_dir (String) - Diretório do arquivo.
     * @param listaDinamica (ListaDinamica) - Lista que vai armazenar a frequência dos caracteres.
     */
    public static String ReadFile(String file_dir, ListaDinamica listaDinamica) {
        // Abre o arquivo e faz a leitura.
        String file_content = "";
        File file = new File(file_dir);
        try (FileReader fileReader = new FileReader(file)) {
            // Lê o código octal de cada caractere e para quando não há mais nenhum.
            int content;
            char file_char;
            while ((content = fileReader.read()) != -1) {
                // Pega o código cotal do caractere e transforma em "sinal".
                file_char = (char) content;

                // Aumenta a frequência do caractere.
                if (!listaDinamica.contains(file_char)) {
                    listaDinamica.push(String.valueOf(file_char));
                } else {
                    listaDinamica.index(listaDinamica.find(file_char));
                }

                file_content += file_char;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        return file_content;
    }

    /**
     * Armazena os caracteres e sua prioridade em uma fila de prioridade.
     * @param filaPrioridadeOrdenada (FilaDePrioridadeOrdenada) - Onde será armazenado os valores.
     * @param listaDinamica (ListaDinamica) - Onde será extraído os valores.
     */
    static void Organizar(FilaPrioridadeOrdenada filaPrioridadeOrdenada, ListaDinamica listaDinamica) {
        // Só percorre os Nó da Lista Dinâmica, não tem nada de mais aqui.
        Node _indicator = listaDinamica._first_attribute;

        while (_indicator != null) {
            filaPrioridadeOrdenada.enqueue(_indicator.data, _indicator.counter);
            _indicator = _indicator.address;
        }
    }

    /**
     * Faz a codificação de Huffman, só isso.
     * @param filaPrioridadeOrdenada (FilaDePrioridadeOrdenada) - Onde será armazenado os novos valores e as prioridades.
     * @param arvoreBinariaDeBusca (ArvoreBinariaDeBusca) - Onde os valores serão armazenados após o término da codificação.
     */
    static void CodificacaoHuffman(FilaPrioridadeOrdenada filaPrioridadeOrdenada, ArvoreBinariaDeBusca arvoreBinariaDeBusca) {
        Node esquerdo, direito, raiz;
        while (filaPrioridadeOrdenada.size() != 1) {
            // Remove os dois primeiros elementos de menor frequência.
            esquerdo = filaPrioridadeOrdenada.dequeue();
            direito = filaPrioridadeOrdenada.dequeue();

            // Verifica se o nó direito é um nó pai.
            if (direito.data instanceof Node) {
                // Verifica se o nó esquerdo também é um nó pai.
                if (esquerdo.data instanceof Node) {
                    // Junta o nó esquerdo e direito em um único nó.
                    Node temp1 = (Node) esquerdo.data, temp2 = (Node) direito.data;
                    raiz = new Node(((Node) esquerdo.data).data.toString() + ((Node) direito.data).data.toString(), ((Node) esquerdo.data).priority + ((Node) direito.data).priority);
                    arvoreBinariaDeBusca.source = raiz;
                    arvoreBinariaDeBusca.source.left_child = temp1;
                    arvoreBinariaDeBusca.source.right_child = temp2;
                } else {
                    Node temp1 = (Node) direito.data;
                    raiz = new Node(esquerdo.data.toString() + ((Node) direito.data).data.toString(), esquerdo.priority + ((Node) direito.data).priority);
                    arvoreBinariaDeBusca.source = raiz;
                    arvoreBinariaDeBusca.source.left_child = esquerdo;
                    arvoreBinariaDeBusca.source.right_child = temp1;
                }
            } else if (esquerdo.data instanceof Node){ // Verifica se o nó esquerdo é um nó pai.
                Node temp1 = (Node) esquerdo.data;
                raiz = new Node(direito.data.toString() + ((Node) esquerdo.data).data.toString(), direito.priority + ((Node) esquerdo.data).priority);
                arvoreBinariaDeBusca.source = raiz;
                arvoreBinariaDeBusca.source.left_child = direito;
                arvoreBinariaDeBusca.source.right_child = temp1;
            } else {
                // Junta dois elementos em um nó, ou um nó e um elemento em outro nó.
                raiz = new Node(esquerdo.data.toString() + direito.data.toString(), esquerdo.priority + direito.priority);
                arvoreBinariaDeBusca.source = raiz;
                arvoreBinariaDeBusca.source.left_child = esquerdo;
                arvoreBinariaDeBusca.source.right_child = direito;
            }
            filaPrioridadeOrdenada.enqueue(arvoreBinariaDeBusca.source, arvoreBinariaDeBusca.source.priority);
        }

        // Caso seja um único caractere, ele basicamente só guarda a árvore.
        Object target_temp = filaPrioridadeOrdenada.front().data;
        if (target_temp.toString().length() == 1) {
            arvoreBinariaDeBusca.source = new Node("");
            arvoreBinariaDeBusca.source.left_child = filaPrioridadeOrdenada.front();
        } else {
            // Passa o nó pai da fila de prioridade para a árvore binária de busca.
            arvoreBinariaDeBusca.source = (Node) filaPrioridadeOrdenada.front().data;
        }
    }

    /**
     * Percorre a Árvore Binária de Busca, gerando um código para cada caractere.
     * @param text (String) - Texto a ser codificado.
     * @param arvoreBinariaDeBusca (ArvoreBinariaDeBusca) - Árvore de Huffman.
     */
    static void Codificar(String text, ArvoreBinariaDeBusca arvoreBinariaDeBusca) {
        arvoreBinariaDeBusca.search(text);
    }

    /**
     * Percorre a Árvore Binária de Busca, gerando um código para ela.
     * @param arvoreBinariaDeBusca (ArvoreBinariaDeBusca) - Árvore de Huffman.
     */
    static void CodificarArvore(ArvoreBinariaDeBusca arvoreBinariaDeBusca) {
        arvoreBinariaDeBusca.get_tree_code();
    }

    /**
     * Armazena o resultado em um arquivo de texto.
     * @param file_dir (String) - Diretório do arquivo.
     * @param arvoreBinariaDeBusca (ArvoreBinariaDeBusca) - A Árvore de Huffman que foi utilizada no processo.
     */
    static void Compactar(String file_dir, ArvoreBinariaDeBusca arvoreBinariaDeBusca) throws IOException {
        // Abre o arquivo alvo e verifica se o mesmo existe, caso contrário, cria um novo.
        File file_target = new File(file_dir);
        if (file_target.createNewFile()) {
            System.out.println("*Arquivo `Arquivo_Compactado.txt` criado.");
        }

        // Escreve no arquivo e fecha ele.
        FileWriter fileWriter = new FileWriter(file_target, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);

        bufferedWriter.write(arvoreBinariaDeBusca.tree_code);
        bufferedWriter.newLine();
        bufferedWriter.write(arvoreBinariaDeBusca.code);
        bufferedWriter.close();

        System.out.println("*Escreveu no arquivo `Arquivo_Compactado.txt`.");
    }

    /**
     * Separa a direção dos nós, dos valores em binário.
     * @param tree_code (String) - O código da Árvore Binária de Busca.
     * @return (String) - String que contem o novo código da Árvore Binária de Busca.
     */
    static String OrganizarCodigoArvore(String tree_code) {
        // Onde será armazenado o código "arrumado".
        String organized_tree_code = "";
        // Flag, ativada sempre que atingir uma folha.
        boolean is_leaf;
        // Controlador de posição.
        int counter_pos = 0;
        // Armazenar o valor das folhas.
        String bin_value = "";

        // Percorre o texto do código da Árvore e vai armazenando os caminhos até a folha e o valor contido na folha.
        String[] tree_code_as_arr = tree_code.split("");
        for (int i = 0; i < tree_code_as_arr.length - 1; i++) {
            if (tree_code_as_arr[i].equals("0")) {
                organized_tree_code += tree_code_as_arr[i];
            } else if (tree_code_as_arr[i].equals("1")) {
                is_leaf = true;
                // Os @PH@ é PlaceHolder, botar espaço não adianta.
                organized_tree_code += tree_code_as_arr[i] + "@PH@";

                // Pega os próximos 7 valores binários do texto.
                while (is_leaf) {
                    if (counter_pos >= 8) {
                        is_leaf = false;
                        counter_pos = 0;
                        // Adiciona o caractere a partir do código binário, contido em "bin_value", na lista.
                        organized_tree_code += (char) Integer.parseInt(bin_value, 2) + "@PH@";
                    } else {
                        bin_value += tree_code_as_arr[i + counter_pos + 1];
                        tree_code_as_arr[i + counter_pos + 1] = "\\";
                        counter_pos++;
                    }
                }
                bin_value = "";
            }
        }
        // Retorna o código da árvore com os valores binários já convertido e sem o espaço no final.
        return organized_tree_code;
    }

    /**
     * Reconstroi a Árvore Binária de Busca, baseado no código fornecido.
     * @param source (Node) - Caminho atual do Nó.
     * @param text (String[]) - Texto que contém o código da Árvore.
     */
    public static void MontarArvore(Node source, String[] text) {
        // Controla a posição.
        int pos = 0;
        while (!(pos >= text.length - 1)) {
            // Ignora index múltiplo de dois, os valores da folha vão SEMPRE ficar ai.
            if ((pos + 1) % 2 != 0) {
                // Se o nó tiver dois filhos, ele volta pro nó pai.
                if (source.left_child != null && source.right_child != null) {
                    source = source.main_source;
                    pos -= 1;
                } else {
                    // Percorre o texto lido na posição pos.
                    for (int i = 0; i < text[pos].length(); i++) {
                        // Se for 0, ele adicionado um filho esquerdo ou direito (no caso o que ele não tem).
                        if ('0' == text[pos].charAt(i)) {
                            if (source.left_child == null) {
                                source.left_child = new Node("");
                                source.left_child.main_source = source;
                                source = source.left_child;
                            } else if (source.right_child == null) {
                                source.right_child = new Node("");
                                source.right_child.main_source = source;
                                source = source.right_child;
                            }
                        }
                        // Se for 1, cria um filho ao nó e bota o valor na folha criada.
                        else if ('1' == text[pos].charAt(i)) {
                            if (source.left_child == null) {
                                source.left_child = new Node(text[pos + 1]);
                                source.left_child.main_source = source;
                            } else if (source.right_child == null) {
                                source.right_child = new Node(text[pos + 1]);
                                source.right_child.main_source = source;
                            }
                        }
                    }
                }

            }
            pos += 1;
        }
    }

    /**
     * Decofidica a mensagem a partir da Árvore Binária de Busca.
     * @param starting_position (Node) - Posição inicial, a raiz.
     * @param text (String) - Mensagem que contém o código da mensagem.
     * @return (String) - A mensagem descodificada.
     */
    public static String Decodificar(Node starting_position, String text) {
        // Armazena a mensagem descodificada.
        String mensagem = "";
        // Posição atual do nó.
        Node actual_position = starting_position;

        // Percorre a Árvore Binária de Busca através do código do texto.
        int i = 0;
        String[] text_as_arr = text.split("");
        do {
            // Volta uma casa, para controlar a leitura da mensagem, e adiciona o valor se for uma folha.
            if (actual_position.left_child == null && actual_position.right_child == null) {
                mensagem += actual_position.data;
                actual_position = starting_position;
                i -= 1;
            }
            // Se o código for 0 e o filho esquerdo não estiver nulo, ele define a posição atual lá.
            else if (actual_position.left_child != null && text_as_arr[i].equals("0")) {
                actual_position = actual_position.left_child;
            }
            // Se o código for 1 e o filho direito não estiver nulo, ele define a posição atual lá.
            else if (actual_position.right_child != null && text_as_arr[i].equals("1")) {
                actual_position = actual_position.right_child;
            }
            i++;
        } while (i < text_as_arr.length);
        return mensagem;
    }

    /**
     *
     * @param file_dir (String) - Caminho do arquivo.
     * @param message (String) - Mensagem descompactada a ser escrevida no arquivo.
     */
    static void Descompactar(String file_dir, String message) throws IOException {
        // Abre o arquivo alvo e verifica se o mesmo existe, caso contrário, cria um novo.
        File file_target = new File(file_dir);
        if (file_target.createNewFile()) {
            System.out.println("*Arquivo `Arquivo_Descompactado.txt` criado.");
        }

        // Escreve no arquivo e fecha ele.
        FileWriter fileWriter = new FileWriter(file_target, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);

        bufferedWriter.write(message);
        bufferedWriter.close();

        System.out.println("*Escreveu no arquivo `Arquivo_Descompactado.txt`.");
    }
    public static void main(String[] args) throws IOException {
        // Faz compactação ou descompactação baseado na escolha do usuário.
        int action;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("*Você deseja:\nCompactar (1) ou Descompactar (2) ?");
        action = Integer.parseInt(input.readLine());

        // Compactação.
        if (action == 1) {
            // Lista que vai armazenar a frequência de cada caractere.
            ListaDinamica frequencia = new ListaDinamica();
            // Lista que vai armazenar e organizar os caracteres.
            FilaPrioridadeOrdenada filaPrioridadeOrdenada = new FilaPrioridadeOrdenada();
            // Árvore Binária de Busca.
            ArvoreBinariaDeBusca arvoreBinariaDeBusca = new ArvoreBinariaDeBusca();

            // Pega o conteúdo do arquivo lido e o armazena junto com sua frequência.
            String file_content = ReadFile("src\\Arquivo.txt", frequencia);

            // Armazena os caracteres com as frequências em uma fila de prioridade.
            Organizar(filaPrioridadeOrdenada, frequencia);

            // Monta a árvore de Huffman.
            CodificacaoHuffman(filaPrioridadeOrdenada, arvoreBinariaDeBusca);

            // Pega o código de cada caractere.
            for (String chr : file_content.split("")) {
                Codificar(chr, arvoreBinariaDeBusca);
            }

            // Pega o código da Árvore Binária de Busca.
            CodificarArvore(arvoreBinariaDeBusca);

            // Armazena o resultado em um outro arquivo.
            Compactar("src\\Resultado\\Arquivo_Compactado.txt", arvoreBinariaDeBusca);
        }
        // Descompactação.
        else if (action == 2) {
            // As linhas lidas, em formato de String, e uma Fila, o qual vai armazenar o que foi lido.
            String tree_original_code = "", text_original_code = "";
            FilaPrioridadeOrdenada filaPrioridadeOrdenada = new FilaPrioridadeOrdenada();

            // Lê o arquivo para descompactação.
            filaPrioridadeOrdenada = ReadFile("src\\Arquivo.txt");

            // Pega o que foi retornado da leitura do arquivo e armazena-os como String.
            tree_original_code = filaPrioridadeOrdenada.dequeue().data.toString();
            text_original_code = filaPrioridadeOrdenada.dequeue().data.toString() + "\\";

            // Organiza o código, separa a direção dos valores.
            tree_original_code = OrganizarCodigoArvore(tree_original_code);

            // Reconstroi a Árvore Binária de Busca (Huffman).
            ArvoreBinariaDeBusca arvoreReconstruida = new ArvoreBinariaDeBusca();
            arvoreReconstruida.source = new Node("");
            MontarArvore(arvoreReconstruida.source, tree_original_code.split("@PH@"));

            /*
             Na função recursiva, toda á árvore é feita no lado esquerdo da raiz, não sei pq, mas isso não é um
             problema, é só definir a raiz como o filho esquerdo.
             */
            arvoreReconstruida.source = arvoreReconstruida.source.left_child;

            // Decodifica a segunda linha e armazena a mensagem na variável.
            String original_message = Decodificar(arvoreReconstruida.source, text_original_code);

            // Escreve a mensagem no arquivo.
            Descompactar("src\\Resultado\\Arquivo_Descompactado.txt", original_message);
        }
    }
}
