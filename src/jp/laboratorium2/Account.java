package jp.laboratorium2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
/**
 *  Class Account
 *  - implements UserDialog interface.
 *  Creates and stores data for accounts
 *
 *  @author Tobiasz Rumian
 *  @version 1.0
 *   Date: 20 October 2016 r.
 *   Index number: 226131
 *   Group: śr 13:15 TN
 */
public class Account implements Serializable
{

    private static final long serialVersionUID = 1L;
    private String login;
    private String username;
    private long password;
    private double balance =0;
    private String address = null;
    private ArrayList<Book> trolley = new ArrayList<>();

    Account(String login, String password, String username)
    {
        this.login = login;
        this.password = password.hashCode();
        this.username = username;
    }
    void setAddress(String address)
    {
        this.address=address;
    }
    private void addToTrolley(Book book)
    {
        trolley.add(book);
    }
    boolean isTrolleyEmpty()
    {
        return trolley.size() <= 0;
    }
    String getLogin()
    {
        return login;
    }
    void setUsername(String username)
    {
        this.username = username;
    }
    String getUsername()
    {
        return username;
    }
    boolean checkPassword(String password)
    {
        return (password!=null&&this.password==password.hashCode());
    }
    void setPassword(String oldPassword, String newPassword) throws Exception
    {
        if (checkPassword(oldPassword)) this.password=newPassword.hashCode();
        else throw(new Exception("Wrong password"));
    }
    double getBalance()
    {
        return balance;
    }
    void payIn(double amount) throws Exception
    {
        if (amount>=0)balance += amount;
        else throw new Exception("Amount cannot be negative");
    }
    private void payOut(String password, double amount) throws Exception
    {
        if (!checkPassword(password)) throw new Exception("Wrong password");
        if (amount<=0) throw new Exception("Amount cannot be negative");
        if (amount>balance) throw new Exception("Not enough funds");
        balance -= amount;
    }
    private void transfer(String password, double amount) throws Exception
    {
        payOut(password, amount);
        MiniShop.shop.payIn(amount);
    }
    void buy(MiniShop shop, int number, int quantity, String password) throws Exception
    {
        Book book = shop.findBook(number);
        if(book.getQuantity()>=quantity)
        {
            if(balance>=book.getPrice())
            {
                transfer(password,book.getPrice()*quantity);
                Book clonedBook = book.clone();
                clonedBook.setQuantity(quantity);
                clonedBook.setDateOfPurchase(new GregorianCalendar());
                addToTrolley(clonedBook);
                book.subtractQuantity(quantity);
            } else throw(new Exception("Not enough funds in your account"));
        } else throw(new Exception("Not enough quantity in warehouse"));
    }
    int getTrolleySize()
    {
        return trolley.size();
    }
    String showTrolley()
    {
        StringBuilder sb = new StringBuilder();
        int n = 1;
        sb.append(String.format("%3s  %-10s %-10s %-17s %-17s %-6s %-8s", "np.", "Name", "Author","Date of release","Date of purchase","Price","Quantity"));
        for (Book book : trolley)
        {
            sb.append("\n");
            sb.append(String.format("%3d) %-10s %-10s %-17s %-17s %06.2f %8d", n, book.getName(), book.getAuthor(),book.getDateOfRelease(),book.getDateOfPurchase(),book.getPrice(),book.getQuantity()));
            n++;
        }
        return sb.toString();
    }
    String getAddress()
    {
        return address;
    }
    double getAmount()
    {
        double amount=0;
        for (Book book:trolley)
        {
            amount+=book.getPrice();
        }
        return amount;
    }
}

