/**
 * Created by Yerchik on 12.04.2017.
 */
public class Book {
    private int id;
    private String author;
    private String bookName;

    public Book() {
    }

    public Book(String author, String bookName) {
        this.author = author;
        this.bookName = bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "" + author + " \"" + bookName + "\"";
    }
}
