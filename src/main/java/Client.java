import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length !=1) {
            System.err.println("Ip pls!");

        } else try (Socket socket = new Socket(args[0], 6969)) {
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            MessageListener listener = new MessageListener(in);


            listener.start();
            while (true) {
                if (scanner.hasNextLine()) {
                    String nxtLn = scanner.nextLine();
                    out.println(nxtLn);
                    if(nxtLn.startsWith("/quit")) {
                        listener.join();
                        return;
                    }
                    System.out.println(listener.isAlive());
                }

            }
        }
    }
}
