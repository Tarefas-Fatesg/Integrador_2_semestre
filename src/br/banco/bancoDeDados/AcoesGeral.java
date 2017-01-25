/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.banco.bancoDeDados;

import br.banco.tratamento.ContaCorrente;
import java.sql.Connection;
import br.banco.tratamento.Pessoa;
import br.banco.tratamento.VerificaCPF;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.banco.tratamento.Pessoa;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author harlock
 */
public class AcoesGeral {

    // Variaveis
    private Connection conectar = null;
    VerificaCPF verifica;
    private boolean comfirmacao = false;
    private boolean eContaConjunta = false;
    private int idUsuarioLogado;
    private int idAuxiliarConta;
    private int[] contas;
    private String[] dadosDaConta;

    public int getidUsuarioLogado() {
        return idUsuarioLogado;
    }
//    public int[] getNumeroDaConta() throws Exception {
//        if(getContas()){
//        return contas;
//        }else{
//            throw new Exception("ALGO DEU ERRADO");
//        }
//    }
    // Mantem conexao

    public AcoesGeral() {
        conectar = Conexao.getConexao();
        verifica = new VerificaCPF();
        contas = new int[2];
    }

    public boolean verificarCPFValido(Pessoa pessoa) {
        return verifica.isCPF(pessoa.getCPF());

    }

    public boolean CPFCadastrado(Pessoa pessoa) throws Exception {
        try {
            String comando = "select CPF from sql597954.usuarios where CPF =  ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, pessoa.getCPF());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("cpf").equalsIgnoreCase(pessoa.getCPF())) {
                comfirmacao = true;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Realiza Login
    public boolean validaLogin(Pessoa pessoa) throws Exception {
        try {
            if (!comfirmacao) {
                throw new Exception("CPF invalido");
            }

            String comando = "select * from usuarios where senha = ? AND CPF = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, pessoa.getSenha());
            ps.setString(2, pessoa.getCPF());
            ResultSet rs = ps.executeQuery();
            rs.next();
            String senhaDB = rs.getString("senha");
            String senhaPessoa = pessoa.getSenha();
            if (!senhaDB.equalsIgnoreCase(senhaPessoa)) {
                throw new Exception("Senha Invalida");
            }
            if (senhaDB.equalsIgnoreCase(senhaPessoa)) {
                idUsuarioLogado = Integer.parseInt(rs.getString("ID_user"));
                pesquisaAuxilarDaConta();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw e;
        }

    }

    public String eFuncionario() throws Exception {
        try {
            String comando = "SELECT * FROM sql597954.Funcionarios where id_user = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, String.valueOf(idUsuarioLogado));
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return "falso";
            } else {
                return rs.getString("Tipo");
            }
//            String tipo = rs.getString("Tipo");
//            if(rs.getString("Tipo").equalsIgnoreCase(null)){
//                return "falso";
//            }else{
//                return rs.getString("Tipo");
//            }
        } catch (Exception e) {
            throw e;
        }
    }

    private int[] pesquisaAuxilarDaConta() throws Exception {
        try {
            int[] usuariosID = new int[3];
            String comando = "SELECT * FROM sql597954.auxiliarConta where id_user = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, String.valueOf(idUsuarioLogado));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuariosID[0] = Integer.valueOf(rs.getString("id_aux"));
                usuariosID[1] = Integer.valueOf(rs.getString("ID_user"));
                idAuxiliarConta = usuariosID[1];
                return usuariosID;
            } else {
                throw new Exception("Algo deu errado na comunicação com banco");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public int[] getContas() throws Exception {
        try {
            int i = 0;
            int nC[] = new int[2];
            String comando = "SELECT * FROM sql597954.auxiliarConta where id_user = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, String.valueOf(idAuxiliarConta));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nC[i] = rs.getInt("ID_aux");
                i++;
            }
            rs.close();
//            contas = nC.clone();
            return nC;
        } catch (Exception e) {
            throw e;
        }

    }
//    public String[] getDadosCompletosCliente(){
//        
//    }
    ///////////////////////////////////////////////////////////////////////////
    //////////                  CONTA CLIENTE                       ///////////
    ///////////////////////////////////////////////////////////////////////////

    public String[] retornaContaDados(int ID_conta) throws Exception {
        String[] dados = new String[2];
        try {
            String comando = "SELECT * FROM sql597954.Conta where id_aux = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, String.valueOf(ID_conta));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                dados[0] = rs.getString("saldo");
                dados[1] = rs.getString("id_aux");
            }
            return dados;
        } catch (Exception e) {
            throw e;
        }
    }

    public Pessoa dadosDaConta(int id_cliente, int id_conta) throws Exception {
        try {
            Pessoa dadosDaContaCliente = new Pessoa();
            String comando = "SELECT u.nome,ag.NomeAgencia, u.CPF,c.saldo,c.Conjunta  FROM Conta c "
                    + "inner join auxiliarConta aux"
                    + "on c.id_aux=aux.id_auxConta"
                    + "inner join usuarios u on u.ID_user=aux.ID_user"
                    + "inner join Agencia ag on c.ID_agencia=ag.ID_ag"
                    + "where aux.ID_user = ? and c.ID_cont = ?";
            PreparedStatement ps = conectar.prepareStatement(comando);
            ps.setString(1, String.valueOf(id_cliente));
            ps.setString(1, String.valueOf(id_conta));
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new Exception("Algo deu errado aos buscar os seus dados");

            } else {
                dadosDaContaCliente.setNome(rs.getString("nome"));
                dadosDaContaCliente.setCPF(rs.getString("CPF"));
                dadosDaContaCliente.setAgencia(rs.getString("NomeAgencia"));
                dadosDaContaCliente.setSaldo(rs.getString("saldo"));
                dadosDaContaCliente.setConjuta(rs.getString("Conjunta"));
                if (dadosDaContaCliente.getConjuta() != "0") {
                    eContaConjunta = true;
                }
                return dadosDaContaCliente;
            }
        } catch (Exception e) {
            throw e;
        }

    }
//    public ContaCorrente pessoaConta(){
//        
//    }
}
