package model;

public class User {

    private String username;
    private String password;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String gmail;

    public User(String username, String password, String cpf, String nome, String sobrenome, String gmail) {
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.gmail = gmail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getGmail() {
        return gmail;
    }

    // Opcional: Métodos setters caso precise modificar essas informações depois
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
