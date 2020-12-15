import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception{
        try (ServerSocket listener = new ServerSocket(6969)) {
            System.out.println("Server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(5);
            while (true) {
                pool.execute(new Doer(listener.accept()));
            }
        }
    }

    private static class Doer implements Runnable {
        private Socket socket;

        public Doer(Socket socket) {
            this.socket = socket;
        }


        @Override
        public void run() {
            System.out.println("Connected on: " + socket);
            try {
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                while (in.hasNextLine()) {
                    String clientIn = in.nextLine();
                    System.out.println("Client typed: " + clientIn);
                    out.println("I am the server and I say: " + clientIn);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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


