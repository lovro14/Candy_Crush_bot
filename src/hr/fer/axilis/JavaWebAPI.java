package hr.fer.axilis;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.json.JSONObject;
import java.net.HttpURLConnection;

public class JavaWebAPI {
	
	private String url;

	public JavaWebAPI(String url) {
		this.url = url;
	}
	
	public JSONObject sendPost(String path, JSONObject parameters) throws Exception {
		
            String query = url + path; 
            String json = parameters.toString(); 

            URL url = new URL(query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            JSONObject jsonObject = new JSONObject(result);
            in.close();
            connection.disconnect();

            return jsonObject;
    }
}