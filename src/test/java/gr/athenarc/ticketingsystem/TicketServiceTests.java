package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.service.TicketService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Date;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static gr.athenarc.ticketingsystem.TestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceTests.class);
    private static final String TICKET_ID = "test";

    @Autowired
    private TicketService ticketService;

    @Test
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
    void updateTicket() {
        Ticket ticket = ticketService.get(TICKET_ID).blockOptional().orElse(createTestTicket(TICKET_ID));
        ticket.setComments(new ArrayList<>());
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setFrom("me");
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
    void addComments() {
        String ticketId = null;
        Ticket t = ticketService.getTicketRepository().findAllByName(TEST_TICKET_NAME).blockFirst();
        if (t != null && t.getId() != null) {
            ticketId = t.getId();
        }
        for (int i = 0; i < 60; i++) {
            Comment comment = new Comment();
            comment.setFrom("User" + i%6+1);
            comment.setText(String.format("This is comment #%s", i+1));
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
    void deleteTestTicket() {
        ticketService.delete(TICKET_ID);
    }


}
