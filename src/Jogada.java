import java.io.Serializable;

public class Jogada implements Serializable {
    private int linha;
    private int coluna;

    public Jogada(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}
