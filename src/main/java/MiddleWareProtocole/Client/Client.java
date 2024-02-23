package MiddleWareProtocole.Client;

import java.io.*;
import java.net.*;

public class Client {
    private static final int MIDDLEWARE_PORT = 9876;

    public static void main(String[] args) {
        try {
            // Création d'une socket TCP pour communiquer avec le middleware
            Socket middlewareSocket = new Socket(InetAddress.getLocalHost(), MIDDLEWARE_PORT);

            // Envoi de la phrase au middleware via TCP
            String userSentence = "Bonjour, combien de voyelles ici ?";
            PrintWriter outToMiddleware = new PrintWriter(middlewareSocket.getOutputStream(), true);
            outToMiddleware.println(userSentence);

            // Réception de la réponse du middleware
            BufferedReader inFromMiddleware = new BufferedReader(new InputStreamReader(middlewareSocket.getInputStream()));
            String middlewareResponse = inFromMiddleware.readLine();

            // Affichage de la réponse du middleware
            System.out.println("Réponse du middleware: " + middlewareResponse);

            // Fermeture de la socket
            middlewareSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}