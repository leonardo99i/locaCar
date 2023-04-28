package locadora;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class conexao {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 300);
            System.out.println("Conectado ao servidor.");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            try (Scanner scanner = new Scanner(System.in)) {
                int opcao = 0;
                do {
                    System.out.println("Selecione uma opção:");
                    System.out.println("1 - Listar carros");
                    System.out.println("2 - Alugar carro");
                    System.out.println("3 - Devolver carro");
                    System.out.println("4 - Sair");

                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir o \n deixado pelo nextInt()

                    switch (opcao) {
                        case 1:
                            output.writeObject("listar carros");
                            List<Carro> carros = (List<Carro>) input.readObject();
                            System.out.println("Carros disponíveis:");
                            for (Carro carro : carros) {
                                System.out.println(carro);
                            }
                            break;
                        case 2:
                            System.out.print("Digite o id do carro que deseja alugar: ");
                            int idAluguel = scanner.nextInt();
                            scanner.nextLine(); // Consumir o \n deixado pelo nextInt()
                            output.writeObject("alugar carro " + idAluguel);
                            String mensagemAluguel = (String) input.readObject();
                            System.out.println(mensagemAluguel);
                            break;
                        case 3:
                            System.out.print("Digite o id do carro que deseja devolver: ");
                            int idDevolucao = scanner.nextInt();
                            scanner.nextLine(); // Consumir o \n deixado pelo nextInt()
                            output.writeObject("devolver carro " + idDevolucao);
                            String mensagemDevolucao = (String) input.readObject();
                            System.out.println(mensagemDevolucao);
                            break;
                        case 4:
                            output.writeObject("sair");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                } while (opcao != 4);
            }
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
