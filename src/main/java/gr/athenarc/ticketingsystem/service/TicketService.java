package gr.athenarc.ticketingsystem.service;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Mono<Ticket> add(Ticket ticket) {
        Date now = new Date();
        ticket.setCreated(now);
        ticket.setUpdated(now);
        ticket.setComments(new ArrayList<>());
        return ticketRepository.save(ticket);
    }

    public Mono<Ticket> update(String id, Ticket ticket) {
        Ticket existing = ticketRepository.findById(id).blockOptional().orElseThrow(() -> new RuntimeException("Could not find resource with id: " + id));
        ticket.setComments(existing.getComments());
        return ticketRepository.save(ticket);
    }

    public Mono<Ticket> get(String id) {
        return ticketRepository.findById(id);
    }

    public Flux<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    public Mono<Ticket> addComment(String ticketId, Comment comment) {
        comment.setDate(new Date());
        Ticket ticket = ticketRepository.findById(ticketId).blockOptional().orElseThrow(() -> new RuntimeException("Could not find resource with id: " + ticketId));
        if (ticket.getComments() == null) {
            ticket.setComments(new ArrayList<>());
        }
        ticket.getComments().add(comment);
        return ticketRepository.save(ticket);
    }
}
