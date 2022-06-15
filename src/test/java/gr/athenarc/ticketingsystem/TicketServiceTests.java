package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.domain.User;
import gr.athenarc.ticketingsystem.service.TicketService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static gr.athenarc.ticketingsystem.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureDataJpa
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceTests.class);
    private static final String TICKET_ID = "test";

    @Autowired
    private TicketService ticketService;

    @Test
    @Order(1)
    void addTicket() {
        Mono<Ticket> ticketMono = ticketService.add(TestHelper.createTestTicket(TICKET_ID));
        StepVerifier
                .create(ticketMono.flux())
                .assertNext(ticket -> {
                    assertNotNull(ticket);
                    assertNotNull(ticket.getCreated());
                    assertNotNull(ticket.getUpdated());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @Order(2)
    void updateTicket() {
        Ticket ticket = ticketService.get(TICKET_ID).block();
        assert ticket != null;
        ticket.setComments(new ArrayList<>());
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setFrom(createTestUser());
        comment.setText("Test comment that should not pass");
        ticket.getComments().add(comment);
        Mono<Ticket> ticketMono = ticketService.update(ticket.getId(), ticket);

        StepVerifier
                .create(ticketMono.flux())
                .assertNext(t -> {
                    assertEquals(0, t.getComments().size());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @Order(3)
    void addComments() {
        String ticketId = null;
        Ticket t = ticketService.getTicketRepository().findAllByNameRegex(TEST_TICKET_NAME).blockFirst();
        if (t != null && t.getId() != null) {
            ticketId = t.getId();
        }
        for (int i = 0; i < 60; i++) {
            User user = new User("User" + i % 6 + 1, "User" + i % 6 + 1, String.format("user%s@mail.test", i % 6 + 1));
            Comment comment = new Comment();
            comment.setFrom(user);
            comment.setText(String.format("This is comment #%s", i + 1));
            ticketService.addComment(ticketId, comment).block();
            logger.info("comment added: {}", comment);
        }
        Mono<Ticket> ticketMono = ticketService.get(ticketId);
        StepVerifier
                .create(ticketMono.flux())
                .assertNext(ticket -> {
                    assertEquals(60, ticket.getComments().size(), "Ticket has 60 comments.");
                })
                .expectComplete()
                .verify();
    }

    @Test
    @Order(4)
    void createMultipleTickets() {
        Ticket ticket = ticketService.get(TICKET_ID).blockOptional().orElse(createTestTicket());
        ticket.setId(null);
        String ticketName = "REMOVE ME ";
        for (int i = 0; i < 10000; i++) {
            Ticket t = new Ticket(ticket);
            t.setName(ticketName + i);
            ticketService.add(t).block();
        }
    }

    @Test
    @Order(5)
    void removeTickets() {
        Flux<Ticket> tickets = ticketService.getAllByName("REMOVE ME ");
        tickets.collectList().blockOptional().orElse(new ArrayList<>()).forEach(ticket -> {
            logger.info("removing ticket with id: " + ticket.getId());
            ticketService.delete(ticket.getId());
        });
    }

    @Test
    @Order(6)
    void deleteTestTicket() {
        ticketService.delete(TICKET_ID);
    }


}
