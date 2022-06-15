package gr.athenarc.ticketingsystem.repository;

import gr.athenarc.ticketingsystem.domain.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {

    Flux<Ticket> findAllByNameRegex(String name);

    @Query(value = "{ $and: [{'status' : {$regex: ?0}, 'priority' :  {$regex: ?1}}, { $or: [ {'name' : {$regex: ?2}}, {'description' : {$regex: ?2}} ] } ]}")
    Flux<Ticket> search(String status, String priority, String keyword, Pageable pageable);

    Flux<Ticket> findAllByAssignee(Sort sort);
}
