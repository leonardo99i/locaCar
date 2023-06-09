package locadora;

import java.io.Serializable;

public class Carro implements Serializable{
    private int id;
    private String marca;
    private String modelo;
    private boolean disponivel;

    public Carro(int id, String marca, String modelo, boolean b){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.disponivel = true;
    }

    public int getId(){
        return id;
    }

    public String getMarca(){
        return marca;
    }

    public boolean isDisponivel(){
        return disponivel;
    }

    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }


    @Override
    public String toString(){
        return "Carro [id=" + id +", marca=" + marca + " modelo=" + modelo +", disponivel=" + disponivel + "]";
    }  
}
