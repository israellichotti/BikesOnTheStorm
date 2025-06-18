package org.example;

public class Bicicleta {
    int id;
    String modelo;
    String marca;
    String tamanhoAro;
    double aluguelBase;
    double aluguelDia=10;
    boolean seEstaAlugada;
    double aluguelTotal;


    public Bicicleta(int id, String modelo, String marca, String tamanhoAro, double aluguelBase, double aluguelDia, boolean seEstaAlugada, double aluguelTotal) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.tamanhoAro = tamanhoAro;
        this.aluguelBase = aluguelBase;
        this.aluguelDia = aluguelDia;
        this.seEstaAlugada = seEstaAlugada;
        this.aluguelTotal = aluguelTotal;
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

    public String getTamanhoAro() {
        return tamanhoAro;
    }

    public void setTamanhoAro(String tamanhoAro) {
        this.tamanhoAro = tamanhoAro;
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

    public boolean isSeEstaAlugada() {
        return seEstaAlugada;
    }

    public void setSeEstaAlugada(boolean seEstaAlugada) {
        this.seEstaAlugada = seEstaAlugada;
    }

    public double getAluguelTotal() {
        return aluguelTotal;
    }

    public void setAluguelTotal(double aluguelTotal) {
        this.aluguelTotal = aluguelTotal;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", tamanhoAro='" + tamanhoAro + '\'' +
                ", aluguelBase=" + aluguelBase +
                ", aluguelDia=" + aluguelDia +
                ", seEstaAlugada=" + seEstaAlugada +
                ", aluguelTotal=" + aluguelTotal +
                '}';
    }

}
