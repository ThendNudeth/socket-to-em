import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Set<String> names = new HashSet<>();

    private static Set<PrintWriter> writers = new HashSet<>();

    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception{
        System.out.println("Server is running...");
        ExecutorService pool = Executors.newFixedThreadPool(5);
        try (ServerSocket listener = new ServerSocket(6969)) {
            while (true) {
                pool.execute(new ClientHandler(listener.accept()));
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;
        private Scanner in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    out.println("Please enter a username.");
                    username = in.nextLine();
                    if (username == null) {
                        return;
                    }
                    if (username.toLowerCase().equals("quit")||username.toLowerCase().equals("help")) {
                        out.println("Invalid username.");
//                        return;
                    } else {
                        synchronized (names) {
                        if (!username.isBlank() && !names.contains(username)) {
                            names.add(username);
                            System.out.println(username + " added.");
                            break;
                        }
                    }
                    }


                }

                out.println("Welcome, " + username + ".\n" +
                        "You are now in the global chatroom. Just type whatever messages you'd\n" +
                        "like to send.\n" +
                        "Type /help for a list of commands");
                for (PrintWriter writer : writers) {
                    writer.println("NOTIFICATION: " + username + " has joined");
                }
                writers.add(out);

                // Accept messages from this client and broadcast them.
                while (true) {
                    String input = in.nextLine();
                    if (input.toLowerCase().startsWith("/quit")) {
                        out.println("/quit");
                        return;
                    }
                    if (input.toLowerCase().startsWith("/help")) {
                        out.println("SERVER: -If you'd like to broadcast a message,\n" +
                                "simply type said message.\n" +
                                "-If you would like to leave the chatroom, type \"/quit\"\n" +
                                "-If you would like to send a private message, type \"/\", followed\n" +
                                "by the user's username, a space and then the message.\n" +
                                "For example: \"/bob hello!\" will send a message only to bob.");
                    }
                    if (input.startsWith("/")) {

                    }
                    else {
                        for (PrintWriter writer : writers) {
                            writer.println("MESSAGE " + username + ": " + input);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                if (username != null) {
                    System.out.println("NOTIFICATION: " + username + " is leaving.");
                    names.remove(username);
                    for (PrintWriter writer : writers) {
                        writer.println("NOTIFICATION: " + username + " has left");
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}


