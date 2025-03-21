package co.za.bsg.domain.model.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Success Response", name = "SuccessResponse")
public class SuccessResponse {

    @Schema(
            title = "The status code representing the success",
            example = "200"
    )
    private String statusCode;

    @Schema(
            title = "A message providing context about the success of the post request",
            example = "Successfully added a team member"
    )
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public SuccessResponse setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SuccessResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
