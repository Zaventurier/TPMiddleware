package MiddleWareProtocole.Middleware;

import java.net.*;
import java.io.*;

public class Middleware {
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try {
            // Création d'une socket UDP pour communiquer avec le serveur
            DatagramSocket serverSocket = new DatagramSocket();

            // Adresse et port du serveur
            InetAddress serverAddress = InetAddress.getLocalHost();
            int serverPort = 1234;

            // Envoi de la phrase au serveur via UDP
            String userSentence = "Bonjour, combien de voyelles ici ?";
            byte[] sendData = userSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            serverSocket.send(sendPacket);

            // Réception de la réponse du serveur
            byte[] receiveData = new byte[256];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Affichage de la réponse du serveur
            System.out.println("Réponse du serveur: " + serverResponse);

            // Fermeture de la socket
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}