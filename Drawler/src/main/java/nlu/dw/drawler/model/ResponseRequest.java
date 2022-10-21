package nlu.dw.drawler.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRequest {
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
    private Object data;
    private String message;
}
