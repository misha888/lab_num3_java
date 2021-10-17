package com.lab4;

import com.lab4.Book;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    public ArrayList<Book> list;

    public Database() {
        list = new ArrayList<>();
    }

    public void add(Book book) {
        this.list.add(book);
    }

    public void add(String seller, String title, int quantity, int price) {
        this.list.add(new Book(seller, title, quantity, price));
    }

    public Book get(int index) {
        return this.list.get(index);
    }

    public Book remove(int index) {
        return this.list.remove(index);
    }

    @Override
    public String toString() {
        return "Database{" + list + '}';
    }

    public void save(String filename) throws IOException {
        FileWriter outStream = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(outStream);
        for (Book book : list) {
            try {
                bw.write(book.getSeller());
                bw.write(System.lineSeparator());
                bw.write(book.getTitle());
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(book.getQuantity()));
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(book.getPrice()));
                bw.write(System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bw.close();
        outStream.close();

    }

    public void load(String filename) throws IOException {
        this.clear();
        //this.list = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(filename));

        String seller, title = "";
        int quantity, price = -1;

        while (scanner.hasNextLine()) {
            seller = scanner.nextLine();
            title = scanner.nextLine();
            quantity = Integer.parseInt(scanner.nextLine());
            price = (int) Float.parseFloat(scanner.nextLine());
            this.list.add(new Book(seller, title, quantity, price));
        }

        scanner.close();
    }

    public void clear() {
        this.list.clear();
    }

    public void serialize(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.list);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deserialize(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.list = (ArrayList<Book>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Books class not found");
            c.printStackTrace();
        }
    }

    public void jacksonSerialize(String filename) throws IOException {
        new ObjectMapper().writeValue(new File(filename), this);
    }

    public void jacksonDeserialize(String filename) throws IOException {
        Database db1 = new ObjectMapper().readValue(new File(filename), Database.class);
        this.list = db1.list;
    }

    public void serializeFastJSON(String filename) throws IOException {
        FileWriter outStream = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(outStream);
        bw.write(JSON.toJSONString(this.list));
        bw.close();
        outStream.close();
    }

    public void deserializeFastJSON(String filename) throws IOException {
        Scanner scanner = new Scanner(new FileReader(filename));
        this.clear();
        ArrayList<JSONObject> JSONlist = JSON.parseObject(scanner.nextLine(), ArrayList.class);
        for (JSONObject st : JSONlist) {
            this.add(new Book(st.getString("seller"), st.getString("title"), st.getIntValue("quantity"), st.getIntValue("price")));
        }
        scanner.close();
    }

    public ArrayList<Book> findNeededSeller(String name, String path) throws FileNotFoundException {
        ArrayList<Book> foundedBooks = new ArrayList<>();
        File file = new File(path);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String parts[] = line.split(" ");

                if (parts[0].equals(name)) {
                    String title = scanner.nextLine();
                    int quantity = Integer.parseInt(scanner.nextLine());
                    int price = (int) Float.parseFloat(scanner.nextLine());

                    Book foundedBook = new Book(parts[0], title, quantity, price);
                    foundedBooks.add(foundedBook);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return foundedBooks;
    }
}
