/*
 * Copyright 2021-2024 OpenAIRE AMKE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
