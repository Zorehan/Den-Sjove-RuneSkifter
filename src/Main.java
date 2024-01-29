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
import java.util.HashMap;
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

    private static HashMap<String, Integer> runeIdMap = new HashMap<>();

        public Main()
        {
            runeIdMap.put("Domination", 8100);
            runeIdMap.put("Electrocute", 8112);
            runeIdMap.put("Predator", 8124);
            runeIdMap.put("Dark Harvest", 8128);
            runeIdMap.put("Hail of Blades", 9923);
            runeIdMap.put("Cheap Shot", 8126);
            runeIdMap.put("Taste of Blood", 8139);
            runeIdMap.put("Sudden Impact", 8143);
            runeIdMap.put("Zombie Ward", 8136);
            runeIdMap.put("Ghost Poro", 8120);
            runeIdMap.put("Eye Ball Collection", 8138);
            runeIdMap.put("Treasure Hunter", 8135);
            runeIdMap.put("Ingenious Hunter", 8134);
            runeIdMap.put("Relentless Hunter", 8105);
            runeIdMap.put("Ultimate Hunter", 8106);
            runeIdMap.put("Inspiration", 8300);
            runeIdMap.put("Glacial Augment", 8351);
            runeIdMap.put("Unsealed Spellbook", 8360);
            runeIdMap.put("First Strike", 8369);
            runeIdMap.put("Hextech Flashtraption", 8306);
            runeIdMap.put("Magical Footwear", 8304);
            runeIdMap.put("Perfect Timing", 8313);
            runeIdMap.put("Future's Market", 8321);
            runeIdMap.put("Minion Dematerializer", 8316);
            runeIdMap.put("Biscuit Delivery", 8345);
            runeIdMap.put("Cosmic Insight", 8347);
            runeIdMap.put("Approach Velocity", 8410);
            runeIdMap.put("Time Warp Tonic", 8352);
            runeIdMap.put("Precision", 8000);
            runeIdMap.put("Press the Attack", 8005);
            runeIdMap.put("Lethal Tempo", 8008);
            runeIdMap.put("Fleet Footwork", 8021);
            runeIdMap.put("Conqueror", 8010);
            runeIdMap.put("Overheal", 9101);
            runeIdMap.put("Triumph", 9111);
            runeIdMap.put("Presence of Mind", 8009);
            runeIdMap.put("Legend: Alacrity", 9104);
            runeIdMap.put("Legend: Tenacity", 9105);
            runeIdMap.put("Legend: Bloodline", 9103);
            runeIdMap.put("Coup de Grace", 8014);
            runeIdMap.put("Cut Down", 8017);
            runeIdMap.put("Last Stand", 8299);
            runeIdMap.put("Resolve", 8400);
            runeIdMap.put("Grasp of the Undying", 8437);
            runeIdMap.put("Aftershock", 8439);
            runeIdMap.put("Guardian", 8465);
            runeIdMap.put("Demolish", 8446);
            runeIdMap.put("Font of Life", 8463);
            runeIdMap.put("Shield Bash", 8401);
            runeIdMap.put("Conditioning", 8429);
            runeIdMap.put("Second Wind", 8444);
            runeIdMap.put("Bone Plating", 8473);
            runeIdMap.put("Overgrowth", 8451);
            runeIdMap.put("Revitalize", 8453);
            runeIdMap.put("Unflinching", 8242);
            runeIdMap.put("Sorcery", 8200);
            runeIdMap.put("Summon Aery", 8214);
            runeIdMap.put("Arcane Comet", 8229);
            runeIdMap.put("Phase Rush", 8230);
            runeIdMap.put("Nullifying Orb", 8224);
            runeIdMap.put("Manaflow Band", 8226);
            runeIdMap.put("Nimbus Cloak", 8275);
            runeIdMap.put("Transcendence", 8210);
            runeIdMap.put("Celerity", 8234);
            runeIdMap.put("Absolute Focus", 8233);
            runeIdMap.put("Scorch", 8237);
            runeIdMap.put("Waterwalking", 8232);
            runeIdMap.put("Gathering Storm", 8236);
        }

    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        String ladosse = runCommand("wmic PROCESS WHERE name='LeagueClientUx.exe' GET commandline");
        String filePath = "filtreretruner.txt";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 1 to get information");
        System.out.println();
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
        else if (Integer.parseInt(userInput) == 3)
        {
            port = searchForPort(ladosse);
            authToken = searchForAuthToken(ladosse);
            decodedPassword = encoder.encodeToBase64(authToken);
            injectRunePage("Gerg", decodedPassword);
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


    public static void injectRunePage(String runepageName, String authentication) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        String apiBaseURL = "https://EUW1.api.riotgames.com";
        String endPoint = "/lol-perks/v1/pages";
        String fullurl = apiBaseURL + endPoint;

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, TrustManagerUtils.getTrustManagers(), null);

        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        String requestBody = "{" +
                "\"current\": true," +
                "\"name\": \"" + runepageName + "\"," +
                "\"primaryStyleId\": " + 8100 + "," +
                "\"selectedPerkIds\": [" +
                8112 + "," +
                8216 + "," +
                8138 + "," +
                8135 + "," +
                9101 + "," +
                8014 +
                "]," +
                "\"subStyleId\": " + 8000 +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:" + port +"/lol-perks/v1/pages"))
                .header("accept", "application/json")
                .header("Authorization", "Basic" + authentication)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }
    }




