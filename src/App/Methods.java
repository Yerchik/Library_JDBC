package App;

import DB.BookDB;
import Entity.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import static DB.BookDB.scanner;

/**
 * Created by Yerchik on 23.06.2017.
 */
public class Methods {
    public static void menu() throws SQLException, IOException {
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
                add(connection);
            }
            if (operation == 2) {
                remove(connection);
            }
            if (operation == 3) {
                BookDB.editBook(connection);
            }
            if (operation == 4) {
                all(connection);
            }
            if (operation == 5) {
                System.exit(0);
            }
            if (operation != 1 && operation != 2 && operation != 3 && operation != 4 && operation != 5)
                System.out.println("vedit nomer z menu");

        } while (switcher);
        connection.close();
    }

    public static void add(Connection connection) throws SQLException, IOException {
        System.out.println("Input author_name:");
        String author = scanner.next();
        System.out.println("Input book_name: ");
        String bookName = scanner.next();
        BookDB.addNewBook(author, bookName, connection);
        System.out.println(new Book(author, bookName) + " was added.");
        System.out.println("Pres any button:");
        System.in.read();
    }

    public static void remove(Connection connection){

    }

    public static void all(Connection connection) throws IOException, SQLException {
        int i = 1;
        System.out.println("All books:");
        for (Book book : BookDB.getAll(connection)) {
            System.out.println("" + i + ". " + book);
            i++;
        }
        System.out.println("Pres any button:");
        System.in.read();
    }

    public static void initializeDatabase(Connection connection)
            throws SQLException {
        BookDB.createBooktTable(connection);

    }

}

