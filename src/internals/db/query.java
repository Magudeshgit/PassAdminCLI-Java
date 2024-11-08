package internals.db;

import org.json.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import internals.formatters.dataformatter;

public class query {
    final private String dburl = "";
    final private String apikey = "";
    dataformatter formatter =  new dataformatter();

    public void insertPassword(String account, String username, String passwd) throws Exception
    {
        System.out.println("Adding Password...");
        String  formQuery = "INSERT INTO passadmin values(\\\"%s\\\",\\\"%s\\\",\\\"%s\\\");";
        formQuery = String.format(formQuery, account, username, passwd);
        JSONObject resp = sender(formQuery);
        Boolean respstatus = resp.getBoolean("success");

        if (respstatus)
        {
            System.out.printf("\n%s%sPassword Saved%s\n", formatter.GREEN, formatter.BOLD, formatter.TXRESET);
        }
        else
        {
            System.out.println("An Error Occured, Please try again.");
        }

    }
    private JSONObject sender(String queryString) throws Exception
    {
        // _query
        String _query = "{\"sql\": \"%s\"}";
        _query = String.format(_query, queryString);

        // Request Builder
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(dburl))
        .header("Authorization", "Bearer "+apikey)
        .POST(BodyPublishers.ofString(_query))
        .build();

        // Instantiating a HTTP CLIENT to propogate the request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(resp.body());
        return json;
    }
    
}
