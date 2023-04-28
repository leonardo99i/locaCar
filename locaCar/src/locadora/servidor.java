package locadora;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class servidor {

    private static List<Carro> carros;

    public static void main(String[] args) {
        carros = new ArrayList<>();
        carros.add(new Carro(1, "Chevrolet", "Onix", true));
        carros.add(new Carro(2, "Fiat", "Argo", true));
        carros.add(new Carro(3, "VolksWagen", "Virtus", true));
        carros.add(new Carro(4, "Ford", "Ka", true));
        carros.add(new Carro(5, "Jeep", "Renegade", true));

        try (ServerSocket serverSocket = new ServerSocket(300)) {
            System.out.println("Servidor iniciado na porta 300.");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClienteHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClienteHandler implements Runnable {

        private Socket socket;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                while (true) {
                    Object obj = input.readObject();
                    if (obj instanceof String) {
                        String comando = (String) obj;
                        switch (comando) {
                            case "listar carros":
                                output.writeObject(carros);
                                break;
                            case "alugar carro 1":
                                if (carros.get(0).isDisponivel()) {
                                    carros.get(0).setDisponivel(false);
                                    output.writeObject("Carro alugado com sucesso.");
                                } else {
                                    output.writeObject("Carro indisponível para locação.");
                                }
                                break;
                            case "devolver carro 1":
                                carros.get(0).setDisponivel(true);
                                output.writeObject("Carro devolvido com sucesso.");
                                break;
                            // Adicione mais casos de acordo com sua necessidade
                            default:
                                output.writeObject("Comando inválido.");
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}