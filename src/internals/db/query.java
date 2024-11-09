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

    // Returns a JSON Array consisting JSON Objects of the search results
    public JSONArray getRecords(String searchaccount) throws Exception
    {
        String formQuery;
        if  (searchaccount != null)
        {
            formQuery = "SELECT * FROM passadmin WHERE account=\\\"%s\\\";";
            formQuery = String.format(formQuery, searchaccount);
        }
        else
        {
            formQuery = "SELECT * FROM passadmin;";
        }
        JSONObject resp = sender(formQuery);
        JSONArray results = resp.getJSONArray("result").getJSONObject(0).getJSONArray("results");
        
        return results;
    }

    public Boolean modifyRecord(String currUsername, 
                             String currPassword,
                             String newUsername,
                             String newPassword,
                             Boolean modifyPassword) throws Exception
    {
        Boolean status = false;
        if (modifyPassword)
        {
            

            String formQuery = "UPDATE passadmin SET password = \\\"%s\\\" WHERE password=\\\"%s\\\"";
            formQuery = String.format(formQuery, newPassword, currPassword);
            
            JSONObject resp = sender(formQuery);
            status = resp.getBoolean("success");
            
        }
        else
        {
            String formQuery = "UPDATE passadmin SET username = \\\"%s\\\" WHERE password=\\\"%s\\\"";
            formQuery = String.format(formQuery, newUsername, currPassword);
            

            JSONObject resp = sender(formQuery);
            status = resp.getBoolean("success");
        }
        return status;
    }

    public Boolean deleteRecord(String password) throws Exception
    {
        String formQuery = "DELETE from passadmin WHERE password='%s'";
        formQuery = String.format(formQuery, password);
        
        JSONObject resp = sender(formQuery);
        return resp.getBoolean("success");
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
        System.out.println("Querying Database...");
        HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = new JSONObject(resp.body());
        return json;
    }

    
    
}