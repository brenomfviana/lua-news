package br.edu.ufrn.brenov.luanews.model;

public class News {

    private String link;
    private String title;
    private String author;
    private String description;
    private String publishedDate;

    public News(String link, String title, String author, String description, String publishedDate) {
        this.link = link;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public String getLink() {
        return this.link;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPublishedDate() {
        return this.publishedDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "link='" + this.link + '\'' +
                ", title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", description='" + this.description + '\'' +
                ", publishedDate=" + this.publishedDate +
                '}';
    }
}
