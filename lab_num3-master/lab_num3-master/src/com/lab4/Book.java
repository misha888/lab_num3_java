package com.lab4;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

    /*
    Определить количество товаров, проданных продавцом «Иванов», вывести сведения о них и
    определить товар с максимальной стоимостью.
Продавец
Наименование
Количество
Цена
Дата продажи
*/

public class Book implements Serializable {
    private String seller;
    private String title;
    private int quantity;
    private int price;
    // private Date dateOfSale;

    public Book() {
    }

    public Book(String seller, String title, int quantity, int price) {
        this.seller = seller;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSeller() {
        return this.seller;
    }

    public void setSeller(String seller) {
        this.seller = seller.strip();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title.strip();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity > 0 ? quantity : -1;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price > 0 ? price : -1;
    }

    /*
    public Date getDateOfSale() {
        return this.dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        try {
            this.dateOfSale = (new SimpleDateFormat("dd/MM/yyyy")).parse(dateOfSale);
        } catch (ParseException var3) {
        }
    }
*/
    @Override
    public String toString() {
        return "\nBook{\n" +
                "seller='" + seller + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nquantity=" + quantity +
                ", \nprice=" + price +
                '}';
    }
}
