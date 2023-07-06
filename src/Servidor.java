import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    private static final int PORTA = 12345;
    private static final int NUM_JOGADORES = 2;
    private Tabuleiro tabuleiro;
    private boolean[] jogadoresConectados;
    private boolean[] jogadoresProntos;
    private int jogadorAtual;

    public Servidor() {
        tabuleiro = new Tabuleiro();
        jogadoresConectados = new boolean[NUM_JOGADORES];
        jogadoresProntos = new boolean[NUM_JOGADORES];
        jogadorAtual = 0;
    }

    public void iniciar() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORTA);
            System.out.println("Aguardando conexões de jogadores...");

            for (int i = 0; i < NUM_JOGADORES; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("Jogador " + (i + 1) + " conectado.");

                Thread thread = new Thread(new JogadorHandler(socket, i + 1));
                thread.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class JogadorHandler implements Runnable {
        private Socket socket;
        private int jogadorId;

        public JogadorHandler(Socket socket, int jogadorId) {
            this.socket = socket;
            this.jogadorId = jogadorId;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                output.writeObject(tabuleiro);

                jogadoresConectados[jogadorId - 1] = true;

                while (!todosJogadoresProntos()) {
                    boolean jogadorPronto = (boolean) input.readObject();
                    jogadoresProntos[jogadorId - 1] = jogadorPronto;
                }

                if (jogadorId == 1) {
                    output.writeObject(true); // Informa ao jogador 1 que o jogo vai começar
                }

                while (!jogoAcabou()) {
                    if (jogadorAtual == jogadorId - 1) {
                        output.writeObject(true); // Informa ao jogador que é a vez dele
                        Jogada jogada = (Jogada) input.readObject();
                        realizarJogada(jogadorId, jogada);
                    } else {
                        output.writeObject(false); // Informa ao jogador que é a vez do adversário
                    }

                    output.writeObject(tabuleiro); // Envia o tabuleiro atualizado para o jogador
                    jogadorAtual = (jogadorAtual + 1) % NUM_JOGADORES;
                }

                output.writeBoolean(false); // Informa ao jogador que o jogo acabou
                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized boolean todosJogadoresProntos() {
        for (boolean jogadorPronto : jogadoresProntos) {
            if (!jogadorPronto) {
                return false;
            }
        }
        return true;
    }

    private boolean jogoAcabou() {
        for (boolean jogadorConectado : jogadoresConectados) {
            if (!jogadorConectado) {
                return false;
            }
        }
        return true;
    }

    private boolean realizarJogada(int jogadorId, Jogada jogada) {
        int linha = jogada.getLinha();
        int coluna = jogada.getColuna();
        boolean acertou = tabuleiro.marcarPosicaoAtigida(linha, coluna);

        if (acertou) {
            System.out.println("Jogador " + jogadorId + " acertou o tiro!");
        } else {
            System.out.println("Jogador " + jogadorId + " errou o tiro!");
        }

        return acertou;
    }


    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}
