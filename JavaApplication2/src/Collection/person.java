package Collection;
public class person {
                private String name,number;
                public person(String name, String lastname,String number) {
                          this.name = name;
                          this.number = number;
                }

                public person() {
                }
                
                public String getName() {
                          return name;
                }

                public String getNumber() {
                          return number;
                }
                public void changeNumber(String newNumber) {
                          this.number = newNumber;
                }

    @Override
    public String toString() {
        return "person{" + "name=" + name + ", number=" + number + '}';
    }

}
