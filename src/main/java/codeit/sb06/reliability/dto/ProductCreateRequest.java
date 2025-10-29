package codeit.sb06.reliability.dto;

public record ProductCreateRequest(
        String name,
        long price,
        int stock
) {
}
