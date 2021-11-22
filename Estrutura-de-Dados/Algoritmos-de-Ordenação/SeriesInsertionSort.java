import java.util.*;

class SeriesInsertionSort {
    private String nome;
    private String formato;
    private String duracao;
    private String paisDeOrigem;
    private String idiomaOriginal;
    private String emissoraDeTelevisaoOriginal;
    private String transmissaoOriginal;
    private int numeroTemporadas;
    private int numeroEpisodios;
    static Scanner ler = new Scanner(System.in);
    static final String PREFIXO = "series/";

    // construtor primário
    public SeriesInsertionSort() {
        nome = "";
        formato = "";
        duracao = "";
        paisDeOrigem = "";
        idiomaOriginal = "";
        emissoraDeTelevisaoOriginal = "";
        transmissaoOriginal = "";
        numeroTemporadas = 0;
        numeroEpisodios = 0;
    }

    // construtor secundário
    public SeriesInsertionSort(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
            String emissoraDeTelevisaoOriginal, String transmissaoOriginal, int numeroTemporadas, int numeroEpisodios) {
        this.nome = nome;
        this.formato = formato;
        this.duracao = duracao;
        this.paisDeOrigem = paisDeOrigem;
        this.idiomaOriginal = paisDeOrigem;
        this.emissoraDeTelevisaoOriginal = emissoraDeTelevisaoOriginal;
        this.transmissaoOriginal = transmissaoOriginal;
        this.numeroTemporadas = numeroTemporadas;
        this.numeroEpisodios = numeroEpisodios;
    }

    public static SeriesInsertionSort lerAtributo(String endereco) {
        SeriesInsertionSort s1 = new SeriesInsertionSort();
        s1.setNome(trataNome(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "firstHeading")))).trim());
        s1.setFormato(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Formato"))).trim());
        s1.setDuracao(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Duração"))).trim());
        s1.setPaisDeOrigem(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "País de origem"))).trim());
        // System.out.println(s1.getFormato() + " - " + s1.getFormato().length());
        s1.setIdiomaOriginal(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Idioma original"))).trim());
        s1.setEmissoraDeTelevisaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Emissora de televisão original"))).trim());
        s1.setTransmissaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Transmissão original"))).trim());
        s1.setNumeroTemporadas(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de temporadas"))).trim()));
        s1.setNumeroEpisodios(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de episódios"))).trim()));
        return s1;
    }

    public static void imprimir(SeriesInsertionSort s1) {
        System.out.println(s1.getNome() + " " + s1.getFormato() + " " + s1.getDuracao() + " " + s1.getPaisDeOrigem()
                + " " + s1.getIdiomaOriginal() + " " + s1.getEmissoraDeTelevisaoOriginal() + " "
                + s1.getTransmissaoOriginal() + " " + s1.getNumeroTemporadas() + " " + s1.getNumeroEpisodios());
    }

    // Abre o arquivo e retorna o código fonte no vetor
    public static String[] lerCodigoFonte(String endereco) {
        String[] vetor = new String[3000];
        int linha = 0;

        if (!Arq.openRead(endereco, "UTF-8")) {
            System.err.printf("Erro ao abrir o arquivo %s\n", endereco);
            System.exit(1);
        }
        Arq.openRead(endereco, "UTF-8");

        while (Arq.hasNext()) {
            vetor[linha] = Arq.readLine();
            linha++;
        }

        Arq.close();
        return vetor;
    }

    // funções de tratamento de dados
    public static String removeTags(String line) {
        String newLine = "";
        int i = 0;
        while (i < line.length() && line.charAt(i) != '<') {
            i++;
        }
        while (i < line.length()) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>')
                    i++;
            } else {
                newLine += line.charAt(i);
            }
            i++;
        }
        return newLine;
    }

    public static String pegaLinha(String endereco, String identificaAtributo) {
        String[] codigoFonte = lerCodigoFonte(endereco);
        int i = 0;
        String linha = "";
        while (i < codigoFonte.length) {
            if (identificaAtributo == "firstHeading" && codigoFonte[i].contains("firstHeading")) {
                linha = codigoFonte[i];
                i = codigoFonte.length;
            } else if (codigoFonte[i].contains(identificaAtributo)) {
                linha = codigoFonte[i + 1];
                i = codigoFonte.length;
            }
            i++;
        }
        return linha;
    }

    public static String trataCaracteresEspeciais(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length()) {
            if (semTags.charAt(i) == '&') {
                i += 6;
            }
            res += semTags.charAt(i);
            i++;
        }
        return res;
    }

    public static String trataNome(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length()) {
            if (semTags.charAt(i) == '(') {
                i = semTags.length();
            } else {
                res += semTags.charAt(i);
                i++;
            }
        }
        // remover espaço em branco
        // if (res.charAt(res.length() - 1) == ' ') {
        // res = res.replaceFirst(".$", "");
        // }
        return res;
    }

    public static int converteParaInt(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length() && isNumber(semTags.charAt(i)) != false) {
            res += semTags.charAt(i);
            i++;
        }
        return Integer.parseInt(res);
    }

    // gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getPaisDeOrigem() {
        return paisDeOrigem;
    }

    public void setPaisDeOrigem(String paisDeOrigem) {
        this.paisDeOrigem = paisDeOrigem;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getEmissoraDeTelevisaoOriginal() {
        return emissoraDeTelevisaoOriginal;
    }

    public void setEmissoraDeTelevisaoOriginal(String emissoraDeTelevisaoOriginal) {
        this.emissoraDeTelevisaoOriginal = emissoraDeTelevisaoOriginal;
    }

    public String getTransmissaoOriginal() {
        return transmissaoOriginal;
    }

    public void setTransmissaoOriginal(String transmissaoOriginal) {
        this.transmissaoOriginal = transmissaoOriginal;
    }

    public int getNumeroTemporadas() {
        return numeroTemporadas;
    }

    public void setNumeroTemporadas(int numeroTemporadas) {
        this.numeroTemporadas = numeroTemporadas;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }

    // condição de parada do programa
    public static boolean isFim(String s) {
        return (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean isNumber(char s) {
        return s >= '0' && s <= '9';
    }

    public static void main(String[] args) throws Exception {
        Lista lista = new Lista();
        SeriesInsertionSort vetorObjetoSeries[] = new SeriesInsertionSort[61];
        String entrada = ler.nextLine();
        String endereco = PREFIXO;
        int i = 0, j = 0;

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            // armazeno os objetos num vetor
            vetorObjetoSeries[i++] = lerAtributo(caminhoDoArquivo);

            entrada = ler.nextLine();
        }
        while (j < i) {
            lista.inserirFim(vetorObjetoSeries[j++]);
        }
        lista.insertionSort(i);
        lista.mostrar();
        ler.close();
    }
}

class Lista {

    SeriesInsertionSort[] vetorObjetoSeries;
    int indice;

    Lista() {
        this(61);
    }

    Lista(int tamanho) {
        vetorObjetoSeries = new SeriesInsertionSort[tamanho];
        indice = 0;
    }

    void insertionSort(int tam) {
        Long tempoInicial = System.currentTimeMillis();
        int numeroComparacoes = 0;
        int numeroMovimentacoes = 0;
        for (int i = 1; i < tam; i++) {
            SeriesInsertionSort tmp = vetorObjetoSeries[i];//
            int j = i - 1;//
            // ingles > Ingles; I vem primeiro que i minúsculo
            while ((j >= 0) && (vetorObjetoSeries[j].getIdiomaOriginal().compareTo(tmp.getIdiomaOriginal())) > 0) {//
                vetorObjetoSeries[j + 1] = vetorObjetoSeries[j];
                j--;
                numeroMovimentacoes++;
                numeroComparacoes++;
            }
            while ((j >= 0) && (vetorObjetoSeries[j].getIdiomaOriginal().compareTo(tmp.getIdiomaOriginal())) == 0
                    && vetorObjetoSeries[j].getNome().compareTo(tmp.getNome()) > 0) {//
                vetorObjetoSeries[j + 1] = vetorObjetoSeries[j];
                j--;
                numeroMovimentacoes++;
                numeroComparacoes += 2;
            }
            vetorObjetoSeries[j + 1] = tmp;//
        }
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, numeroComparacoes, numeroMovimentacoes);
    }

    void inserirFim(SeriesInsertionSort serie) throws Exception {
        if (indice >= vetorObjetoSeries.length) {
            new Exception("there's impossible to insert, no memory avaliable");
        }
        vetorObjetoSeries[indice] = serie;
        indice++;
    }

    void mostrar() {
        for (int i = 0; i < indice; i++) {
            vetorObjetoSeries[i].imprimir(vetorObjetoSeries[i]);
        }
    }

    void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_insercao.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");
        // Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

}
