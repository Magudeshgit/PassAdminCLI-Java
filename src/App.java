import java.util.Scanner;

import internals.formatters.dataformatter;
import internals.operations.Operations;

class App 
{    
    public static void main(String[] args) throws Exception
    {
        boolean flag = true;
        int choice;

        dataformatter formatText = new dataformatter();
        Operations operation = new Operations();

        Scanner inp = new Scanner(System.in);
        do
       {
        System.out.printf("\n\n%s%sPassAdmin%s\n", formatText.GREEN, formatText.BOLD, formatText.TXRESET);
        System.out.printf("%s%sA Safe and Secure password management tool%s\n", formatText.GREEN, formatText.BOLD, formatText.TXRESET);
        System.out.printf("\n%sChoose What do you want to do.?\n1. View Password \n2. Add Password \n3. Modify Password\n\nEnter you choice.: %s", formatText.BOLD, formatText.TXRESET);
        if (inp.hasNextInt())
        {
            choice = inp.nextInt();
            inp.nextLine();
        }
        else
        {
            System.out.println("invalid");
            inp.nextLine();
            continue;
        }
        

        switch (choice) {
            case 1:
                System.out.println("View Password");
                break;
            case 2:
                operation.addPassword();
                break;

            case 3:
                System.out.println("Modify Password");
                break;
        
            default:
                flag = false;
                break;
            }   

       } while(flag);
       inp.close();
    
    }
}