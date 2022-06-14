package gr.athenarc.ticketingsystem.domain;

import java.util.Date;

public class Comment {

    private String from;
    private String text;
    private Date date;

    public Comment() {
        // no arg constructor
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
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
