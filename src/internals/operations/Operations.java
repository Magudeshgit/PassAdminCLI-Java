package internals.operations;
import java.util.*;

import internals.crypto.cryptohandlers;
import internals.formatters.dataformatter;
import internals.db.query;

public class Operations {
    dataformatter formatText = new dataformatter();
    cryptohandlers crypto = new cryptohandlers();
    query db = new query();

    public void addPassword() throws Exception
    {
        Scanner input = new Scanner(System.in);
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
}