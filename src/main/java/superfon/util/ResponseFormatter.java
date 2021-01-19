package superfon.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseFormatter<T> {

    /*
     * {
     *   "success": "True" or "False,
     *   "body": {object} or null,
     *   "error": null or String
     * }
     * */
    public Map<String, Object> formatResponse(boolean success, T type, String errorObj) {
        Map<String, Object> formatter = new HashMap<>();
        formatter.put("success", success);
        formatter.put("body", type);
        formatter.put("error", errorObj);
        return formatter;
    }
}
