import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static Encoding encoder = new Encoding();
    private static String port;
    private static String authToken;
    private static String decodedPassword;
    private static String currentRunePageId;

    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        String ladosse = runCommand("wmic PROCESS WHERE name='LeagueClientUx.exe' GET commandline");
        String filePath = "filtreretruner.txt";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 1 to get information");
        String userInput = scanner.nextLine();
        if(Integer.parseInt(userInput) == 1)
        {
            port = searchForPort(ladosse);
            authToken = searchForAuthToken(ladosse);
            decodedPassword = encoder.encodeToBase64(authToken);
            String test = searchForRunePage(decodedPassword);
            System.out.println(decodedPassword);
            System.out.println(test);
        }
        else if(Integer.parseInt(userInput) == 2)
        {
            port = searchForPort(ladosse);
            authToken = searchForAuthToken(ladosse);
            decodedPassword = encoder.encodeToBase64(authToken);
            deleteRunePage(decodedPassword, currentRunePageId);
        }
//xd
        }

    public static String searchForSummonerinfo() throws IOException, InterruptedException {
        String apiBaseURL = "https://EUW1.api.riotgames.com";
        String endPoint = "/lol-perks/v1/currentpage";
        String fullurl = apiBaseURL + endPoint;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://127"))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 OPR/106.0.0.0")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Charset","application/x-www-form-urlencoded; charset=UTF-8")
                .header("Origin", "https://developer.riotgames.com")
                .header("X-Riot-Token", "RGAPI-4c09c3dc-85f9-4533-8f76-abb01d9c084f")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String searchForRunePage(String authentication) throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        String apiBaseURL = "https://EUW1.api.riotgames.com";
        String endPoint = "/lol-perks/v1/currentpage";
        String fullurl = apiBaseURL + endPoint;

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, TrustManagerUtils.getTrustManagers(), null);

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port +"/lol-perks/v1/currentpage"))
                .header("accept", "application/json")
                .header("Authorization", "Basic " + authentication)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(extractRunePageID(response.body()));
        currentRunePageId = extractRunePageID(response.body());
        return response.body();
    }

    public static String runCommand(String command) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                output.append(line).append("\n");
            }
        }

        int exitCode;
        try
        {
            exitCode = process.waitFor();
        } catch(InterruptedException e)
        {
            throw new IOException("Command execution interrupted", e);
        }

        if(exitCode != 0)
        {
            throw new IOException("command exited with non-zero status:" + exitCode);
        }

        return output.toString();
    }

    public static String searchForPort(String tokenAnswer)
    {
        String port = "";

        Pattern pattern = Pattern.compile("--app-port=([0-9]*)");
        Matcher matcher = pattern.matcher(tokenAnswer);

        while(matcher.find())
        {
            String matchedGroup = matcher.group(1);
            port = matchedGroup;
            System.out.println(port);
            return port;
        }
        return port;
    }

    public static String searchForAuthToken(String tokenAnswer)
    {
        String authToken = "";

        Pattern pattern = Pattern.compile("--remoting-auth-token=([\\w-]*)");
        Matcher matcher = pattern.matcher(tokenAnswer);

        while(matcher.find())
        {
            String matchedGroup = matcher.group(1);
            authToken = matchedGroup;
            return authToken;
        }
        return authToken;
    }

    public static String extractIdandName(String jsonInput)
    {
        JSONArray jsonArray = new JSONArray(jsonInput);

        JSONArray filteredArray = new JSONArray();

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject originalObject = jsonArray.getJSONObject(i);
            int id = originalObject.getInt("id");
            String name = originalObject.getString("name");
            String key = originalObject.getString("key");

            JSONObject filteredObject = new JSONObject();
            filteredObject.put("id", id);
            filteredObject.put("key", key);
            filteredObject.put("name", name);

            filteredArray.put(filteredObject);
        }

        return filteredArray.toString();
    }

    public static void deleteRunePage(String authentication, String runePageID) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        String apiBaseURL = "https://EUW1.api.riotgames.com";
        String endPoint = "/lol-perks/v1/pages";
        String fullurl = apiBaseURL + endPoint;

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, TrustManagerUtils.getTrustManagers(), null);

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port +"/lol-perks/v1/pages"))
                .header("accept", "application/json")
                .header("Authorization", "Basic " + authentication)
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public static String extractRunePageID(String jsonInput)
    {

            JSONObject originalObject = new JSONObject(jsonInput);

            int id = originalObject.getInt("id");

            return String.valueOf(id);

    }
    }




