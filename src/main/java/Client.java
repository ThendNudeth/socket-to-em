import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length !=1) {
            System.err.println("Ip pls!");

        } else try (Socket socket = new Socket(args[0], 6969)) {

            byte[] payload;
            byte[] preamble;
            boolean loggedIn = false;

            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            MessageListener listener = new MessageListener(in);



            while (true) {
                if (scanner.hasNextLine()) {
                    String nxtLn = scanner.nextLine();
                    payload = nxtLn.getBytes(StandardCharsets.UTF_8);

                    preamble = new byte[] {(byte) 0x00, (byte)payload.length};
                    out.write(preamble, 0, preamble.length);
                    out.flush();

                    out.write(payload);
                    out.flush();
                    loggedIn = listener.loggedIn;
                    System.out.println(loggedIn);
//                    out.write(payload);

                }

            }
            listener.start();
            while (true) {
                //                    if (nxtLn.startsWith("/quit")) {
//                        header = new byte[] {(byte)0x00};
//
//                        out.write(header, 0, header.length);
////                        listener.join();
//                        return;
//                    } else if (nxtLn.startsWith("/help")) {
//                        header = new byte[] {(byte)0x01};
//                        packet = new byte[header.length+ payload.length];
//
//                        System.arraycopy(header, 0, packet, 0, header.length);
//                        System.arraycopy(payload, 0, packet, header.length, payload.length);
//                    } else if (nxtLn.startsWith("/link")) {
//                        header = new byte[] {(byte)0x02};
//                        packet = new byte[header.length+ payload.length];
//
//                        System.arraycopy(header, 0, packet, 0, header.length);
//                        System.arraycopy(payload, 0, packet, header.length, payload.length);
//
//                    } else if (nxtLn.startsWith("/")) {
//                        header = new byte[] {(byte)0x03};
//                        packet = new byte[header.length+ payload.length];
//
//                        System.arraycopy(header, 0, packet, 0, header.length);
//                        System.arraycopy(payload, 0, packet, header.length, payload.length);
//
//                    } else {
//                        header = new byte[] {(byte)0x04};
//                        packet = new byte[header.length+ payload.length];
//
//                        System.arraycopy(header, 0, packet, 0, header.length);
//                        System.arraycopy(payload, 0, packet, header.length, payload.length);
//
//                        out.write(packet, 0, packet.length);
////                        out.flush();
//
//                        System.out.println("packet sent");
//
//                    }
            }
        }
    }
}
