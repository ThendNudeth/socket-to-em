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
            } /*if () {

            } */
            else {
                System.out.println(nxtLn);
            }
        }
    }
}
