import java.io.Serializable;
public class Tabuleiro implements Serializable {
    private static final int TAMANHO_TABULEIRO = 10;
    private Celula[][] celulas;

    public Tabuleiro() {
        celulas = new Celula[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
                celulas[i][j] = new Celula();
            }
        }
    }

    public void posicionarEmbarcacao(Embarcacao embarcacao, int linha, int coluna, boolean vertical) {
        int tamanho = embarcacao.getTamanho();
        if (vertical) {
            for (int i = 0; i < tamanho; i++) {
                celulas[linha + i][coluna].setEmbarcacao(embarcacao);
            }
        } else {
            for (int i = 0; i < tamanho; i++) {
                celulas[linha][coluna + i].setEmbarcacao(embarcacao);
            }
        }
    }

    public boolean posicaoEstaOcupada(int linha, int coluna) {
        return celulas[linha][coluna].estaOcupada();
    }

    public boolean marcarPosicaoAtigida(int linha, int coluna) {
        celulas[linha][coluna].marcarAtigida();
        return false;
    }

    public class Celula implements Serializable {
        private boolean atingida;
        private Embarcacao embarcacao;

        public Celula() {
            this.atingida = false;
            this.embarcacao = null;
        }

        public boolean estaOcupada() {
            return embarcacao != null;
        }

        public void setEmbarcacao(Embarcacao embarcacao) {
            this.embarcacao = embarcacao;
        }

        public void marcarAtigida() {
            this.atingida = true;
            if (embarcacao != null) {
                embarcacao.setPosicaoOcupada(0);
            }
        }
    }
}
