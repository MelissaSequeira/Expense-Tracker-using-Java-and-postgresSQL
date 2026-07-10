public class User {

    private int uId;
    private String uName;
//    private String password;
private static int nextId = 1;
    public User(String uName) {
        this.uId = nextId++;
        this.uName = uName;
    }

    public String getuName() {
        return uName;
    }

    public int getuId() {
        return uId;
    }
    public void show(){
        System.out.println("User id: "+getuId());
        System.out.println("User name: "+getuName());
    }
}
