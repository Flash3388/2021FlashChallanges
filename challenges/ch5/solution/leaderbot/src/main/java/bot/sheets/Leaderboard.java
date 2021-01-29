package bot.sheets;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {

    private static final String LEADERBOARD_SPREADSHEET_ID = "1Fvfill9UrVN6Jwkqq89NDYpzDJui1ztdP72w6qE9PkA";
    private static final String NAMES_VALUE_RANGE = "passwords!A2:A6";
    private static final String SCORES_VALUE_RANGE = "passwords!C2:Z6";

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public Map<String, Double> getLeaderboard() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange namesResponse = service.spreadsheets().values()
                .get(LEADERBOARD_SPREADSHEET_ID, NAMES_VALUE_RANGE)
                .execute();
        ValueRange valuesResponse = service.spreadsheets().values()
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

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Leaderboard.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

}
