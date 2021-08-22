package aluno.base;

import cliente.IRHService;

public class Terceirizado extends Funcionario{

    private boolean insalubre;
    private final double SALARIO_INSALUBRE = 1500;
    private final double SALARIO_NAO_INSALUBRE = 1000;

    public Terceirizado(String cpf, String nome, boolean insalubre) {
        super(cpf, nome);
        this.insalubre = insalubre;
        setTipo(IRHService.Tipo.TERC);
    }

    @Override
    public double getSalario() {
        if(insalubre)
            return SALARIO_INSALUBRE;

        return SALARIO_NAO_INSALUBRE;
    }
}