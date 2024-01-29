import java.util.Base64;

public class Encoding {

    public String encodeToBase64(String input)
    {
        String riot = "riot:";
        String authToken = input;
        String passwordToBeEncoded = riot + authToken;

        byte[] encodedBytes = Base64.getEncoder().encode(passwordToBeEncoded.getBytes());

        return new String(encodedBytes);
    }
}
