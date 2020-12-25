import java.io.PrintWriter;

public class User {
    String username;
    PrintWriter out;

    User(String username, PrintWriter source) {
        this.username = username;
        this.out = source;

    }
}
