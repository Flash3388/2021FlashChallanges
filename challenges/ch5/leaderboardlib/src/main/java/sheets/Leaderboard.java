package sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import crypto.Scrambler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {

    private static final String NAMES_VALUE_RANGE = "passwords!A2:A6";
    private static final String SCORES_VALUE_RANGE = "passwords!C2:Z6";

    private static final String APPLICATION_NAME = "Flash discord bot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    private static final String SPREADSHEETID_FILE_PATH = "/spreadsheetid.txt.scrambled";
    private static final String CREDENTIALS_FILE_PATH = "/credentialsid.json.scrambled";

    private static final String LEADERBOARD_SPREADSHEET_ID;
    private static final String CREDENTIALS_DATA;

    static {
        Scrambler scrambler = new Scrambler();
        LEADERBOARD_SPREADSHEET_ID = scrambler.unscramble(loadStringFromFile(SPREADSHEETID_FILE_PATH));
        CREDENTIALS_DATA = scrambler.unscramble(loadStringFromFile(CREDENTIALS_FILE_PATH));
    }

    private final Sheets mService;

    public Leaderboard() throws GeneralSecurityException, IOException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        mService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, CREDENTIALS_DATA))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Map<String, Double> getLeaderboard() throws IOException {
        ValueRange namesResponse = mService.spreadsheets().values()
                .get(LEADERBOARD_SPREADSHEET_ID, NAMES_VALUE_RANGE)
                .execute();
        ValueRange valuesResponse = mService.spreadsheets().values()
                .get(LEADERBOARD_SPREADSHEET_ID, SCORES_VALUE_RANGE)
                .execute();

        List<List<Object>> names = namesResponse.getValues();
        if (names == null || names.isEmpty()) {
            throw new IOException("no data");
        }

        List<List<Object>> values = valuesResponse.getValues();
        if (values == null || values.isEmpty()) {
            throw new IOException("no data");
        }

        Map<String, Double> scores = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            List<Object> teamScores = values.get(i);
            double score = 0;
            for (Object teamScore : teamScores) {
                if (teamScore instanceof String) {
                    String str = (String)teamScore;
                    if (!str.isEmpty()) {
                        score += Double.parseDouble(str);
                    }
                } else if (teamScore instanceof Double) {
                    score += (double) teamScore;
                }
            }
            scores.put((String) names.get(i).get(0), score);
        }

        return scores;
    }

    private static Credential getCredentials(NetHttpTransport HTTP_TRANSPORT, String credentials) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new StringReader(credentials));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(8888)
                .build();
        return new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize("user");
    }

    private static String loadStringFromFile(String filePath) {
        try {
            Path file = new File(Leaderboard.class.getClassLoader()
                    .getResource(filePath)
                    .toURI())
                    .toPath();
            return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new Error(e);
        }
    }
}
