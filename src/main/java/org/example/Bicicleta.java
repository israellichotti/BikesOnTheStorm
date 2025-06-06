package org.example;

public class Bicicleta {
    int id;
    String modelo;
    String marca;
    String tamanhoDoAro;
    double aluguelBase;
    double aluguelDia;
    boolean estaAlugada;
    double aluguelTotal;


    public Bicicleta(int id, String modelo, String marca, String tamanhoDoAro, double aluguelBase, double aluguelDia, boolean estaAlugada) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.tamanhoDoAro = tamanhoDoAro;
        this.aluguelBase = aluguelBase;
        this.aluguelDia = aluguelDia;
        this.estaAlugada = estaAlugada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanhoDoAro() {
        return tamanhoDoAro;
    }

    public void setTamanhoDoAro(String tamanhoDoAro) {
        this.tamanhoDoAro = tamanhoDoAro;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public void setAluguelBase(double aluguelBase) {
        this.aluguelBase = aluguelBase;
    }

    public double getAluguelDia() {
        return aluguelDia;
    }

    public void setAluguelDia(double aluguelDia) {
        this.aluguelDia = aluguelDia;
    }

    public boolean isEstaAlugada() {
        return estaAlugada;
    }

    public void setEstaAlugada(boolean estaAlugada) {
        this.estaAlugada = estaAlugada;
    }


    public void calculaAluguel(double aluguelBase, double aluguelDia) {

        aluguelTotal=aluguelBase+aluguelDia;

    }



    @Override
    public String toString() {
        return "Bicicleta{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", tamanhoDoAro='" + tamanhoDoAro + '\'' +
                ", aluguelBase=" + aluguelBase +
                ", aluguelDia=" + aluguelDia +
                ", estaAlugada=" + estaAlugada +
                '}';
    }


}
