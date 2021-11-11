import java.util.*;

//atente-se ao caminho na main

class Series {
    private String nome;

    // construtor primário (inicializando atributos da classe)
    public Series() {
        nome = "";
    }

    // construtor secundário
    public Series(String nome) {
        this.nome = nome;
    }

    // Abre o arquivo e salva o código fonte no vetor
    public static String[] lerTexto(String endereco) {
        String[] vetor = new String[3000];
        int linha = 0;

        Arq.openRead(endereco, "UTF-8");
        while (Arq.hasNext()) {
            vetor[linha] = Arq.readLine();
            linha++;
        }

        Arq.close();
        return vetor;
    }

    // funções de tratamento de dados
    public static String pegaNome(String[] texto) {
        int linha = 0;
        int i = 0;
        String str = "";

        while (!texto[linha].contains("firstHeading"))
            linha++;
        if (texto[linha].contains("firstHeading")) {
            while (texto[linha].charAt(i) != '>') {
                i++;
            }
            i++;
            while (texto[linha].charAt(i) != '>') {
                i++;
            }
            i++;
            while (texto[linha].charAt(i) != '<') {
                str += texto[linha].charAt(i++);
            }

        }

        return str;
    }

    // gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

class Lista {
    ArrayList<String> arrayList;
    ArrayList<String> arrayListInput;
    Long tempoInicial;
    Long tempoFinal;
    int count;

    public Lista() {
        arrayList = new ArrayList<String>();
        arrayListInput = new ArrayList<String>();
    }

    public void InserirNome(String str) {
        arrayList.add(str);
    }

    public void InserirInput(String str) {
        arrayListInput.add(str);
    }

    public void Mostrar() {
        // MyIO.println(arrayList.size());

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

    }

    public void MostrarInput() {

        for (int i = 0; i < arrayListInput.size(); i++) {
            System.out.println(arrayListInput.get(i));
        }

    }

    public void comparacao() {
        MyIO.setCharset("UTF-8");
        // o laço externo corresponde ao maior numero de elementos
        tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < arrayListInput.size(); i++) {
            String res = "NÃO";
            for (int j = 0; j < arrayList.size(); j++) {
                count++;
                // MyIO.println(arrayList.get(i) + " - " + arrayListInput.get(j));
                if (arrayListInput.get(i).equals(arrayList.get(j))) {
                    res = "SIM";
                }
            }
            MyIO.println(res);
        }

        tempoFinal = System.currentTimeMillis();

    }

}

public class PesquisaSequencial {
    public static void main(String[] args) {
        Lista lista = new Lista();
        Series series = new Series();

        String entrada = MyIO.readLine();

        while (!isFim(entrada)) {
            String endereco = "series/" + entrada;
            String[] vetor = series.lerTexto(endereco);
            String str = series.pegaNome(vetor);

            lista.InserirNome(str);
            entrada = MyIO.readLine();
        }
        // lista.Mostrar();

        // armazenando a segunda parte(entrada) no array
        String input = MyIO.readLine();
        while (!isFim(input)) {
            lista.InserirInput(input);
            input = MyIO.readLine();
        }
        // lista.MostrarInput();

        lista.comparacao();
        arquivoLog(tempoExecucao(lista.tempoInicial, lista.tempoFinal), numeroComparacoes(lista.count));

    }

    public static Long tempoExecucao(long inicio, long fim) {
        return fim - inicio;
    }

    public static int numeroComparacoes(int count) {
        return count;
    }

    public static void arquivoLog(Long tempoExecucao, int numeroComparacoes) {
        Arq.openWrite("matricula_sequencial.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao / 1000 + " s\t");
        Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
    // condição de parada do programa
}
