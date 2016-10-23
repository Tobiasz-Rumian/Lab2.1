package jp.laboratorium2;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *  Class MiniShop
 *  - implements Serializable interface.
 *  Creates and stores data for the shop.
 *  Has additional static account for admin.
 *
 *  @author Tobiasz Rumian
 *  @version 1.0
 *   Date: 20 October 2016 r.
 *   Index number: 226131
 *   Group: śr 13:15 TN
 */
class MiniShop implements Serializable
{

    private static final long serialVersionUID = 1L;
    private ArrayList<Book> warehouse = new ArrayList<>();
    private ArrayList<Account> listOfAccounts = new ArrayList<>();
    static Account shop = new Account("admin", "admin1","Shop");
    private void checkIfEmpty(String string,String name) throws Exception
    {
        if (string==null||string.equals("")) throw(new Exception(name.toUpperCase()+" cannot be empty"));
    }
    Account createAccount(String login, String password, String username) throws Exception
    {
        checkIfEmpty(login,"Login");
        checkIfEmpty(password,"Password");
        checkIfEmpty(username,"Username");
        if (findAccount(login)!=null) throw(new Exception("Existing account"));
        Account newAccount = new Account(login,password,username);
        listOfAccounts.add( newAccount );
        return newAccount;
    }
    void addBook(String name, double price, String author, GregorianCalendar dateOfRelease, int quantity)
    {
        warehouse.add(new Book(name,price,author,dateOfRelease,quantity));
    }

    void changeAdminPassword(String oldPassword, String newPassword) throws Exception
    {
        shop.setPassword(oldPassword,newPassword);
    }

    void removeAccount(Account account) throws Exception
    {
        if (account==null) throw(new Exception("No such account exist"));
        if (account.getBalance()!= 0) throw(new Exception("Balance not equal to 0"));
        listOfAccounts.remove(account);
    }

    Account findAccount(String login)
    {
        if(shop.getLogin().equals((login))) return shop;
        for (Account account : listOfAccounts)
        {
            if (account.getLogin().equals(login)) return account;
        }
        return null;
    }

    void addQuantity(int number, int quantity) throws Exception
    {
        if(warehouse.get(number-1)!=null) warehouse.get(number-1).addQuantity(quantity);
        else throw(new Exception("No such book exist"));
    }

    void setPrice(int number, double price) throws Exception
    {
        if(warehouse.get(number-1)!=null) warehouse.get(number-1).setPrice(price);
        else throw(new Exception("No such book exist"));
    }

    Account findAccount(String name, String password)
    {
        Account account = findAccount(name);
        if (account!=null&&account.checkPassword(password)) return account;
        return null;
    }

    Book findBook(int number) throws Exception
    {
        if(warehouse.get(number-1)!=null) return warehouse.get(number-1);
        else throw(new Exception("No such book exist"));
    }
    String getNameOfBook(int number)
    {
        if(warehouse.get(number-1)!=null) return warehouse.get(number-1).getName();
        else return null;
    }

    void deleteBook(int number)
    {
        if(warehouse.get(number-1)!=null) warehouse.remove(number-1);
    }

    String listAccounts()
    {

        StringBuilder sb = new StringBuilder();
        int n = 1;
        sb.append(String.format("%3s  %-10s", "no.", "Username"));
        for (Account account : listOfAccounts)
        {
            sb.append("\n");
            sb.append(String.format("%03d) %-10s", n, account.getUsername()));
            n++;
        }
        return sb.toString();
    }
    String listAccountsLogins()
    {

        StringBuilder sb = new StringBuilder();
        int n = 1;
        sb.append(String.format("%3s  %-10s", "no.", "Login"));
        for (Account account : listOfAccounts)
        {
            sb.append("\n");
            sb.append(String.format("%03d) %-10s", n, account.getLogin()));
            n++;
        }
        return sb.toString();
    }

    String listBooks()
    {
        StringBuilder sb = new StringBuilder();
        int n = 1;
        sb.append(String.format("%3s  %-10s %-10s %-17s %-6s %-8s", "no.", "Title", "Author","Date of release","Price","Quantity"));
        for (Book book : warehouse)
        {
            sb.append("\n");
            sb.append(String.format("%3d) %-10s %-10s %-17s %06.2f %8d", n, book.getName(), book.getAuthor(),book.getDateOfRelease(),book.getPrice(),book.getQuantity()));
            n++;
        }
        return sb.toString();
    }

    void saveShopToFile(String fileName) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(listOfAccounts);
        out.writeObject(warehouse);
        out.writeObject(shop);
        out.close();
    }


    void loadShopFromFile(String fileName) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        listOfAccounts = (ArrayList<Account>)in.readObject();
        warehouse=(ArrayList<Book>)in.readObject();
        shop=(Account)in.readObject();
        in.close();
    }
    String createInvoice(Account account)
    {
        return "BOOKS AND YOU, Woronicza 33/38 22-101 Warsaw \n" +
                "Account: " + account.getUsername() + "\t address: " + account.getAddress() + "\n" +
                account.showTrolley() + "\n" +
                "Amount to pay: " + account.getAmount() + "\n" +
                "THANK YOU FOR SHOPPING WITH BOOKS AND YOU!";
    }

}

