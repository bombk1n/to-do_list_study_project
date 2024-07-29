package dto;

public record TicketFilter(int limit,int offset,String name, String description, Boolean is_completed) {
}
