package Calculatrice.Middleware;

import java.net.*;
import java.io.*;

public class Middleware {
    public static void main(String[] args) throws IOException {
        int additionPort = 1234;
        int subtractionPort = 1235;
        int multiplicationPort = 1236;
        int divisionPort = 1237;

        try (ServerSocket serverSocket = new ServerSocket(1233)) {
            System.out.println("Middleware en attente sur le port 1233");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                //Je récupère les informations du client
                String inputLine = in.readLine();

                // Utilisation d'une expression régulière pour capturer les opérandes et l'opérateur
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(-?\\d+)\\s*([-+*/])\\s*(-?\\d+)");
                java.util.regex.Matcher matcher = pattern.matcher(inputLine);

                if (matcher.matches()) {
                    int operand1 = Integer.parseInt(matcher.group(1));
                    String operator = matcher.group(2);
                    int operand2 = Integer.parseInt(matcher.group(3));

                    System.out.println(operand1);
                    System.out.println(operator);
                    System.out.println(operand2);

                    int result = 0;

                    switch (operator) {
                        case "+":
                            result = communicateWithServer(new String[]{String.valueOf(operand1), String.valueOf(operand2)}, additionPort);
                            break;
                        case "-":
                            result = communicateWithServer(new String[]{String.valueOf(operand1), String.valueOf(operand2)}, subtractionPort);
                            break;
                        case "*":
                            result = communicateWithServer(new String[]{String.valueOf(operand1), String.valueOf(operand2)}, multiplicationPort);
                            break;
                        case "/":
                            result = communicateWithServer(new String[]{String.valueOf(operand1), String.valueOf(operand2)}, divisionPort);
                            break;
                        default:
                            System.err.println("Opération non reconnue");
                            break;
                    }

                    out.println(result);
                } else {
                    System.err.println("Format d'opération invalide");
                    out.println("Format d'opération invalide");
                }

                //String[] operands = inputLine.split("[\\+\\-\\*/]");
                /*System.out.println("Middleware - message du client : " + inputLine.indexOf("-"));
                System.out.println(operands);

                int result = 0;
                if(inputLine.indexOf("+") > 0){
                    System.out.println("+");
                    result = communicateWithServer(operands, additionPort);
                    out.println(result);
                    break;
                } else if (inputLine.indexOf("-") > 0 ) {
                    System.out.println("-");
                    System.out.println(subtractionPort);
                    result = communicateWithServer(operands, subtractionPort);
                    out.println(result);
                    break;
                } else if (inputLine.indexOf("*") > 0) {
                    System.out.println("*");
                    result = communicateWithServer(operands, multiplicationPort);
                    out.println(result);
                    break;
                } else if (inputLine.indexOf("/") > 0) {
                    System.out.println("/");
                    result = communicateWithServer(operands, divisionPort);
                    out.println(result);
                    break;
                }
                else {
                    System.out.println("Opérateur non reconnue !");
                }*/

                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écoute sur le port 1233");
            System.exit(-1);
        }
    }

    private static int communicateWithServer(String[] operands, int port) throws IOException {
        try (Socket serverSocket = new Socket("localhost", port)) {
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            out.println(String.join(";", operands));

            int result = Integer.parseInt(in.readLine());

            out.close();
            in.close();

            return result;
        }
    }
}
