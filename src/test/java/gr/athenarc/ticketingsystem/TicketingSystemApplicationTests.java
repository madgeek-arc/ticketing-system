package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class TicketingSystemApplicationTests {

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void addAndFindByName() {
		ticketRepository.save(createTestTicket()).block();
		Flux<Ticket> ticketFlux = ticketRepository.findAllByName("Test");

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

	private Ticket createTestTicket() {
		Ticket ticket = new Ticket();
		ticket.setId("test");
		ticket.setName("Test");
		ticket.setAssignee("me@me.me");
		ticket.setAssigner("he@he.he");
		ticket.setDescription("This is a test ticket.");
		ticket.setPriority("low");
		ticket.setStatus("pending");
		return ticket;
	}
}
