import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String SERVIDOR_IP = "172.20.144.1";
    private static final int PORTA = 12345;

    public void iniciar() {
        try {
            Socket socket = new Socket(SERVIDOR_IP, PORTA);
            System.out.println("Conectado ao servidor.");

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            Tabuleiro tabuleiro = (Tabuleiro) input.readObject();

            boolean jogoIniciado = (boolean) input.readObject();
            if (jogoIniciado) {
                System.out.println("O jogo começou!");
            }

            Scanner scanner = new Scanner(System.in);

            while (true) {
                boolean minhaVez = (boolean) input.readObject();
                if (minhaVez) {
                    System.out.println("Sua vez de jogar.");
                    System.out.print("Informe a linha: ");
                    int linha = scanner.nextInt();
                    System.out.print("Informe a coluna: ");
                    int coluna = scanner.nextInt();

                    Jogada jogada = new Jogada(linha, coluna);
                    output.writeObject(jogada);
                } else {
                    System.out.println("Aguarde a vez do adversário.");
                }

                tabuleiro = (Tabuleiro) input.readObject();
                exibirTabuleiro(tabuleiro);

                boolean jogoAcabou = (boolean) input.readObject();
                if (jogoAcabou) {
                    System.out.println("O jogo acabou!");
                    break;
                }
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void exibirTabuleiro(Tabuleiro tabuleiro) {
        // Implemente a lógica para exibir o tabuleiro na forma desejada
        // ...
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.iniciar();
    }
}
