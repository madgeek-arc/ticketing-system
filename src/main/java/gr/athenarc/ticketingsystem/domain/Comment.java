package gr.athenarc.ticketingsystem.domain;

import java.util.Date;

public class Comment {

    private User from;
    private String text;
    private Date date;

    public Comment() {
        // no arg constructor
    }

    public Comment(User from, String text, Date date) {
        this.from = from;
        this.text = text;
        this.date = date;
    }

    public Comment(Comment comment) {
        this(comment.getFrom(), comment.getText(), Date.from(comment.getDate().toInstant()));
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
