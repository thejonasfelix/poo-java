package school.sptech;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContaCorrente {
    private String titular;
    private String agencia;
    private String numero;
    private List<Operacao> operacoes = new ArrayList<>();


    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public ContaCorrente(String titular, String agencia, String numero, List<Operacao> operacoes) {
        this.titular = titular;
        this.agencia = agencia;
        this.numero = numero;
        this.operacoes = operacoes;
    }

    void adicionarOperacao(String categoria, String descricao, Double valor){
        if (categoria == null || categoria.isBlank() || descricao == null || descricao.isBlank() || valor == null || valor <= 0) {
            return;
        }
        Operacao novaOperacao = new Operacao(categoria, descricao, valor);
        operacoes.add((novaOperacao));
    }

    Double obterSaldo(){
        if (operacoes == null) {
            return 0.0;
        }
        Double saldo = 0.0;
        for (Operacao operacao : operacoes) {
            saldo += operacao.getValor();
        }
        return saldo;
    }

    List<Operacao> buscarOperacoesPorCategoria(String categoria){
        List<Operacao> listaAuxiliar = new ArrayList();
        if (categoria == null) {
            return listaAuxiliar;
        }
        for (Operacao operacao : operacoes) {
            if (operacao.getCategoria().equalsIgnoreCase(categoria)) {
                listaAuxiliar.add(operacao);
            }
        }
        return listaAuxiliar;
    }


    List<Operacao> buscarOperacoesSaida(){
        List<Operacao> listaAuxiliar = new ArrayList<>();
        for (Operacao operacao : operacoes) {
            if (operacao.getValor() != null && operacao.getValor() < 0.0) {
                listaAuxiliar.add(operacao);
            }
        }
        return listaAuxiliar;

    }

    List<Operacao> buscarOperacoesPorValor(Double valor){
        List<Operacao> listaAuxiliar = new ArrayList<>();
        if (valor == null) {
            return listaAuxiliar;
        }
        for (Operacao operacao : operacoes) {
            if (operacao.getValor() != null && operacao.getValor().equals(valor)) {
                listaAuxiliar.add(operacao);
            }
        }
        return listaAuxiliar;

    }

    List<Operacao> buscarOperacoesPorDescricao(String descricao){
        List<Operacao> listaAuxiliar = new ArrayList<>();
        if (descricao == null) {
            return listaAuxiliar;
        }
        String busca = descricao.toLowerCase(Locale.ROOT);
        for (Operacao operacao : operacoes) {
            if (operacao.getDescricao().toLowerCase(Locale.ROOT).contains(busca)) {
                listaAuxiliar.add(operacao);
            }
        }
        return listaAuxiliar;

    }

    Double buscarMenorValor(){
        if (operacoes == null || operacoes.isEmpty()) {
            return 0.0;
        }

        Double numAuxiliar = operacoes.get(0).getValor();
        for (Operacao operacao : operacoes) {
            if (operacao.getValor() < numAuxiliar) {
                numAuxiliar = operacao.getValor();
            }
        }
        return numAuxiliar;
    }

    Double obterSaldoPorCategoria(String categoria){
        if (categoria == null || categoria.isEmpty()) {
            return 0.0;
        }
        Double saldo = 0.0;
        for (Operacao operacao : operacoes) {
            if (operacao.getCategoria().equalsIgnoreCase(categoria)) {
                saldo += operacao.getValor();
            }
        }
        return saldo;
    }
}
