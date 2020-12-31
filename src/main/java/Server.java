import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Set<String> names = new HashSet<>();

//    private static Set<PrintWriter> writers = new HashSet<>();
    private static Set<User> users = new HashSet<>();

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
        private DataOutputStream out;
        private User user;
        private String serverMessage;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            try {
//                in = new Scanner(socket.getInputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                byte[] recvd_preamble;
                byte[] recvd_payload;
                byte[] preamble;
                byte[] payload;
                String input;
                boolean invalidUsrName=false;


                while (true) {
                    serverMessage = "";
                    preamble = new byte[] {(byte) 0x00, 0};
                    if (invalidUsrName) {
                        serverMessage = "Invalid username.\n";
                        preamble = new byte[] {(byte) 0x01, 0};
                    }
                    invalidUsrName=false;
                    serverMessage = serverMessage+"Please enter a username.";
                    payload = serverMessage.getBytes(StandardCharsets.UTF_8);

                    preamble[1]=(byte) payload.length;
                    out.write(preamble, 0, preamble.length);
                    out.flush();

                    out.write(payload);
                    out.flush();



                    recvd_preamble = new byte[2];
                    in.read(recvd_preamble);

                    if (recvd_preamble[0]!=(byte)0x00) {
//                        out.println("Wrong input type. Packets likely out of order.");
                        System.out.println(socket +" sent bad data. Server synch likely out of order.");
                    } else {
                        System.out.println("packet received");
                        recvd_payload = new byte[(int)recvd_preamble[1]];

                        in.read(recvd_payload);
                        input = new String(recvd_payload);
                        username = input;
                        if (username.equals("")) {
                            continue;
                        }
                        if (username.toLowerCase().startsWith("quit") || username.toLowerCase().startsWith("help")
                                || username.toLowerCase().startsWith("link") || username.contains(" ")) {
                            invalidUsrName=true;
                            continue;
                        } else {
                            synchronized (names) {
                                if (!username.isBlank() && !names.contains(username)) {
                                    names.add(username);
                                    System.out.println(username + " added.");
                                    System.out.println(input);
//                                    out.println("/loginsuc");
                                    break;
                                }
                            }
                        }
                    }
                }

//                out.println("Welcome, " + username + ".\n" +
//                        "You are now in the global chatroom. Just type whatever messages you'd\n" +
//                        "like to send.\n" +
//                        "Type /help for a list of commands");
//
//                for (User user : users) {
//                    user.out.println("NOTIFICATION: " + username + " has joined");
//                }
//                user = new User(username, out);
//                users.add(user);
//
//                while (true) {
////                    if (in.)
//                    recvd_preamble = new byte[2];
//                    in.read(recvd_preamble);
//
//                    if (recvd_preamble[0]!=(byte) 0x01) {
//                        out.println("Wrong input type. Packets likely out of order.");
//                        System.out.println(socket +" sent bad data. Server synch likely out of order.");
//                    } else {
//                        recvd_payload = new byte[recvd_preamble[1]];
//                        in.read(recvd_payload);
//
//                        input = new String(recvd_payload);
////                        System.out.println(input);
//                        for (User user : users) {
//                            user.out.println("MESSAGE " + username + ": " + input);
//                        }
//                    }
//                }


                // Accept messages from this client and broadcast them.
//                while (true) {
//                    String input = in.nextLine();
//                    if (input.toLowerCase().startsWith("/quit")) {
//                        out.println("/quit");
//                        return;
//                    }
//                    if (input.toLowerCase().startsWith("/help")) {
//                        out.println("SERVER: -If you'd like to broadcast a message,\n" +
//                                "simply type said message.\n" +
//                                "-If you would like to leave the chatroom, type \"/quit\"\n" +
//                                "-If you would like to send a private message, type \"/\", followed\n" +
//                                "by the user's username, a space and then the message.\n" +
//                                "For example: \"/bob hello!\" will send a message only to bob.");
//                    }
//                    if (input.startsWith("/link")) {
//                        String recipient = input.substring(input.indexOf(" ")+1);
//                        for (User user : users) {
////                            if (user.username.equals(recipient)) {
////                                user.out.println("LINK REQUEST FROM " + username);
////                                break;
////                            }
//                        }
//
//                    }
//                    if (input.startsWith("/")) {
//                        String recipient = input.substring(1, input.indexOf(" "));
//                        for (User user : users) {
//                            if (user.username.equals(recipient)) {
//                                user.out.println("WHISPER " + username + ": " + input);
//                                break;
//                            }
//                        }
//
//                    }
//                    else {
//                        for (User user : users) {
//                            user.out.println("MESSAGE " + username + ": " + input);
//                        }
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    users.remove(user);
                }

                if (username != null) {
                    System.out.println("NOTIFICATION: " + username + " is leaving.");
                    names.remove(username);

                    for (User user : users) {
                        user.out.println("NOTIFICATION: " + username + " has left");
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


