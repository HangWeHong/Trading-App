public class Test {
    public static void main(String[] args) {
        User user1=new User("Testing", "Testing@gmail.com", "12345","Malaysian", 23, 145627837);
       System.out.println(user1.checkUser("Testing", "12345"));
        user1.getRole();
    }
}
