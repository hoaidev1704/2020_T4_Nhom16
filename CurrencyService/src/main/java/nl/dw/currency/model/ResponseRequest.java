package nl.dw.currency.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRequest<T extends Object> {
    public enum ResponseStatus {
        SUCCESS("success"), FAILED("error");
        String description;
        public String getDescription() {
            return this.description;
        }
        private ResponseStatus(String description) {
            this.description = description;
        }
    }

    private ResponseStatus status;
    private T data;
    private String message;
}
