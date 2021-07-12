package Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class listProduct {
        private static final String DATA_PATH="src/Set/listproduct.csv";
        private static void data_product(Map<String,List<String>>listproduct){
                try (PrintWriter writer = new PrintWriter(DATA_PATH)){
                        if (!listproduct.isEmpty()) {
                                for (Map.Entry<String, List<String>> entry : listproduct.entrySet()) {
                                String line = String.format("%s,\"%s\"",
                                entry.getKey(), entry.getValue().toString().replaceAll("\\[|]", ""));
                                writer.println(line);
                                }
                        }
                }catch (IOException ioex) {
                    System.err.println(ioex.getMessage());
                 }
        }
        static void load_product(Map<String, List<String>> listproduct) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH))) {
            Pattern pattern = Pattern.compile("^([^,\"]{2,50}),\"([0-9+, ]+)\"$");
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String[] numbers = matcher.group(2).split(",\\s*");
                    listproduct.put(matcher.group(1), Arrays.asList(numbers));
                }
            }
        } catch (IOException ioex) {
            System.err.println("Could not load contacts, phone book is empty!");
        }
    }
        private String id,name,unit,price,menu,delete,li;
        private int count;
        private Scanner in = new Scanner(System.in);
        void menu(){
                         System.out.println("~~~~Select menu for use~~~~");
                         System.out.println("1. : for Add New ListProduct");
                         System.out.println("2. : for Search");
                         System.out.println("3. : for Delete");
                         System.out.println("4. : for Display list");
                         System.out.println("5. : for Edit ListProduct (detele/addnewProduct)");
                         System.out.println(">>Exit<<");
                         System.out.println("-----------------------------------------");
                         System.out.print("Select Menu for use :  >  ");
                         Map<String, List<String>> contacts = new TreeMap<>();
                         load_product(contacts);
                         do{
                         menu = in.nextLine().trim();
                          if(menu.equals("1")){
                                            add(contacts);
                                    }else if(menu.equals("2")){
                                            search(contacts);
                                    }else if(menu.equals("3")){
                                            remove(contacts);
                                    }else if(menu.equals("4")){
                                            display(contacts);
                                    }else if(menu.equals("5")){
                                       //     set(contacts);
                                    }else if(menu.equals("!help")){
                                            menu();
                                    }
                         System.out.print("\n >  ");
                         }while(menu.equals("exit")==false); 
    }
        void add(Map<String,List<String>>listproduct){
                while (true) {
                System.out.println("Enter ID_Product.");
                System.out.print(">     ");
                id = in.nextLine().trim();
                        if (id.matches("^[0-9]{3,5}$")) {
                        break;
                        } else {
                        System.out.println("กรุณาป้อนรหัสสินค้าเฉพาะตัวเลข จำนวนตัวเลข3-5หลัก");
                        }
                }
                while (true) {
                System.out.println("Enter NameProduct.");
                System.out.print(">     ");
                name = in.nextLine().trim();
                        if (name.matches("^.[a-z,A-Z]{2,25}$")) {
                        break;
                        } else {
                        System.out.println("ชื่อสินค้าควรป้อนตัวอักษรต่ำสุด2ตัว มากสุด25ตัว(ป้อนได้เฉพาะภาษาอังกฤษ)");
                        }
                }
                while (true) {
                System.out.println("Enter UnitProduct.");
                System.out.print(">     ");
                unit = in.nextLine().trim();
                        if (unit.matches("^[0-9]{1,5}$")) {
                        break;
                        } else {
                        System.out.println("กรุณาป้อนเฉพาะตัวเลข จำนวน1-5หลัก");
                        }
                }
                while (true) {
                System.out.println("Enter PriceProduct.");
                System.out.print(">     ");
                price = in.nextLine().trim();
                        if (price.matches("^[0-9]{1,6}$")) {
                        break;
                        } else {
                        System.out.println("กรุณาป้อนเฉพาะตัวเลข จำนวน1-6หลัก");
                        }
                }
                if (listproduct.containsKey(id)) {
                System.out.printf("'%s' มีข้อมูลรหัสสินค้านี้แล้ว\n", id);
                        if (listproduct.get(id).contains(name)) {
                        System.out.printf("มีชื่อสินค้านี้ในรายการแล้ว '%s'.\n", id, name);
                        } else {
                            listproduct.get(id).add(name);
                            data_product(listproduct);
                            System.out.printf("เพิ่มรายชื่อสินค้าเสร็จสิ้น '%s'.\n", id, name);
                        }
                } else {
                List<String> newlists= new ArrayList<>();
                newlists.add(name);
                newlists.add(unit);
                newlists.add(price);
                listproduct.put(id,newlists);
                data_product(listproduct);
                System.out.printf("เพิ่มข้อมูลสินค้าเสร็จสิ้น '%s' !\n", name);
                }
                System.out.println("=================================");
                System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
        void search(Map<String,List<String>>listproduct){
            System.out.println("Enter ID_Product or Product_Name for search");
            System.out.print(">     ");
            String search = in.nextLine().trim();
            if(search.matches("^.{2,50}$")) {
                        if (listproduct.containsKey(search)) {
                            System.out.println(search);
                                for (String name : listproduct.get(search)) {
                                    System.out.println(name);
                                }
                        }else {
                                for (Map.Entry<String, List<String>> entry : listproduct.entrySet()) {
                                        if (entry.getValue().contains(search)) {
                                            System.out.println(entry.getKey());
                                            System.out.println(search);
                                            break;
                                        }else{System.out.println("ไม่พบข้อมูลสินค้านี้!");}
                                }
                        }
            }else{System.out.println("ไม่พบข้อมูลสินค้านี้!");}
            System.out.println("=================================");
            System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
        void remove(Map<String,List<String>>listproduct){
            System.out.println("Enter List ID_Product to be deleted");
            System.out.print(">     ");
            delete = in.nextLine().trim();
            if (listproduct.containsKey(delete)) {
            System.out.printf("Contact '%s' will be deleted. Are you sure? [Y/N]:\n", name);
            System.out.print(">     ");
            String confirmation = in.nextLine().trim().toLowerCase();
            confirm:
                    while (true) {
                        switch (confirmation) {
                            case "y":
                            listproduct.remove(delete);
                            data_product(listproduct);
                            System.out.println("Contact was deleted successfully!");
                            break confirm;
                            case "n":
                            break confirm;
                            default:
                            System.out.println("Delete contact? [Y/N]\n >  ");
                            break;
                        }
                        confirmation = in.nextLine().trim().toLowerCase();
                    }
            } else {
            System.out.println("Sorry, name not found!");
            }
            System.out.println("=================================");
            System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
        void display(Map<String,List<String>>listproduct){
            if (!listproduct.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : listproduct.entrySet()) {
                    System.out.println(entry.getKey());
                    for (String name : entry.getValue()) {
                        System.out.println(name);
                    }
                    System.out.println();
                }
            } else {
                System.out.println("No records found, the phonebook is empty!");
            }
            System.out.println("=================================");
            System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
        }
        //private boolean isFull(){return count==listproduct.length;}
        private boolean isEmpty(){return count!=0;}
}
