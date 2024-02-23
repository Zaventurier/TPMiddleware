package Calculatrice;

import java.io.*;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1233)) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                // Demander à l'utilisateur de saisir le calcul
                System.out.print("Entrez un calcul (par exemple, '20/2') ou 'exit' pour quitter : ");
                String userInputLine = userInput.readLine();

                // Envoyer la saisie au middleware
                out.println(userInputLine);

                // Quitter si l'utilisateur a saisi 'exit'
                if (userInputLine.equalsIgnoreCase("exit")) {
                    break;
                }

                // Réception et affichage du résultat du middleware
                String result = in.readLine();
                System.out.println("Résultat reçu du middleware : " + result);
            }

            // Fermer les flux
            userInput.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
