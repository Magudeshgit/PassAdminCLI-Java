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
        System.out.printf("""
                        \n%sChoose What do you want to do.?
                        1. View Password 
                        2. View All Passwords
                        3. Add Password 
                        4. Modify
                        5. Delete
                        6. Exit
                        \nEnter you choice.: %s""", formatText.BOLD, formatText.TXRESET);
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
                operation.viewPassword();
                break;
            case 2:
                operation.viewAllPasswords();
                break;
            case 3:
                operation.addPassword();
                break;

            case 4:
                operation.modifyRecord();
                break;
            case 5:
                operation.deletRecord();
                break;
            case 6:
                System.out.printf("\n%s%sThankyou for using PassAdminCLI%s\n", formatText.GREEN, formatText.BOLD, formatText.TXRESET);
                flag=false;
                break;
            default:
                System.out.printf("%sInvalid Option, Please choose a valid option.%s", formatText.BOLD, formatText.TXRESET);
                break;
            }   

       } while(flag);
       inp.close();
    
    }
}