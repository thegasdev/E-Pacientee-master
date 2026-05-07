package model;

public class Anamnese {

    private String cpfPaciente;
    private String alergias;
    private String medicamentosUso;
    private String doencasPrevias;
    private boolean fumante; // Usaremos boolean
    private boolean gravida; // Usaremos boolean
    private String observacoes;

    // Construtor para uma nova anamnese
    public Anamnese(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
        this.alergias = "";
        this.medicamentosUso = "";
        this.doencasPrevias = "";
        this.fumante = false;
        this.gravida = false;
        this.observacoes = "";
    }

    // Construtor para carregar dados do banco
    public Anamnese(String cpfPaciente, String alergias, String medicamentosUso, String doencasPrevias, boolean fumante, boolean gravida, String observacoes) {
        this.cpfPaciente = cpfPaciente;
        this.alergias = alergias;
        this.medicamentosUso = medicamentosUso;
        this.doencasPrevias = doencasPrevias;
        this.fumante = fumante;
        this.gravida = gravida;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public String getCpfPaciente() { return cpfPaciente; }
    public String getAlergias() { return alergias; }
    public String getMedicamentosUso() { return medicamentosUso; }
    public String getDoencasPrevias() { return doencasPrevias; }
    public boolean isFumante() { return fumante; }
    public boolean isGravida() { return gravida; }
    public String getObservacoes() { return observacoes; }
}