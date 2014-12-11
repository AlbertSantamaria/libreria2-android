package edu.eetac.dsa.asantamaria.libreria2_android;

/** RRRRRRRRRRRRRRR
 * Created by sito on 10/12/14.
 */

public class Book {

    private int bookid;
    private String title;
    private String author;
    private String language;
    private int edition;
    private String editionDate;
    private String printingDate;
    private String publisher;

    public Book(){}

    public Book(int bookid,String title, String author, String language, int edition,
                String editionDate, String printingDate, String publisher){
        super();
        this.bookid =bookid;
        this.title = title;
        this.author = author;
        this.language = language;
        this.edition = edition;
        this.editionDate = editionDate;
        this.printingDate = printingDate;
        this.publisher = publisher;

    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(String editionDate) {
        this.editionDate = editionDate;
    }

    public String getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(String printingDate) {
        this.printingDate = printingDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    //@Override
    //public String toString(){
    //    return this.getTitle()+" "+this.getAuthor();
    //}

}