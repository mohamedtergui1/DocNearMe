package ma.tr.docnearme.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private final String message;
    private final T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
