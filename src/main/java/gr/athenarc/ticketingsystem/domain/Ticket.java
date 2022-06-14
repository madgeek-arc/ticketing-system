package gr.athenarc.ticketingsystem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Ticket {

    @Id
    private String id;
    private String name;
    private String assigner;
    private String assignee;
    private Date created;
    private Date updated;
    private String description;
    private String status;
    private String priority;
    private List<Comment> comments;

    public Ticket() {
        // no arg constructor
    }

    public Ticket(String id, String name, String assigner, String assignee, Date created, Date updated, String description, String status, String priority, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.assigner = assigner;
        this.assignee = assignee;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.comments = comments;
    }

    public Ticket(Ticket ticket) {
        // copy constructor
        this(ticket.getId(), ticket.getName(), ticket.getAssigner(), ticket.getAssignee(), ticket.getCreated(), ticket.getUpdated(), ticket.getDescription(), ticket.getStatus(), ticket.getPriority(), ticket.getComments());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssigner() {
        return assigner;
    }

    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
