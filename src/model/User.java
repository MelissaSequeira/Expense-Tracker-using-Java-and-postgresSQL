package model;

public class User {

    private int uId;
    private String uName;
//    private String password;
    public User(int uId,String uName) {
        this.uId = uId;
        this.uName = uName;
    }

    public String getuName() {
        return uName;
    }

    public int getuId() {
        return uId;
    }
    public void show(){
        System.out.println("model.User id: "+getuId());
        System.out.println("model.User name: "+getuName());
    }
}
