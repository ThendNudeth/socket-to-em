import java.io.PrintWriter;
import java.util.List;

public class User {
    String username;
    PrintWriter out;
    List<String> channels;

    User(String username, PrintWriter source) {
        this.username = username;
        this.out = source;

    }
}
