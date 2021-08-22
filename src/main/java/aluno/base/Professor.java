package aluno.base;

import cliente.IRHService;

public class Professor extends Funcionario {

    private char classe;
    private final double SALARIO_A = 3000;
    private final double SALARIO_B = 5000;
    private final double SALARIO_C = 7000;
    private final double SALARIO_D = 9000;
    private final double SALARIO_E = 11000;

    public Professor(String cpf, String nome, char classe) {
        super(cpf, nome);
        this.classe = classe;
        setTipo(IRHService.Tipo.PROF);
    }

    @Override
    public double getSalario() {
        switch (classe){
            case 'A':
                return SALARIO_A;
            case 'B':
                return SALARIO_B;
            case 'C':
                return SALARIO_C;
            case 'D':
                return SALARIO_D;
            case 'E':
                return SALARIO_E;
        }
        return 0;
    }
}