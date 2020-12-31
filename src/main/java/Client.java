import java.io.DataInputStream;
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

            byte[] recvd_preamble;
            byte[] recvd_payload;

            String nxtLnOut;
            String messageIn;

            Scanner scanner = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            MessageListener listener = new MessageListener(in);


            while (true) {
                recvd_preamble = new byte[2];
                in.read(recvd_preamble);

                recvd_payload = new byte[(int) recvd_preamble[1]];
                in.read(recvd_payload);

                messageIn = new String(recvd_payload);
                System.out.println(messageIn);

                if (messageIn.equals("/loginsuc")) {
                    break;
                }
                if (scanner.hasNextLine()) {
                    nxtLnOut = scanner.nextLine();
                    payload = nxtLnOut.getBytes(StandardCharsets.UTF_8);

                    preamble = new byte[] {(byte) 0x00, (byte)payload.length};
                    out.write(preamble, 0, preamble.length);
                    out.flush();

                    out.write(payload);
                    out.flush();
                }
            }

//            listener.start();
            while (true) {
                nxtLnOut = scanner.nextLine();
                payload = nxtLnOut.getBytes(StandardCharsets.UTF_8);

                preamble = new byte[] {(byte) 0x01, (byte)payload.length};
                out.write(preamble, 0, preamble.length);
                out.flush();

                out.write(payload);
                out.flush();
            }

        }
    }
}
//                    out.write(payload);

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