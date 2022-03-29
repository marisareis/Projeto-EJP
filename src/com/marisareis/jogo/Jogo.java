package com.marisareis.jogo;

import java.sql.*;
import com.marisareis.dal.ModuloConexao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Jogo {
    private Connection conexao = ModuloConexao.conector();
    private Statement stmt;
    private ResultSet rs;
    
    private String palavraChave;
    private char[] aux;
    private List<String> letrasPalavraChave = new ArrayList<>();
    private List<String> lacunasPalavraChave = new ArrayList<>();
    private int tentativas = 0;
    
    
    Jogo(){
        gerarPalavraChave();
        gerarLacunas();
    }
    
    public String atualizarJogo(){
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < palavraChave.length(); i++){
            stringBuilder.append(lacunasPalavraChave.get(i));
            stringBuilder.append("  ");
        }
        
        string = String.valueOf(stringBuilder);
        return string;
    }
    
    public void checaPalavra(String jogada) {
	boolean acertouLetra = false;
	for(int i = 0; i < this.palavraChave.length(); i++) {
            
            if(jogada.equalsIgnoreCase(this.letrasPalavraChave.get(i))) {
                this.lacunasPalavraChave.set(i, jogada);
                acertouLetra = true;
            }
        }
        
         if(acertouLetra == false){
            this.tentativas++;
            
         }
            
    }

    public String retornaEnderecoImg(){
        String aux = String.valueOf(this.tentativas);
        String endereco = "/com/marisareis/img/hangman" + aux + ".png";
        
        return endereco;
    }
    
    public boolean gameOver(){
        boolean fimDeJogo = false;
        if(completouBoneco() || acertouPalavra()){
            fimDeJogo = true;
        }
        
        return fimDeJogo;
    }
    
    public boolean acertouPalavra(){
        boolean acertou = false;
        for(int i = 0; i < this.palavraChave.length(); i++){
           if(this.lacunasPalavraChave.get(i).equalsIgnoreCase(this.letrasPalavraChave.get(i))) {
               acertou = true;
           } else {
               acertou = false;
               break;
           }
        }
        
        return acertou;
    }
    
    public boolean completouBoneco(){
        boolean completou = false;
        if(this.tentativas == 6){
            completou = true;
        }
        
        return completou;
    }
    
    private void gerarPalavraChave(){
        Random gerador = new Random();
        int numId = gerador.nextInt(100);
        
        if(numId == 0){
            while(numId == 0) {
                numId = gerador.nextInt(100);
            }
        }
        
        String idGerado = String.valueOf(numId);
        String sql = "select palavra from tb_palavras where id=" + idGerado;
        
        try{
            stmt = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            rs.first();
            this.palavraChave = rs.getString("palavra");
            
            rs.close();
            stmt.close();
            conexao.close();
            
        } catch(SQLException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        char[] aux = this.palavraChave.toCharArray();
        for(int i = 0; i < this.palavraChave.length(); i++){
            letrasPalavraChave.add(String.valueOf(aux[i]));
	}
        
    }
    
    private void gerarLacunas(){
        for(int i = 0; i < this.palavraChave.length(); i++){
            lacunasPalavraChave.add("__");
                                    
        }
    }
    
    public String getPalavraChave(){
        return this.palavraChave;
    }
    
}
