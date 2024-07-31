package Example.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResponseController {

    @GetMapping("/response")
    public void getResponse(
            @RequestParam String message,
            @RequestParam(required = false) Integer statusCode,
            HttpServletResponse response) throws IOException {


        String[] pairs = message.split("@");
        Map<String, String> responseData = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                responseData.put(keyValue[0], keyValue[1]);
            } else {
                responseData.put(pair, "No value");
            }
        }

        // Status kodunu müəyyən et
        int status = statusCode != null ? statusCode : 200; // Əgər status kodu verilməyibsə, 200 OK istifadə et
        String customStatusMessage = "Everything is okay";

        // Status kodu 313 üçün xüsusi işləmə
        if (status == 313) {
            customStatusMessage = "Custom status 313";
        } else if (status < 100 || status > 10000) {
            status = 500;
            customStatusMessage = "Invalid status code";
        }

        // Status kodunu qoy
        response.setStatus(status);

        // responsun tipini teyin etmek
        response.setContentType("application/json");

        // responseData'ı JSON formatına çevir
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(responseData);

        response.getWriter().write(String.format(
                "{\"statusCode\": %d, \"statusMessage\": \"%s\", \"data\": %s}",
                status,
                customStatusMessage,
                jsonData
        ));
    }

}
