package sample.Asset;

public class Member {
    private String Id;
    private String Rol;
    private String Username;
    private String Email;

    public Member (String id, String rol, String username, String email) {
        Id = id;
        Rol = rol;
        Username = username;
        Email = email;
    }

    public String getId() {
        return Id;
    }

    public String getRol() {
        return Rol;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }
}
