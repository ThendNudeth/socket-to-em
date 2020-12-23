import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriter extends Thread{
    private Socket socket;
    private PrintWriter out;

    public ClientWriter(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            if (in.hasNextLine()) {
                out.println(in);
            }
        }
    }
}
