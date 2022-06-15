package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static gr.athenarc.ticketingsystem.TestHelper.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@AutoConfigureDataJpa
@ActiveProfiles("test")
class TicketingSystemApplicationTests {

    private static final String TEST_ID = "test";
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void repoSaveAndFindByName() {
        ticketRepository.save(TestHelper.createTestTicket(TEST_ID)).block();
        Flux<Ticket> ticketFlux = ticketRepository.findAllByNameRegex(TEST_TICKET_NAME);

        StepVerifier
                .create(ticketFlux)
                .assertNext(ticket -> {
                    assertNotNull(ticket.getId());
                    assertEquals("Check Name", TEST_TICKET_NAME, ticket.getName());
                    assertEquals("Check Assigner", createTestAssigner(), ticket.getAssigner());
                    assertEquals("Check Assignee", TEST_TICKET_ASSIGNEE, ticket.getAssignee());
                    assertNotNull(ticket.getAssigner());
                })
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void repoDelete() {
        ticketRepository.deleteById(TEST_ID).block();
        Flux<Ticket> ticketFlux = ticketRepository.findById(TEST_ID).flux();

        StepVerifier
                .create(ticketFlux)
                .expectNextCount(0)
                .verifyComplete();
    }
}
