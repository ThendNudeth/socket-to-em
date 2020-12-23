import java.util.Scanner;

public class MessageListener extends Thread{
    private Scanner in;

    public MessageListener(Scanner in) {
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            String nxtLn = in.nextLine();
            if (nxtLn.startsWith("/quit")) {
                return;
            } else {
                System.out.println(nxtLn);
            }

        }
    }
}
