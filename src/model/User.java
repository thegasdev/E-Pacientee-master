package model;

public class User {

    private String username;
    private String password;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String gmail;
    private String role;
    private String dataNascimento;
    private String telefone;
    private String fotoPath;

    // --- NOVOS CAMPOS ---
    private String cro;
    private String clinicaNome;
    private String clinicaCep;
    private String clinicaLocalizacao;

    // Construtor atualizado para incluir TODOS os campos
    public User(String username, String password, String cpf, String nome, String sobrenome, String gmail, String role,
                String dataNascimento, String telefone, String fotoPath,
                String cro, String clinicaNome, String clinicaCep, String clinicaLocalizacao) {
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.gmail = gmail;
        this.role = role;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.fotoPath = fotoPath;
        this.cro = cro;
        this.clinicaNome = clinicaNome;
        this.clinicaCep = clinicaCep;
        this.clinicaLocalizacao = clinicaLocalizacao;
    }

    // Getters e Setters
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getSobrenome() { return sobrenome; }
    public String getGmail() { return gmail; }
    public String getDataNascimento() { return dataNascimento; }
    public String getTelefone() { return telefone; }
    public String getFotoPath() { return fotoPath; }
    public String getCro() { return cro; }
    public String getClinicaNome() { return clinicaNome; }
    public String getClinicaCep() { return clinicaCep; }
    public String getClinicaLocalizacao() { return clinicaLocalizacao; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public void setGmail(String gmail) { this.gmail = gmail; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setFotoPath(String fotoPath) { this.fotoPath = fotoPath; }
    public void setCro(String cro) { this.cro = cro; }
    public void setClinicaNome(String clinicaNome) { this.clinicaNome = clinicaNome; }
    public void setClinicaCep(String clinicaCep) { this.clinicaCep = clinicaCep; }
    public void setClinicaLocalizacao(String clinicaLocalizacao) { this.clinicaLocalizacao = clinicaLocalizacao; }

    @Override
    public String toString() {
        return nome + " " + sobrenome + " (CPF: " + cpf + ")";
    }
}