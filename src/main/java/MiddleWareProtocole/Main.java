package MiddleWareProtocole;

import MiddleWareProtocole.Client.Client;
import MiddleWareProtocole.Middleware.Middleware;
import MiddleWareProtocole.Server.Serveur;

public class Main {
    public static void main(String[] args) {
        // Lancer le serveur
        Thread serveurThread = new Thread(() -> Serveur.main(null));
        serveurThread.start();

        // Attente pour laisser le serveur démarrer
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Lancer le middleware
        Thread middlewareThread = new Thread(() -> Middleware.main(null));
        middlewareThread.start();

        // Lancer le client
        Thread clientThread = new Thread(() -> Client.main(null));
        clientThread.start();
    }
}
