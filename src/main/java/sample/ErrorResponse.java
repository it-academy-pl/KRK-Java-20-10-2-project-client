package sample;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ErrorResponse {
    private int status;
    private String error;
    private String message;
}
