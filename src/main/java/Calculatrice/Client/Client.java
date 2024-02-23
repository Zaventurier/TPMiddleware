package Calculatrice.Client;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 1233)) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String userInputLine;
            while (!(userInputLine = userInput.readLine()).equals("exit")) {
                out.println(userInputLine);
                System.out.println("Réponse du serveur : " + in.readLine());
            }

            userInput.close();
            in.close();
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
