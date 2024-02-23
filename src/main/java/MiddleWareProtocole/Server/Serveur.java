package MiddleWareProtocole.Server;

import java.net.*;
import java.io.*;

public class Serveur {
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);

            while (true) {
                byte[] receiveData = new byte[256];

                // Réception de la phrase du client via UDP
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String clientSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Traitement de la phrase pour compter les voyelles
                int vowelCount = countVowels(clientSentence);

                // Envoi de la réponse au middleware
                InetAddress middlewareAddress = receivePacket.getAddress();
                int middlewarePort = receivePacket.getPort();
                String response = "Nombre de voyelles: " + vowelCount;
                byte[] sendData = response.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, middlewareAddress, middlewarePort);
                serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countVowels(String str) {
        // Implémentation pour compter les voyelles dans la phrase
        int count = 0;
        for (char c : str.toCharArray()) {
            if ("aeiouAEIOU".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
}