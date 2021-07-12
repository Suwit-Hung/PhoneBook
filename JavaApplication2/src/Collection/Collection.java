package Collection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Collection {
        private static final String DATA_PATH = "src/contacts.csv";
        private static void data(Map<String, List<String>> phonebook) {
        try (PrintWriter writer = new PrintWriter(DATA_PATH)) {
            if (!phonebook.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : phonebook.entrySet()) {
                    String line = String.format("%s,\"%s\"",
                            entry.getKey(), entry.getValue().toString().replaceAll("\\[|]", ""));
                    writer.println(line);
                }
            }

        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }
        private static void load(Map<String, List<String>> phonebook) {
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
                    phonebook.put(matcher.group(1), Arrays.asList(numbers));
                }
            }

        } catch (IOException ioex) {
            System.err.println("Could not load contacts, phone book is empty!");
        }
    }
    private String name,number,menu,delete;
    private Scanner in = new Scanner(System.in);
    void menu(){
                         System.out.println("~~~~Select menu for use~~~~");
                         System.out.println("1. : for Add new Name&Phone");
                         System.out.println("2. : for Search");
                         System.out.println("3. : for Delete");
                         System.out.println("4. : for Display list");
                         System.out.println("5. : for Edit Phonenumber (detele/addnewnumber)");
                         System.out.println(">>Exit<<");
                         System.out.println("-----------------------------------------");
                         System.out.print("Select Menu for use :  >  ");
                         Map<String, List<String>> contacts = new TreeMap<>();
                         load(contacts);
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
                                            set(contacts);
                                    }else if(menu.equals("!help")){
                                            menu();
                                    }
                         System.out.print("\n >  ");
                         }while(menu.equals("exit")==false); 
    }
    void add(Map<String,List<String>>phonebook){
                while (true) {
                System.out.println("Enter Name.");
                System.out.print(">     ");
                name = in.nextLine().trim();
                        if (name.matches("^.{2,50}$")) {
                        break;
                        } else {
                        System.out.println("Name must be in range 2 - 50 symbols.");
                        }
                }
                while (true) {
                System.out.println("Enter PhoneNumber.");
                System.out.print(">     ");
                number = in.nextLine().trim();
                        if (number.matches("^\\+?[0-9]{3,25}$")) {
                        break;
                        } else {
                        System.out.println("Number may contain only '+', spaces and digits. Min length 3, max length 25.");
                        }
                }
                if (phonebook.containsKey(name)) {
                System.out.printf("'%s' already exists in the phone book!\n", name);
                        if (phonebook.get(name).contains(number)) {
                        System.out.printf("Number %s already available for contact '%s'.\n", number, name);
                        } else {
                        phonebook.get(name).add(number);
                        data(phonebook);
                        System.out.printf("Successfully added number %s for contact '%s'.\n", number, name);
                        }
                } else {
                List<String> numbers = new ArrayList<>();
                numbers.add(number);
                phonebook.put(name, numbers);
                data(phonebook);
                System.out.printf("Successfully added contact '%s' !\n", name);
                }
                System.out.println("=================================");
                System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
    void set(Map<String,List<String>>phonebook){
          System.out.println("Enter name of the list you would like to modify");
          System.out.print(">     ");
          name = in.nextLine().trim();
        if (phonebook.containsKey(name)) {
            List<String> numbers = new ArrayList<>(phonebook.get(name));
            System.out.printf("Current number(s) for %s:\n", name);
            for (String number : numbers) {
                System.out.println(number);
            }
            System.out.println();
            System.out.println("Would you like to add a new number or delete number for this list? [add/delete/cancel]");
            System.out.print(">     ");
            boolean addnum = false;
            boolean delnum = false;
            option:
            while (true) {
                String editoption = in.nextLine().trim().toLowerCase();
                switch (editoption) {
                    case "add":
                        addnum= true;
                        break option;
                    case "delete":
                        delnum= true;
                        break option;
                    case "cancel":
                        System.out.println("not modified!");
                        break option;
                    default:
                        System.out.println("Use 'add' to save a new number, 'delete' to remove an existing number or 'cancel' to go back.");
                        System.out.print(">     ");
                        break;
                }
            }
            if (addnum) {
                while (true) {
                    System.out.println("Enter new number");
                    System.out.print(">     ");
                    String number = in.nextLine().trim();
                    if (number.matches("^\\+?[0-9 ]{3,25}$")) {
                        phonebook.get(name).add(number);
                        data(phonebook);
                        System.out.printf("Number %s was successfully added, record updated!\n", number);
                        break;
                    } else {
                        System.out.println("Number may contain only '+', spaces and digits. Min length 3, max length 25");
                    }
                }
            }
            if (delnum) {
                while (true) {
                    System.out.println("Enter the number you want to delete");
                    System.out.print(">     ");
                    String number = in.nextLine().trim();
                    if (numbers.contains(number)) {
                        numbers.remove(number);
                        phonebook.put(name, numbers);
                        data(phonebook);
                        System.out.printf("Number %s was removed from the record for '%s'\n", number, name);
                        break;
                    } else {
                        System.out.printf("Number does not exist! Current number(s) for %s:\n", name);
                        for (String num : numbers) {
                            System.out.println(num);
                        }
                    }
                }
            }
        } else {
            System.out.println("Sorry, name not found!");
        }  
        System.out.println("=================================");
        System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
    void remove(Map<String,List<String>>phonebook){
            System.out.println("Enter name to be deleted");
            System.out.print(">     ");
            delete = in.nextLine().trim();
            if (phonebook.containsKey(delete)) {
            System.out.printf("Contact '%s' will be deleted. Are you sure? [Y/N]:\n", name);
            System.out.print(">     ");
            String confirmation = in.nextLine().trim().toLowerCase();
            confirm:
                    while (true) {
                        switch (confirmation) {
                            case "y":
                            phonebook.remove(delete);
                            data(phonebook);
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
    void search(Map<String,List<String>>phonebook){
            System.out.println("Enter Name or Number you want to search");
            System.out.print(">     ");
            String search = in.nextLine().trim();
            if(search.matches("^.{2,50}$")) {
                        if (phonebook.containsKey(search)) {
                            System.out.println(search);
                                for (String number : phonebook.get(search)) {
                                    System.out.println(number);
                                }
                        }else {
                                for (Map.Entry<String, List<String>> entry : phonebook.entrySet()) {
                                        if (entry.getValue().contains(search)) {
                                            System.out.println(entry.getKey());
                                            System.out.println(search);
                                            break;
                                        }else{System.out.println("nothing found!");}
                                }
                        }                            
            }else{System.out.println("nothing found!");}
            System.out.println("=================================");
            System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
    void display(Map<String,List<String>>phonebook){
        if (!phonebook.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : phonebook.entrySet()) {
                System.out.println(entry.getKey());
                for (String number : entry.getValue()) {
                    System.out.println(number);
                }
                System.out.println();
            }
        } else {
            System.out.println("No records found, the phonebook is empty!");
        }
        System.out.println("=================================");
        System.out.println("For a list menu of valid commands use '!help' or commands 'exit' to quit");
    }
}
