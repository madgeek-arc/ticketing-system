package gr.athenarc.ticketingsystem.controller;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.domain.User;
import gr.athenarc.ticketingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("isAuthenticated()")
    public Mono<Ticket> addTicket(@RequestBody Ticket ticket) {
        ticket.setAssigner(User.of(SecurityContextHolder.getContext().getAuthentication()));
        return ticketService.add(ticket);
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public Mono<Ticket> updateTicket(@PathVariable("id") String id, @RequestBody Ticket ticket) {
        ticket.setAssigner(User.of(SecurityContextHolder.getContext().getAuthentication()));
        return ticketService.update(id, ticket);
    }

    @GetMapping("{id}")
    public Mono<Ticket> getTicket(@PathVariable String id) {
        return ticketService.get(id);
    }

    @GetMapping()
//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Ticket> search(@RequestParam(value = "status", defaultValue = "") String status,
                               @RequestParam(value = "priority", defaultValue = "") String priority,
                               @RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                               @RequestParam(value = "order", defaultValue = "asc") String order,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        Sort.Direction sortDirection = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, sortBy);
        return ticketService.getAll(status, priority, keyword, PageRequest.of(page, size, sort));
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public Mono<Ticket> addComment(@PathVariable String id, @RequestBody Comment comment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        comment.setFrom(User.of(auth));
        return ticketService.addComment(id, comment);
    }
}
