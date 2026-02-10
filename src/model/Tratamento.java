package model;

public class Tratamento {

    private final int id;
    private final String cpfPaciente;
    private final String cpfProfissional; // <-- NOVO CAMPO
    private final String data;
    private final String denteRegiao;
    private final String descricao;

    // Construtor atualizado
    public Tratamento(String cpfPaciente, String cpfProfissional, String data, String denteRegiao, String descricao) {
        this.id = 0; // O ID será gerado pelo banco
        this.cpfPaciente = cpfPaciente;
        this.cpfProfissional = cpfProfissional;
        this.data = data;
        this.denteRegiao = denteRegiao;
        this.descricao = descricao;
    }

    // Construtor atualizado
    public Tratamento(int id, String cpfPaciente, String cpfProfissional, String data, String denteRegiao, String descricao) {
        this.id = id;
        this.cpfPaciente = cpfPaciente;
        this.cpfProfissional = cpfProfissional;
        this.data = data;
        this.denteRegiao = denteRegiao;
        this.descricao = descricao;
    }

    // Getters
    public int getId() { return id; }
    public String getCpfPaciente() { return cpfPaciente; }
    public String getCpfProfissional() { return cpfProfissional; } // <-- NOVO GETTER
    public String getData() { return data; }
    public String getDenteRegiao() { return denteRegiao; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        // Formato: [Data] Dente/Região: Descrição (Profissional: CPF)
        String dente = (denteRegiao != null && !denteRegiao.isEmpty()) ? denteRegiao + ": " : "";
        String prof = (cpfProfissional != null && !cpfProfissional.isEmpty()) ? " (Prof: " + cpfProfissional + ")" : "";
        return String.format("[%s] %s%s%s", data, dente, descricao, prof);
    }
}