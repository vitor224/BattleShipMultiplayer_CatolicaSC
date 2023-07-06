// Classe abstrata para representar uma embarcação genérica
public abstract class Embarcacao {
    private String tipo; // Tipo da embarcação
    private int tamanho; // Tamanho da embarcação
    private boolean[] posicoesOcupadas; // Array para armazenar as posições ocupadas pela embarcação

    public Embarcacao(String tipo, int tamanho) {
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.posicoesOcupadas = new boolean[tamanho];
    }

    public String getTipo() {
        return tipo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setPosicaoOcupada(int posicao) {
        posicoesOcupadas[posicao] = true;
    }

    public boolean isPosicaoOcupada(int posicao) {
        return posicoesOcupadas[posicao];
    }

    public boolean foiAfundada() {
        for (boolean posicao : posicoesOcupadas) {
            if (!posicao) {
                return false;
            }
        }
        return true;
    }
}

