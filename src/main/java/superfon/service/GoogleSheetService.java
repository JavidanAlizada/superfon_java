package superfon.service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import superfon.auth.GoogleCredentials;
import superfon.util.PropertyReader;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;


public class GoogleSheetService {

    private static final String APPLICATION_NAME = PropertyReader.getPropByKey("appName");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String RANGE = PropertyReader.getPropByKey("range");
    private static final String SHEET_ID = PropertyReader.getPropByKey("sheet_id");
    private static Sheets sheets = createSheetsService();


    private static Sheets createSheetsService() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredentials.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<List<Object>> getSheetData() {
        List<List<Object>> values = null;
        try {
            if (sheets != null) {
                ValueRange response = sheets.spreadsheets().values()
                        .get(SHEET_ID, RANGE)
                        .execute();

                values = response.getValues();
                if (values == null || values.isEmpty()) {
                    System.out.println("No data found.");
                } else {
                    return values;
                }
            } else
                return null;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return values;
    }

//    Unused
//    public boolean deleteRows() {
//        try {
//            if (sheets != null) {
//                ClearValuesRequest requestBody = new ClearValuesRequest();
//                Sheets.Spreadsheets.Values.Clear request =
//                        sheets.spreadsheets().values().clear(SHEET_ID, RANGE, requestBody);
//
//                ClearValuesResponse response = request.execute();
//                System.out.println(response.getClearedRange());
//                if (response != null && !response.getClearedRange().isEmpty())
//                    return true;
//            } else
//                return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
