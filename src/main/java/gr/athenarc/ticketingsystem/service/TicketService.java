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

package gr.athenarc.ticketingsystem.service;

import gr.athenarc.ticketingsystem.domain.Comment;
import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Mono<Ticket> add(Ticket ticket) {
        Date now = new Date();
        ticket.setCreated(now);
        ticket.setUpdated(now);
        ticket.setComments(new ArrayList<>());
        return ticketRepository.save(ticket);
    }

    public Mono<Ticket> update(String id, Ticket ticket) {
        Ticket existing = ticketRepository.findById(id).blockOptional().orElseThrow(() -> new RuntimeException("Could not find resource with id: " + id));
        ticket.setComments(existing.getComments());
        return ticketRepository.save(ticket);
    }

    public void delete(String id) {
        ticketRepository.deleteById(id).block();
    }

    public Mono<Ticket> get(String id) {
        return ticketRepository.findById(id);
    }

    public Flux<Ticket> getAll(String status, String priority, String keyword, Pageable pageable) {
        return ticketRepository.search(status, priority, keyword, pageable);
    }

    public Flux<Ticket> getAllByName(String name) {
        return ticketRepository.findAllByNameRegex(name);
    }

    public Mono<Ticket> addComment(String ticketId, Comment comment) {
        comment.setDate(new Date());
        Ticket ticket = ticketRepository.findById(ticketId).blockOptional().orElseThrow(() -> new RuntimeException("Could not find resource with id: " + ticketId));
        if (ticket.getComments() == null) {
            ticket.setComments(new ArrayList<>());
        }
        ticket.getComments().add(comment);
        return ticketRepository.save(ticket);
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }
}
