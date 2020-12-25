import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    String username;
    PrintWriter out;

    User(String username, OutputStream source) {
        this.username = username;
        this.out = new PrintWriter(source, true);

    }
}
