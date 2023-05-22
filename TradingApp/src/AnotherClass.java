public class AnotherClass {
    public static void main(String[] args) {
        User userFromUserExample = UserCheck.getUser1();

        // Access user properties
        System.out.println("Username: " + userFromUserExample.getUsername());
        System.out.println("Email: " + userFromUserExample.getEmail());
        System.out.println("Password: " + userFromUserExample.getPassword());
        System.out.println("Nationality: " + userFromUserExample.getNationality());
        System.out.println("Age: " + userFromUserExample.getAge());
        System.out.println("Phone Number: " + userFromUserExample.getPhoneNum());

        // Call user methods if any
        // userFromUserExample.someMethod();
    }
}
