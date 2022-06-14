package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static gr.athenarc.ticketingsystem.TestHelper.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class TicketingSystemApplicationTests {

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void repoSaveAndFindByName() {
		ticketRepository.save(TestHelper.createTestTicket()).block();
		Flux<Ticket> ticketFlux = ticketRepository.findAllByName("Test");

		StepVerifier
				.create(ticketFlux)
				.assertNext(ticket -> {
					assertNotNull(ticket.getId());
					assertEquals("Check Name", TEST_TICKET_NAME, ticket.getId());
					assertEquals("Check Assigner", TEST_TICKET_ASSIGNER , ticket.getAssigner());
					assertEquals("Check Assignee", TEST_TICKET_ASSIGNEE , ticket.getAssignee());
					assertNotNull(ticket.getAssigner());
				})
				.expectComplete()
				.verify();
	}

	@Test
	void repoDelete() {
		ticketRepository.deleteById("test").block();
		Flux<Ticket> ticketFlux = ticketRepository.findById("test").flux();

		StepVerifier
				.create(ticketFlux)
				.assertNext(ticket -> {
					assertEquals("Check ID", "test", ticket.getId());
					assertEquals("Check Assignee", "me@me.me" , ticket.getAssignee());
					assertNotNull(ticket.getAssigner());
				})
				.expectComplete()
				.verify();
	}
}
