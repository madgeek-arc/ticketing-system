package gr.athenarc.ticketingsystem.controller;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping()
    public Mono<Ticket> addTicket(@RequestBody Ticket ticket) {
        return ticketService.add(ticket);
    }

    @PutMapping()
    public Mono<Ticket> updateTicket(@RequestParam String id, @RequestBody Ticket ticket) {
        return ticketService.update(id, ticket);
    }

    @GetMapping("{id}")
    public Mono<Ticket> getTicket(@PathVariable String id) {
        return ticketService.get(id);
    }

    @GetMapping()
    public Flux<Ticket> search() {
        return ticketService.getAll();
    }

    @PostMapping("{id}/comments")
    public Mono<Ticket> aComment(@PathVariable String id, @RequestBody Comment comment) {
        return ticketService.addComment(id, comment);
    }
}
