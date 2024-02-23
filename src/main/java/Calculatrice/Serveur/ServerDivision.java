package Calculatrice.Serveur;

import java.net.*;
import java.io.*;

public class ServerDivision {
    public static void main(String[] args) throws IOException {
        int portNumber = 1237;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Serveur Divison en attente sur le port " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new DivisionHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écoute sur le port " + portNumber);
            System.exit(-1);
        }
    }
}

class DivisionHandler extends Thread {
    private Socket clientSocket;

    public DivisionHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine = in.readLine();
            String[] operands = inputLine.split(";");
            int result = Integer.parseInt(operands[0]) / Integer.parseInt(operands[1]);
            out.println(result);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
