package App;

import DB.BookDB;
import Entity.Book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static DB.BookDB.scanner;

/**
 * Created by Yerchik on 23.06.2017.
 */
public class Menu {
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

            String operation = scanner.next();
            if (operation.equals("1")) {
                add(connection);
            }
            else if (operation.equals("2")) {
                remove(connection);
            }
            else if (operation.equals("3")) {
                edit(connection);
            }
            else if (operation.equals("4")) {
                all(connection);
            }
            else if (operation.equals("5")) {
                System.exit(0);
            }
            else System.out.println("Please select number from the menu.");

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

    public static void remove(Connection connection) throws SQLException, IOException {
        boolean a = true;
        do {
            System.out.println("Input book_name, that you want to remove:");
            String bookName = scanner.next();
            List<Book> books = BookDB.findByName(bookName, connection);
            if (BookDB.findByName(bookName, connection).size() == 0){
                System.out.println("we don't have such book.");
                all(connection);
                System.out.println(" 1 - Try again.\n 2 - Main menu.");
                String operation = scanner.next();
                if(operation.equals("1")) a = true;
                if(operation.equals("2")) a = false;
            }
            else if (books.size() == 1){
                System.out.print(books.get(0));
                BookDB.removeBook(books.get(0).getId(), connection);
                a = false;
                System.out.println(" was removed.");
            }
            else {
                int i = 1;
                System.out.println("We have few books with such name please choose one by typing a number of book:");
                for (Book book : BookDB.findByName(bookName, connection)) {
                    System.out.println("" + i + ". " + book);
                    i++;
                }
                int number = scanner.nextInt();
                if (number < i){
                    System.out.print(books.get(number - 1));
                    BookDB.removeBook(books.get(number - 1).getId(), connection);
                    a = false;
                    System.out.println(" was removed.");
                }
                else System.out.println("You've putted wrong number, try again.");
            }

        }while (a);
    }

    public static void edit(Connection connection) throws SQLException, IOException {
        boolean a = true;
        do {
            System.out.println("Input book_name, that you want to edit:");
            String bookName = scanner.next();
            List<Book> books = BookDB.findByName(bookName, connection);
            if (BookDB.findByName(bookName, connection).size() == 0){
                System.out.println("Sorry we don't have such book.");
                all(connection);
                System.out.println(" 1 - Try again.\n 2 - Main menu.");
                String operation = scanner.next();
                if(operation.equals("1")) a = true;
                if(operation.equals("2")) a = false;
            }
            else if (books.size() == 1){
                System.out.println("please enter new name of book.");
                String newBookName = scanner.next();
                System.out.print(books.get(0));
                BookDB.editBook(newBookName, books.get(0).getId(), connection);
                a = false;
                books.get(0).setBookName(newBookName);
                System.out.println(" was changed to: " + books.get(0));
            }
            else {
                int i = 1;
                System.out.println("We have few books with such name please choose one by typing a number of book:");
                for (Book book : BookDB.findByName(bookName, connection)) {
                    System.out.println("" + i + ". " + book);
                    i++;
                }
                int number = scanner.nextInt();
                if (number < i){
                    System.out.println("please enter new name of book.");
                    String newBookName = scanner.next();
                    System.out.print(books.get(number - 1));
                    BookDB.editBook(newBookName, books.get(number - 1).getId(), connection);
                    a = false;
                    books.get(number - 1).setBookName(newBookName);
                    System.out.println(" was changed to: " + books.get(number - 1));
                }
                else System.out.println("You've putted wrong number, try again.");
            }

        }while (a);
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
        BookDB.createBookTable(connection);

    }

}

