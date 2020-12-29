import java.util.Scanner;

public class MessageListener extends Thread{
    private Scanner in;
    private String nxtLn;
    public boolean loggedIn;
    public boolean responseRecvd;

    public MessageListener(Scanner in) {
        this.in = in;
    }

    @Override
    public void run() {
        loggedIn = false;
        while (true) {
            responseRecvd =  false;
            nxtLn = in.nextLine();
            if (nxtLn.startsWith("/quit")) {
                return;
            } if (nxtLn.startsWith("/loginsuc")) {
                loggedIn = true;
                System.out.println("Successfully logged in.");
            }
            else {
                System.out.println(nxtLn);
            }
            responseRecvd = true;
        }
    }
}
