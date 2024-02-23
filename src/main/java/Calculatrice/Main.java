package Calculatrice;

import Calculatrice.Client.Client;
import Calculatrice.Middleware.Middleware;
import Calculatrice.Serveur.ServerAddition;
import Calculatrice.Serveur.ServerDivision;
import Calculatrice.Serveur.ServerMultiplication;
import Calculatrice.Serveur.ServerSubtraction;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Démarrer les serveurs
        new Thread(() -> {
            try {
                ServerAddition.main(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                ServerSubtraction.main(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                ServerMultiplication.main(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                ServerDivision.main(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Attendre un court moment pour que les serveurs démarrent
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Démarrer le middleware et le client
        new Thread(() -> {
            try {
                Middleware.main(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // Simulation de l'envoi de données au middleware depuis le client
        new Thread(() -> {
            try {
                TestClient.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
