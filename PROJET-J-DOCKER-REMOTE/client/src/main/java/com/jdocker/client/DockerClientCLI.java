package com.jdocker.client;

import com.jdocker.protocol.Request;
import com.jdocker.protocol.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class DockerClientCLI {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 9090);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            ObjectMapper mapper = new ObjectMapper();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Client connecté au serveur Docker");

            while (true) {
                System.out.print("docker> ");
                String cmd = scanner.nextLine();

                Request req = new Request();

                // Commandes simples
                if (cmd.equals("images")) {
                    req.setAction("LIST_IMAGES");
                    req.setData(Map.of());

                } else if (cmd.startsWith("pull ")) {
                    req.setAction("PULL_IMAGE");
                    req.setData(Map.of("name", cmd.split(" ")[1]));

                } else if (cmd.startsWith("run ")) {
                    req.setAction("RUN_CONTAINER");
                    req.setData(Map.of("name", cmd.split(" ")[1]));

                } else if (cmd.startsWith("stop ")) {
                    req.setAction("STOP_CONTAINER");
                    req.setData(Map.of("id", cmd.split(" ")[1]));

                } else if (cmd.startsWith("rm ")) {
                    req.setAction("REMOVE_CONTAINER");
                    req.setData(Map.of("id", cmd.split(" ")[1]));

                } else {
                    System.out.println("Commande inconnue");
                    continue;
                }

                // Envoyer la requête au serveur
                out.println(mapper.writeValueAsString(req));

                // Lire et afficher la réponse
                Response res = mapper.readValue(in.readLine(), Response.class);
                System.out.println(res);
            }

        } catch (Exception e) {
            System.out.println("Impossible de se connecter au serveur");
            e.printStackTrace();
        }
    }
}
