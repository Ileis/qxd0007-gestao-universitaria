package aluno.manager;

import aluno.base.Funcionario;
import cliente.IRHService;

import java.util.*;

public class RHService implements IRHService {

    private class Bonus{
        private double diaria;
        private double gratificacao;

        public Bonus(){
            diaria = 0;
            gratificacao = 0;
        }

        public double getDiaria() {
            return diaria;
        }

        public double getGratificacao() {
            return gratificacao;
        }

        public void recebeDiaria(double valor){
            diaria += valor;
        }

        public void recebeGratificacao(double valor){
            gratificacao += valor;
        }
    }

    private HashMap<String, Funcionario> folhaDePagamento;
    private HashMap<Funcionario, Bonus> bonus;
    private final double SALARIO_STA_MAXIMO = 1000 + 100 * 30;
    private final double DIARIA = 100;
    private final double DIARIA_MAXIMA_PROF = DIARIA * 3;
    private final double DIARIA_MAXIMA_STA = DIARIA * 1;

    public RHService(){
        folhaDePagamento = new HashMap<String, Funcionario>();
        bonus = new HashMap<Funcionario, Bonus>();
    }

    @Override
    public boolean cadastrar(Funcionario funcionario) {

        if(funcionarioValido(funcionario) && !folhaDePagamento.containsKey(funcionario.getCpf())){
            folhaDePagamento.put(funcionario.getCpf(), funcionario);
            bonus.put(funcionario, new Bonus());

            return true;
        }
        return false;
    }

    private boolean funcionarioValido(Funcionario funcionario){
        if(funcionario.getTipo() == Tipo.PROF)
            return funcionario.getSalario() != 0;

        if(funcionario.getTipo() == Tipo.STA)
            return funcionario.getSalario() <= SALARIO_STA_MAXIMO;

        return true;
    }

    @Override
    public boolean remover(String cpf) {

        if(folhaDePagamento.containsKey(cpf)){
            bonus.remove(folhaDePagamento.get(cpf));
            folhaDePagamento.remove(cpf);

            return true;
        }
        return false;
    }

    @Override
    public Funcionario obterFuncionario(String cpf) {
        return folhaDePagamento.getOrDefault(cpf, null);
    }

    @Override
    public List<Funcionario> getFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>(folhaDePagamento.values());

        Collections.sort(funcionarios);

        return funcionarios;
    }

    @Override
    public List<Funcionario> getFuncionariosPorCategoria(Tipo tipo) {

        ArrayList<Funcionario> funcionariosCategoria = new ArrayList<Funcionario>();
        List<Funcionario> funcionarios = getFuncionarios();

        for(Funcionario funcionario : funcionarios){
            if(funcionario.getTipo() == tipo)
                funcionariosCategoria.add(funcionario);
        }
        return funcionariosCategoria;
    }

    @Override
    public int getTotalFuncionarios() {
        return folhaDePagamento.size();
    }

    @Override
    public boolean solicitarDiaria(String cpf) {
        Funcionario funcionario = folhaDePagamento.get(cpf);
        return pagarDiaria(funcionario);
    }

    private boolean pagarDiaria(Funcionario funcionario) {

        if(funcionario.getTipo() == Tipo.PROF){
            if(bonus.get(funcionario).getDiaria() < DIARIA_MAXIMA_PROF)
                bonus.get(funcionario).recebeDiaria(DIARIA);
            else
                return false;
        }else if(funcionario.getTipo() == Tipo.STA) {
            if(bonus.get(funcionario).getDiaria() < DIARIA)
                bonus.get(funcionario).recebeDiaria(DIARIA);
            else
                return false;
        }else
            return false;

        return true;
    }

    @Override
    public boolean partilharLucros(double valor) {
        List<Funcionario> funcionariosLista = getFuncionarios();

        if(!folhaDePagamento.isEmpty()){
            for(Funcionario funcionario : funcionariosLista){
                bonus.get(funcionario).recebeGratificacao(valor / folhaDePagamento.size());
            }

            return true;
        }
        return false;
    }

    @Override
    public void iniciarMes() {
        List<Funcionario> funcionariosLista = getFuncionarios();

        for(Funcionario funcionario : funcionariosLista){
            bonus.put(funcionario, new Bonus());
        }
    }

    @Override
    public Double calcularSalarioDoFuncionario(String cpf) {
        Double valor = folhaDePagamento.get(cpf).getSalario() +
                       bonus.get(folhaDePagamento.get(cpf)).getDiaria() +
                       bonus.get(folhaDePagamento.get(cpf)).getGratificacao();

        return valor;
    }

    @Override
    public double calcularFolhaDePagamento() {

        List<Funcionario> funcionarios = getFuncionarios();
        double valorFolhaDePagamento = 0;

        for(Funcionario funcionario : funcionarios){
            valorFolhaDePagamento += calcularSalarioDoFuncionario(funcionario.getCpf());
        }

        return valorFolhaDePagamento;
    }
}