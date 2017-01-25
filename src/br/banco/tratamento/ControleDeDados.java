/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.banco.tratamento;
import br.banco.bancoDeDados.AcoesGeral;
/**
 *
 * @author harlock
 */
public class ControleDeDados  implements InterfacePega{
    private int contaLogadaID;
    AcoesGeral aG;
    Pessoa p;
    public ControleDeDados(){
        aG = new AcoesGeral();
    }

    @Override
    public void SetcontaLogada(int conta) {
       contaLogadaID =  conta;
    }

    public boolean cpfCadastrado(Pessoa pessoa) throws Exception{
        return aG.CPFCadastrado(pessoa);
    }
    //                                                                  //
    public boolean verificaCPF(Pessoa pessoa){
        return aG.verificarCPFValido(pessoa);
    }
    public boolean confirmarLogin(Pessoa pessoa) throws Exception{
        
        return aG.validaLogin(pessoa);
    }
    public int[] pegaContasExistentes() throws Exception{
         return aG.getContas();
         
    }
    public String funcionarioLogado() throws Exception{
        return aG.eFuncionario();
    }
    public int getUsuarioLogado(){
        return aG.getidUsuarioLogado();
    }
    ///////////////////////////////////////////////////////////////////////////
    //////////                  CONTA CLIENTE                       ///////////
    ///////////////////////////////////////////////////////////////////////////
//    public String[] dadosDoClienteConta(int IDconta) throws Exception{
//        return aG.retornaContaDados(IDconta);
//    }

    public Pessoa obtemdadosDaConta(int id_cliente, int id_conta) throws Exception{
        return aG.dadosDaConta(id_cliente, id_conta);
    }
}
