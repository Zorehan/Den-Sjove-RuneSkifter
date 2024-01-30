import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoding {

    public String encodeToBase64(String input)
    {
        String riot = "riot:";
        String authToken = input;
        String passwordToBeEncoded = riot + authToken;

        String encodedBytes = Base64.getEncoder().encodeToString(passwordToBeEncoded.getBytes(StandardCharsets.UTF_8)) ;

        return new String(encodedBytes);
    }
}
