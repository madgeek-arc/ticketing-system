package gr.athenarc.ticketingsystem.repository;

import gr.athenarc.ticketingsystem.domain.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {

    Flux<Ticket> findAllByName(String name);

    Flux<Ticket> findAllByAssignee(Sort sort);
}
