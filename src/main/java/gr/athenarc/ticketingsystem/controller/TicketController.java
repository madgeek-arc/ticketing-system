package gr.athenarc.ticketingsystem.controller;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @PutMapping("{id}")
    public Mono<Ticket> updateTicket(@PathVariable("id") String id, @RequestBody Ticket ticket) {
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

    @GetMapping("name")
//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Ticket> search(@RequestParam(value = "name", defaultValue = "") String name) {
        return ticketService.getAllByName(name);
    }

    @PostMapping("{id}/comments")
    public Mono<Ticket> addComment(@PathVariable String id, @RequestBody Comment comment) {
        return ticketService.addComment(id, comment);
    }
}
