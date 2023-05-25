package Model;



public class User {
    public static User user;
    private String username;
    private String email;
    private String password;
    private String nationality;
    private int age;
    private String phoneNum;
    private String role;
    private String notificationLanguage;
    private String department;
    private int balance;
    private int PL_Points;

    
    public User() {
    }


    public User(String username, String email, String password, String nationality, int age, String phoneNum,String role,String notificationLanguage,String department) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.phoneNum = phoneNum;
        this.role=role;
        this.notificationLanguage=notificationLanguage;
        this.department=department;
    }
    public User(String username, String email, String password, String nationality, int age, String phoneNum,String role,int balance,int PL_Points) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.phoneNum = phoneNum;
        this.role=role;
        this.balance=balance;
        this.PL_Points=PL_Points;
    }
    public User(String username, String email, String nationality, int age, String phoneNum,int balance,int PL_Points) {
        this.username = username;
        this.email = email;
        this.nationality = nationality;
        this.age = age;
        this.phoneNum = phoneNum;
        this.balance=balance;
        this.PL_Points=PL_Points;
    }
    


  


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    

    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public static User getUser() {
        return user;
    }


    public static void setUser(User user) {
        User.user = user;
    }


    public String getNotificationLanguage() {
        return notificationLanguage;
    }


    public void setNotificationLanguage(String notificationLanguage) {
        this.notificationLanguage = notificationLanguage;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public int getBalance() {
        return balance;
    }


    public void setBalance(int balance) {
        this.balance = balance;
    }


    public int getPL_Points() {
        return PL_Points;
    }


    public void setPL_Points(int pL_Points) {
        PL_Points = pL_Points;
    }
}
