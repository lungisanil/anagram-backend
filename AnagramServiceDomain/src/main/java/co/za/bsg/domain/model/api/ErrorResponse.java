package co.za.bsg.domain.model.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Error Response", name = "ErrorResponse")
public class ErrorResponse {

    @Schema(
            title = "The status code representing the error",
            example = "500"
    )
    private String statusCode;

    @Schema(
            title = "A message providing context about the causes of the error",
            example = "An error has occurred"
    )
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public ErrorResponse setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
