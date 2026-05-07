package model;

public class Consulta {

    private int id;
    private String cpfPaciente;
    private String data;
    private String hora;
    private String descricao;
    private String status;
    private String nomePaciente; // Campo extra para exibição

    // Construtor para criar uma nova consulta
    public Consulta(String cpfPaciente, String data, String hora, String descricao) {
        this.cpfPaciente = cpfPaciente;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.status = "Agendada";
    }

    // Construtor para carregar do banco
    public Consulta(int id, String cpfPaciente, String data, String hora, String descricao, String status) {
        this.id = id;
        this.cpfPaciente = cpfPaciente;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getCpfPaciente() { return cpfPaciente; }
    public String getData() { return data; }
    public String getHora() { return hora; }
    public String getDescricao() { return descricao; }
    public String getStatus() { return status; }
    public String getNomePaciente() { return nomePaciente; }

    // Setters (para carregar o nome do paciente depois)
    public void setNomePaciente(String nome) { this.nomePaciente = nome; }

    @Override
    public String toString() {
        // Formato que aparecerá na lista: HH:MM - Nome do Paciente (Descrição)
        return String.format("%s - %s (%s)", hora, nomePaciente, descricao);
    }
}