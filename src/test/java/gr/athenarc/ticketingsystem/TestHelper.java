package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Ticket;
import gr.athenarc.ticketingsystem.domain.User;

public class TestHelper {

    public static final String TEST_TICKET_NAME = "Test";
    public static final String TEST_TICKET_ASSIGNER = "assigner@test.ts";
    public static final String TEST_TICKET_ASSIGNEE = "assignee@test.ts";

    public static Ticket createTestTicket(String id) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setName(TEST_TICKET_NAME);
        ticket.setAssigner(createTestAssigner());
        ticket.setAssignee(TEST_TICKET_ASSIGNEE);
        ticket.setDescription("This is a test ticket. Should you find it, ignore or delete it.");
        ticket.setPriority("low");
        ticket.setStatus("test");
        return ticket;
    }

    public static Ticket createTestTicket() {
        Ticket ticket = new Ticket();
        ticket.setName(TEST_TICKET_NAME);
        ticket.setAssigner(createTestAssigner());
        ticket.setAssignee(TEST_TICKET_ASSIGNEE);
        ticket.setDescription("This is a test ticket. Should you find it, ignore or delete it.");
        ticket.setPriority("low");
        ticket.setStatus("test");
        return ticket;
    }

    public static Ticket createManyTickets() {
        Ticket ticket = new Ticket();
        ticket.setName("Test");
        ticket.setAssignee("assignee@test.ts");
        ticket.setAssigner(createTestAssigner());
        ticket.setDescription("This is a test ticket. Should you find it, ignore or delete it.");
        ticket.setPriority("low");
        ticket.setStatus("pending");
        return ticket;
    }

    public static User createTestUser() {
        return new User("Name", "Surname", "name-surname@mail.test");
    }

    public static User createTestAssigner() {
        return new User("Test", "Assigner", TEST_TICKET_ASSIGNER);
    }

    private TestHelper() {}
}
