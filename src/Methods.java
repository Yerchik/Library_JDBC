import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Yerchik on 12.04.2017.
 */
public class Methods {
    public static void menu() throws SQLException {
        Connection connection = (Connection) DriverManager
                .getConnection(
                        "jdbc:mysql://localhost:3306/library?verifyServerCertificate=false&useSSL=true",
                        "root", "root");
        initializeDatabase(connection);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, User! Welcome to our Library!");
        boolean switcher = true;
        do {
            System.out
                    .println("Menu:\n 1 - add book;\n 2 - remove;\n 3 - edit book;\n 4 - all books;\n 5 - Exit\n Please select number from the menu");

            int operation = scanner.nextInt();
            if (operation == 1) {
                BookDB.addNewBook(connection);
            }
            if (operation == 2) {
                BookDB.removeBook(connection);
            }
            if (operation == 3) {
                BookDB.editBook(connection);
            }
            if (operation == 4) {
                BookDB.getAll(connection);
                System.out.println("enter any key.");
                scanner.next();
            }
            if (operation == 5) {
                System.exit(0);
            }
            if (operation != 1 && operation != 2 && operation != 3 && operation != 4 && operation != 5)
                System.out.println("vedit nomer z menu");

        } while (switcher);
        connection.close();
    }

    public static void initializeDatabase(Connection connection)
            throws SQLException {
        BookDB.createBooktTable(connection);

    }

}
