package superfon.service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import superfon.auth.GoogleCredentials;
import superfon.util.PropertyReader;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


public class GoogleSheetService {

    private static final String APPLICATION_NAME = PropertyReader.getPropByKey("appName");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String RANGE = "Form responses 1!A2:F11";

    /**
     * Prints customers data:
     * https://docs.google.com/spreadsheets/d/17_wqS7l4CpSVqYofG2j8l5WKxXSkpRPqBZvJv3aZyTE
     */
    public List<List<Object>> getSheetData() {
        List<List<Object>> values = null;
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = PropertyReader.getPropByKey("sheet_id");

            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                    GoogleCredentials.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, RANGE)
                    .execute();
            values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                return values;
            }
        } catch (IOException | GeneralSecurityException exception) {
            exception.printStackTrace();
        }
        return values;
    }
}
