package jp.laboratorium2;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 *  Class Book
 *  - implements Cloneable and Serializable interfaces.
 *  Creates and stores data for books
 *
 *  @author Tobiasz Rumian
 *  @version 1.0
 *   Date: 20 October 2016 r.
 *   Index number: 226131
 *   Group: śr 13:15 TN
 */
class Book implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    private String name = null;
    private double price = 0d;
    private String author = null;
    private GregorianCalendar dateOfPurchase = new GregorianCalendar();
    private GregorianCalendar dateOfRelease = new GregorianCalendar();
    private int quantity=0;
    Book(String name, double price, String author, GregorianCalendar dateOfRelease, int quantity)
    {
        this.author=author;
        this.name=name;
        this.price=price;
        this.dateOfRelease=dateOfRelease;
        this.quantity=quantity;
    }
    void setDateOfPurchase(GregorianCalendar date)
    {
        this.dateOfPurchase=date;
    }
    public String getName()
    {
        return name;
    }
    double getPrice()
    {
        return price;
    }
    String getAuthor()
    {
        return author;
    }
    String getDateOfPurchase()
{
    return showDate(dateOfPurchase);
}
    String getDateOfRelease()
    {
        return showDate(dateOfRelease);
    }
    int getQuantity()
        {
            return quantity;
        }
    void setQuantity(int quantity) throws Exception
    {
        if(quantity>=0)this.quantity=quantity;
        else throw(new Exception("Quantity cannot be less than 0"));
    }
    void addQuantity(int quantity)throws Exception
    {
        if(quantity>=0) this.quantity+=quantity;
        else throw(new Exception("Quantity cannot be less than 0"));
    }
    void subtractQuantity(int quantity) throws Exception
    {
        if(this.quantity>=quantity) this.quantity-=quantity;
        else throw(new Exception("Not enough quantity"));
    }
    void setPrice(double price) throws Exception
    {
        if(price>0) Book.this.price=price;
        else throw(new Exception("Price cannot be less or equal 0!"));
    }
    private String showDate(GregorianCalendar calendar)
    {
        return (Integer.toString(calendar.get(GregorianCalendar.DATE)) + ".") +
                Integer.toString(calendar.get(GregorianCalendar.MONTH)) + "." +
                Integer.toString(calendar.get(GregorianCalendar.YEAR));
    }
    @Override
    public Book clone() throws CloneNotSupportedException
    {
        Book cloned = (Book) super.clone();
        cloned.dateOfPurchase = (GregorianCalendar) dateOfPurchase.clone();
        cloned.dateOfRelease = (GregorianCalendar) dateOfRelease.clone();
        return cloned;
    }
}

