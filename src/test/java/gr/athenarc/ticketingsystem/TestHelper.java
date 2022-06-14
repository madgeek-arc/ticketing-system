package gr.athenarc.ticketingsystem;

import gr.athenarc.ticketingsystem.domain.Ticket;

public class TestHelper {

    public static final String TEST_TICKET_NAME = "Test";
    public static final String TEST_TICKET_ASSIGNER = "assigner@test.ts";
    public static final String TEST_TICKET_ASSIGNEE = "assignee@test.ts";

    public static Ticket createTestTicket(String id) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setName(TEST_TICKET_NAME);
        ticket.setAssigner(TEST_TICKET_ASSIGNER);
        ticket.setAssignee(TEST_TICKET_ASSIGNEE);
        ticket.setDescription("This is a test ticket. Should you find it, ignore or delete it.");
        ticket.setPriority("low");
        ticket.setStatus("test");
        return ticket;
    }

    public static Ticket createTestTicket() {
        Ticket ticket = new Ticket();
        ticket.setName(TEST_TICKET_NAME);
        ticket.setAssigner(TEST_TICKET_ASSIGNER);
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
        ticket.setAssigner("assigner@test.ts");
        ticket.setDescription("This is a test ticket. Should you find it, ignore or delete it.");
        ticket.setPriority("low");
        ticket.setStatus("pending");
        return ticket;
    }

    private TestHelper() {}
}
