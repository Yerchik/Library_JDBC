package DB;

import Entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yerchik on 23.06.2017.
 */
public class BookDB {
    public static Scanner scanner = new Scanner(System.in);
    private static java.sql.PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void createBooktTable(java.sql.Connection connection)
            throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("create table if not exists book(id int primary key auto_increment, author varchar(30) not null , name varchar(30) not null );");
        preparedStatement.execute();
    }

    public static void addNewBook(String author, String bookName, java.sql.Connection connection)
            throws SQLException {
        preparedStatement = connection
                .prepareStatement("insert into book(author, name ) VALUES (?,?)");
        preparedStatement.setString(1, author);
        preparedStatement.setString(2, bookName);
        preparedStatement.execute();

    }

    public static List<Book> getAll(java.sql.Connection connection)
            throws SQLException {
        List<Book> books = new ArrayList<>();
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from book ");
        resultSet = preparedStatement.executeQuery();
        int i = 1;
        while (resultSet.next()) {
            Book book = new Book(resultSet.getString("author"), resultSet.getString("name"));
            books.add(book);
        }
        return books;
    }

    public static List<Book> findByName(String bookName, java.sql.Connection connection) throws SQLException {
        List<Book> books = new ArrayList<>();
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from book WHERE name like ?");
        preparedStatement.setString(1, bookName);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Book book = new Book(resultSet.getString("author"), resultSet.getString("name"));
            book.setId(resultSet.getInt("id"));
            books.add(book);
        }
        return books;
    }

    public static void removeBook(int id, java.sql.Connection connection)
            throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("DELETE from book WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }


    public static void editBook(String newBookName, int id, Connection connection) throws SQLException {

        preparedStatement = (PreparedStatement) connection
                .prepareStatement("UPDATE book set name = ? WHERE id = ?");
        preparedStatement.setString(1, newBookName);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();

    }

    public static void getSameBook(String bookName, Connection connection) throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from book WHERE name like ?");
        preparedStatement.setString(1, bookName);
        resultSet = preparedStatement.executeQuery();
        Book book;
        int i = 0;
        while (resultSet.next()) {
            i++;
            book = new Book(resultSet.getString("author"), resultSet.getString("name"));
            System.out.println(i + ": " + book);
        }
    }

    public static void deleteSameBook(String bookName, int j, Connection connection) throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from book WHERE name like ?");
        preparedStatement.setString(1, bookName);
        resultSet = preparedStatement.executeQuery();
        Book book;
        int i = 0;
        while (resultSet.next()) {
            i++;
            book = new Book(resultSet.getString("author"), resultSet.getString("name"));
            book.setId(resultSet.getInt("id"));
            if (i == j) {
                System.out.print(book);
                preparedStatement = (PreparedStatement) connection
                        .prepareStatement("DELETE from book WHERE id = ?");
                preparedStatement.setInt(1, book.getId());
                preparedStatement.executeUpdate();
                System.out.println(" was removed.");
                System.out.println("enter any key");
            }
        }
    }

    public static void editSameBook(String bookName, int j, Connection connection) throws SQLException {
        preparedStatement = (PreparedStatement) connection
                .prepareStatement("select * from book WHERE name like ?");
        preparedStatement.setString(1, bookName);
        resultSet = preparedStatement.executeQuery();
        Book book;
        int i = 0;
        while (resultSet.next()) {
            i++;
            book = new Book(resultSet.getString("author"), resultSet.getString("name"));
            book.setId(resultSet.getInt("id"));
            if (i == j) {
                System.out.println("please enter new name of book.");
                String newBookName = scanner.next();
                System.out.print(book);
                preparedStatement = (PreparedStatement) connection
                        .prepareStatement("UPDATE book set name = ? WHERE id = ?");
                preparedStatement.setString(1, newBookName);
                preparedStatement.setInt(2, book.getId());
                preparedStatement.executeUpdate();
                book.setBookName(newBookName);
                System.out.println(" was changed to: " + book);
                System.out.println("enter any key");
            }
        }
    }

}
