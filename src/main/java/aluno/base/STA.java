package aluno.base;

import cliente.IRHService.Tipo;

public class STA extends Funcionario{

    int nivel;

    public STA(String cpf, String nome, int nivel) {
        super(cpf, nome);
        this.nivel = nivel;
        setTipo(Tipo.STA);
    }

    @Override
    public double getSalario() {
        return 1000 + 100 * nivel;
    }

    private int getNivel(){
        return nivel;
    }
}