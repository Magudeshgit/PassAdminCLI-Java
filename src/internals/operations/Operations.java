package internals.operations;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import internals.crypto.cryptohandlers;
import internals.formatters.dataformatter;
import internals.db.query;

public class Operations {
    dataformatter formatText = new dataformatter();
    cryptohandlers crypto = new cryptohandlers();
    query db = new query();
    Scanner input = new Scanner(System.in);

    public void addPassword() throws Exception
    {
        System.out.printf("%s\nAdd Password%s\n", formatText.BOLD, formatText.TXRESET);

        System.out.printf("Enter App/Website of Account.: ");
        String source = input.next();

        System.out.printf("Enter Username of the belonging Account.: ");
        String username = input.next();

        System.out.printf("Enter Password.: ");
        String password = input.next();

        String encrypted = crypto.encrypt(password);
        db.insertPassword(source, username, encrypted);
    }

    public void viewPassword() throws Exception
    {
        System.out.printf("%s\nView Password%s\n", formatText.BOLD, formatText.TXRESET);

        System.out.printf("Enter App/Website Name.: ");
        String source = input.next();

        JSONArray searchresults = db.getRecords(source);
        // Formatting Text to Output
        System.out.println("---------------------------------");
        System.out.println("           Your Passwords            ");
        System.out.println("---------------------------------");

        System.out.printf("| %4s | %-10s | %-8s | %4s | %n", "ID","Account", "Username", "Password");
        System.out.println("---------------------------------");

        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            searchresults.getJSONObject(i).put("ID", i+1);

            System.out.printf("| %4s | %-10s | %-8s | %4s | \n", record.getInt("ID"),record.getString("account"), record.getString("username"), crypto.decrypt(record.getString("password")));
        }

    }

    public void viewAllPasswords() throws Exception
    {
        System.out.printf("%s\nView All Passwords%s\n", formatText.BOLD, formatText.TXRESET);

        JSONArray searchresults = db.getRecords(null);
        // Formatting Text to Output
        System.out.println("---------------------------------");
        System.out.println("           Your Passwords            ");
        System.out.println("---------------------------------");

        System.out.printf("| %4s | %-10s | %-8s | %4s | %n", "ID","Account", "Username", "Password");
        System.out.println("---------------------------------");

        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            searchresults.getJSONObject(i).put("ID", i+1);

            System.out.printf("| %4s | %-10s | %-8s | %4s | \n", record.getInt("ID"),record.getString("account"), record.getString("username"), crypto.decrypt(record.getString("password")));
        }

    }

    public void modifyRecord() throws Exception
    {
        System.out.printf("%s\nModify%s\n", formatText.BOLD, formatText.TXRESET);

        System.out.printf("Enter App/Website Name.: ");
        String source = input.next();

        JSONArray searchresults = db.getRecords(source);

        // Formatting Text to Output
        System.out.println("---------------------------------");
        System.out.println("           Your Passwords            ");
        System.out.println("---------------------------------");

        System.out.printf("| %4s | %-10s | %-8s | %4s | %n", "ID","Account", "Username", "Password");
        System.out.println("---------------------------------");

        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            searchresults.getJSONObject(i).put("ID", i+1);

            System.out.printf("| %4s | %-10s | %-8s | %4s | \n", record.getInt("ID"),record.getString("account"), record.getString("username"), crypto.decrypt(record.getString("password")));
        }

        System.out.printf("\nEnter ID to Modify.: ");
        int ID = input.nextInt();


        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            if (record.getInt("ID") == ID)
            {
                System.out.printf("""
                    \nWhat would you like to modify?
                    1. Username
                    2. Password
                    \nEnter Choice.: """);
                    int editChoice = input.nextInt();
                    switch (editChoice) {
                        case 1:
                            System.out.printf("\nEnter New Username.: ");
                            String newUsername = input.next();
                            Boolean status = db.modifyRecord(record.getString("username"),
                                            record.getString("password"),
                                            newUsername,
                                            null,
                                            false
                                            );
                            if (status)
                            {
                                System.out.println("Update SuccessFull");
                            }
                            else
                            {
                                System.out.println("Update Failed");
                            }
                            break;
                        case 2:
                            System.out.printf("\nEnter New Password.: ");
                            String newPassword = input.next();
                            Boolean updateStatus = db.modifyRecord(record.getString("username"),
                                            record.getString("password"),
                                            null,
                                            crypto.encrypt(newPassword),
                                            true
                                            );
                            if (updateStatus)
                            {
                                System.out.println("Update SuccessFull");
                            }
                            else
                            {
                                System.out.println("Update Failed");
                            }
                            break;
                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                
            }
            searchresults.getJSONObject(i).put("ID", i+1);

            System.out.printf("| %4s | %-10s | %-8s | %4s | \n", record.getInt("ID"),record.getString("account"), record.getString("username"), crypto.decrypt(record.getString("password")));
        }
    }
    
    public void deletRecord() throws Exception
    {
        System.out.printf("%s\nDelete%s\n", formatText.BOLD, formatText.TXRESET);

        System.out.printf("Enter App/Website Name.: ");
        String source = input.next();

        JSONArray searchresults = db.getRecords(source);

        // Formatting Text to Output
        System.out.println("---------------------------------");
        System.out.println("           Your Passwords            ");
        System.out.println("---------------------------------");

        System.out.printf("| %4s | %-10s | %-8s | %4s | %n", "ID","Account", "Username", "Password");
        System.out.println("---------------------------------");
        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            searchresults.getJSONObject(i).put("ID", i+1);

            System.out.printf("| %4s | %-10s | %-8s | %4s | \n", record.getInt("ID"),record.getString("account"), record.getString("username"), crypto.decrypt(record.getString("password")));
        }

        System.out.printf("\nEnter ID to Delete.: ");
        int ID = input.nextInt();

        for (int i=0;i<searchresults.length();i++)
        {
            JSONObject record = searchresults.getJSONObject(i);
            if (record.getInt("ID") == ID)
            {
                Boolean status = db.deleteRecord(record.getString("password"));
                if (status)
                {
                    System.out.println("Deletion Succesfull");
                }
                else
                {
                    System.out.println("Deletion Failed");
                }
            }
       }
        
    }
}