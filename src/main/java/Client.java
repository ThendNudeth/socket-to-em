import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        if (args.length !=1) {
            System.err.println("Ip pls!");
        } else try (Socket socket = new Socket(args[0], 6969)) {
            System.out.println("Type sum shit.");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println("Server sends back: " +in.nextLine());
            }
        }
    }
}
