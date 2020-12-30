import java.util.Scanner;

public class MessageListener extends Thread{
    private Scanner in;
    private String nxtLn;

    public MessageListener(Scanner in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            nxtLn = in.nextLine();
            if (nxtLn.startsWith("/quit")) {
                return;
            } if (nxtLn.startsWith("/loginsuc")) {
                System.out.println("Successfully logged in.");
                break;
            }
            else {
                System.out.println(nxtLn);
            }
        }
    }
}
