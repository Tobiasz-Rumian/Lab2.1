package jp.laboratorium2;


import java.util.GregorianCalendar;
import java.util.Scanner;
/**
 *  Class MiniShopApplication
 *  Run whole application.
 *
 *  @author Tobiasz Rumian
 *  @version 1.0
 *   Date: 20 October 2016 r.
 *   Index number: 226131
 *   Group: śr 13:15 TN
 */
class MiniShopApplication
{
    private MiniShop shop = new MiniShop();
    private static UserDialog UI;
    /**
     * Allows to choose between displaying messages in console or in windows.
     * @param b if true, messages are displayed in console, if false, messages are displayed in windows.
     */
    private static void chooseUserDialog(boolean b)
    {
        if(b) MiniShopApplication.UI = new ConsoleUserDialog();
        else  MiniShopApplication.UI =new JOptionPaneUserDialog();
    }
    /**
     * Asks the user which way he wants to see the application and then run it.
     */
    public static void main(String[] args)
    {
        System.out.println("Select the application version:");
        System.out.println("C-console");
        System.out.println("W-windows");
        Scanner in = new Scanner(System.in);
        try
        {
            char answer= in.nextLine().charAt(0);
            if(answer=='C'||answer=='c') MiniShopApplication.chooseUserDialog(true);
            else if(answer=='W'||answer=='w') MiniShopApplication.chooseUserDialog(false);
            else
            {
                System.err.println(INCORRECT_DATA);
                return;
            }
        }
        catch(Exception e)
        {
            System.err.println(INCORRECT_DATA);
            return;
        }
        new MiniShopApplication();
    }
    private static final String GREETING_MESSAGE =
                    "Program MINISHOP - console version \n" +
                    "Author: Tobiasz Rumian\n" +
                    "Date: 20 October 2016 r.\n"+
                    "Index number: 226131\n";

    private static final String MAIN_MENU =
                    "SHOP - M A I N    M E N U\n" +
                    "1 - Show all users\n" +
                    "2 - Create new account\n" +
                    "3 - Log in to your account\n" +
                    "0 - Exit\n";

    private static final String ACCOUNT_MENU =
                    "1 - Pay in\n" +
                    "2 - Buy\n" +
                    "3 - View trolley\n" +
                    "4 - Change password\n" +
                    "5 - Change username\n" +
                    "6 - Delete account\n" +
                    "0 - Log out\n";

    private static final String ADMIN_MENU =
                    "1 - Add product to the warehouse\n"+
                    "2 - Browse warehouse\n"+
                    "3 - Add quantity to warehouse\n"+
                    "4 - Change price\n"+
                    "5 - Delete product\n"+
                    "6 - List of registered clients\n" +
                    "7 - Change password\n"+
                    "8 - Create invoice\n"+
                    "0 - Log out\n";
    private static final String INCORRECT_DATA = "Incorrect data!";
    private static final String DATA_FILE_NAME = "MINISHOP.BIN";

    public MiniShopApplication()
    {
        UI.printMessage(GREETING_MESSAGE);
        try
        {
            shop.loadShopFromFile(DATA_FILE_NAME);
            UI.printMessage("Accounts have been downloaded from  " + DATA_FILE_NAME);
        } catch (Exception e)
        {
            UI.printErrorMessage(e.getMessage());
        }
        while (true)
        {
            try
            {
                UI.clearConsole();
                switch (UI.enterInt(MAIN_MENU + "==>> "))
                {
                    case 1:
                        listAllAccounts();
                        break;
                    case 2:
                        createNewAccount();
                        break;
                    case 3:
                        loginAccount();
                        break;
                    case 0:
                        try
                        {
                            shop.saveShopToFile(DATA_FILE_NAME);
                            UI.printMessage("\nAccounts have been saved to " + DATA_FILE_NAME);
                        }
                        catch (Exception e)
                        {
                            UI.printErrorMessage(e.getMessage());
                        }
                        UI.printInfoMessage("\nHave a nice day!");
                        System.exit(0);
                }
            }
            catch (Exception e)
            {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
    private void listAllAccounts()
    {
        UI.printMessage("\nLIST OF ACCOUNTS:\n"+shop.listAccounts());
    }
    private void createNewAccount()
    {
        String login;
        String password;
        String username;

        UI.printMessage("\nCREATING NEW ACCOUNT\n");
        while(true)
        {
            Account account;
            login = UI.enterString("Enter login:");
            if (login.equals("")) return;
            if (shop.findAccount(login)!=null)
            {
                UI.printErrorMessage("Account with that login already exists");
                continue;
            }
            username = checkIfEmptyString("Enter username(it will be visible to everybody)");
            password = checkIfEmptyString("Enter password:");
            try
            {
                account = shop.createAccount(login,password,username);
            } catch (Exception e)
            {
                UI.printErrorMessage(e.getMessage());
                continue;
            }
            String address = checkIfEmptyString("Enter user address: ");
            account.setAddress(address);
            UI.printMessage("Account has been created!");
            break;
        }
    }

    /**
     *  Checks if the entered text is not empty.
     * @param text Text displayed in the <code>UI.enterString</code> function
     * @return Returns the correct text.
     */
    private String checkIfEmptyString(String text)
    {
        boolean check = true;
        String obj=null;
        while(check)
        {
            obj = UI.enterString(text);
            if (!obj.equals("")) check=false;
            else UI.printErrorMessage(INCORRECT_DATA);
        }
        return obj;
    }
    /**
     *  Checks if the entered integer is not empty.
     * @param text Text displayed in the <code>UI.enterString</code> function
     * @return Returns the correct integer.
     */
    private int checkIfEmptyInteger(String text)
    {
        boolean check = true;

        int obj=-1;
        while(check)
        {
            obj = UI.enterInt(text);
            if (obj>=0) check=false;
            else UI.printErrorMessage(INCORRECT_DATA);
        }
        return obj;
    }
    /**
     *  Checks if the entered double is not empty.
     * @param text Text displayed in the <code>UI.enterString</code> function
     * @return Returns the correct double.
     */
    private double checkIfEmptyDouble(String text)
    {
        boolean check = true;

        double obj=-1;
        while(check)
        {
            obj = UI.enterDouble(text);
            if (obj>=0) check=false;
            else UI.printErrorMessage(INCORRECT_DATA);
        }
        return obj;
    }

    private void accountMenu(Account account, String password)
    {
        while (true)
        {
            UI.printMessage("\nYOU ARE LOGGED IN!");
            UI.printMessage("Username: " + account.getUsername());
            UI.printMessage("Balance: " + account.getBalance());
            if(account.isTrolleyEmpty()) UI.printMessage("Your trolley is empty");
            else if(account.getTrolleySize()==1) UI.printMessage("You have 1 book in your trolley");
            else UI.printMessage("You have "+account.getTrolleySize()+" books in your trolley");
            try
            {
                switch (UI.enterInt(ACCOUNT_MENU + "==>> "))
                {
                    case 1:
                        payInMoney(account);
                        break;
                    case 2:
                        buy(account,password);
                        break;
                    case 3:
                        UI.printMessage(account.showTrolley());
                        break;
                    case 4:
                        password = changePassword(account, password);
                        break;
                    case 5:
                        changeUsername(account);
                        break;
                    case 6:
                        if (removeAccount(account))
                        {
                            account = null;
                            return;
                        }
                        break;
                    case 0:
                        account = null;
                        UI.printMessage("You have been logged out.");
                        return;
                }
            } catch (Exception e)
            {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }

    private void buy(Account account, String password)
    {
        int number,quantity;
        showAllBooks();
        number=checkIfEmptyInteger("Enter the product's number");
        quantity=checkIfEmptyInteger("Enter the quantity you want to buy");
        try
        {
            account.buy(shop,number,quantity,password);
        }
        catch (Exception e)
        {
            UI.printErrorMessage(e.getMessage());
        }
    }

    private void adminMenu(Account account)
    {
        while (true)
        {
            UI.printMessage("\nYOU ARE LOGGED AS ADMINISTRATOR");
            UI.printMessage("Shop balance: " + account.getBalance());
            try
            {
                switch (UI.enterInt(ADMIN_MENU + "==>> "))
                {
                    case 1:
                        addNewBook();
                        break;
                    case 2:
                        showAllBooks();
                        break;
                    case 3:
                        resupply();
                        break;
                    case 4:
                        changePrice();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 6:
                        listAllAccountsLogins();
                        break;
                    case 7:
                        changeAdminPassword();
                        break;
                    case 8:
                        createInvoice();
                        break;
                    case 0:
                        account = null;
                        UI.printMessage("You have been signed out");
                        return;
                }
            } 
            catch (Exception e)
            {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
    private void listAllAccountsLogins()
    {
        UI.printMessage("\nLIST OF ACCOUNTS:\n"+shop.listAccountsLogins());
    }
    private void createInvoice()
    {
        UI.printMessage("\nCREATING AN INVOICE");
        listAllAccountsLogins();
        String login=UI.enterString("Enter name(login) of the account you want to create an invoice for");
        try
        {
            UI.printInfoMessage(shop.createInvoice(shop.findAccount(login)));
        }
        catch(Exception e)
        {
            UI.printErrorMessage("Such account does not exist!");
        }
    }

    private void changeAdminPassword()
    {
        String newPassword,oldPassword;
        UI.printMessage("\nYOU ARE CHANGING ADMIN PASSWORD!");
        oldPassword = UI.enterString("Enter old password: ");
        newPassword = UI.enterString("Enter new password: ");
        try
        {
            shop.changeAdminPassword(oldPassword,newPassword);
        }
        catch (Exception e)
        {
            UI.printErrorMessage(e.getMessage());
        }
    }

    private void deleteProduct()
    {
        int number;
        showAllBooks();
        number=checkIfEmptyInteger("Enter the product's number you want to delete");
        if(UI.enterChar("Do you really want to delete product named: "+shop.getNameOfBook(number)+" [Y/N]")=='Y')
        {
            shop.deleteBook(number);
        }
    }

    private void changePrice()
    {
        int number;
        double price;
        showAllBooks();
        number=checkIfEmptyInteger("Enter the product's number");
        price=checkIfEmptyDouble("Enter the price");
        try
        {
            shop.setPrice(number,price);
        }
        catch(Exception e)
        {
            UI.printErrorMessage(e.getMessage());
        }
    }

    private void resupply()
    {
        int number,quantity;
        showAllBooks();
        number=checkIfEmptyInteger("Enter the product's number");
        quantity=checkIfEmptyInteger("Enter the quantity you want to add");
        try
        {
            shop.addQuantity(number,quantity);
        }
        catch(Exception e)
        {
            UI.printErrorMessage(e.getMessage());
        }

    }

    private void showAllBooks()
    {
        UI.printMessage("\n\n" + "CURRENT LIST OF BOOKS:\n"+shop.listBooks());
    }

    private void addNewBook()
    {
        String name,author;
        double price;
        GregorianCalendar dateOfRelease= new GregorianCalendar();
        int quantity,month,year,day;
        name=checkIfEmptyString("Enter title");
        author=checkIfEmptyString("Enter author");
        price=checkIfEmptyDouble("Enter price");
        year=checkIfEmptyInteger("Enter year of release");
        month=checkIfEmptyInteger("Enter month of release");
        day=checkIfEmptyInteger("Enter day of release");
        dateOfRelease.set(year,month,day);
        quantity=checkIfEmptyInteger("Enter quantity");
        shop.addBook(name,price,author,dateOfRelease,quantity);
    }
    private void loginAccount()
    {
        String login;
        String password;
        Account account;

        UI.printMessage("\nLOGGING INTO ACCOUNT\n");
        login = checkIfEmptyString("Enter login");
        password = checkIfEmptyString("Enter password");
        account = shop.findAccount(login, password);
        if (account == null)
        {
            UI.printErrorMessage(INCORRECT_DATA);
            return;
        }
        if(account.getLogin().equals("admin")&&account.checkPassword(password))
        {
            adminMenu(account);
        }
        else
        accountMenu(account,password);
    }

    /**
     *  Allows to deposit money.
     * @param account User's account.
     * @throws Exception Exception thrown if amount<=0.
     */
    private void payInMoney(Account account)throws Exception
    {
        UI.printMessage("\nMAKING DEPOSIT");
        double amount = checkIfEmptyDouble("Enter amount");
        account.payIn(amount);
    }

    /**
     * Allows to change user password.
     * @param account User's account.
     * @param password User's password.
     * @return Returns new password
     * @throws Exception Exception thrown if password is incorrect.
     */
    private String changePassword(Account account, String password) throws Exception
    {
        UI.printMessage("\nCHANGING PASSWORD");
        String newPassword = UI.enterString("Enter new password");
        account.setPassword(password, newPassword);
        return newPassword;
    }

    /**
     * Allows to change username.
     * @param account User's account.
     */
    private void changeUsername(Account account)
    {
        UI.printMessage("\nCHANGING USERNAME");
        String newUsername = UI.enterString("Enter username: ");
        account.setUsername(newUsername);
    }

    /**
     * Allows to delete account.
     * @param account User's account.
     * @return Returns true if account is successfully deleted and false if not.
     * @throws Exception Exception thrown if wrong password.
     */
    private boolean removeAccount(Account account) throws Exception
    {
        String answer;

        UI.printMessage("\nDELETING ACCOUNT");
        answer = UI.enterString("Are you sure? (Y/N)");
        if (!answer.equals("Y"))
        {
            UI.printErrorMessage("\nAccount not deleted!");
            return false;
        }
        shop.removeAccount(account);
        UI.printInfoMessage("\nAccount deleted");
        return true;
    }
}

