package aluno.base;

import cliente.IRHService;

public abstract class Funcionario implements Comparable<Funcionario> {

    private String nome;
    private String cpf;
    private IRHService.Tipo tipo;

    Funcionario(String cpf, String nome){
        this.cpf = cpf;
        this.nome = nome;
    }

    @Override
    public int compareTo(Funcionario funcionario) {
        return nome.compareTo(funcionario.getNome());
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public abstract double getSalario();

    public IRHService.Tipo getTipo() {
        return tipo;
    }

    protected void setTipo(IRHService.Tipo tipo) {
        this.tipo = tipo;
    }
}