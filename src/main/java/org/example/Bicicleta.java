package org.example;

public class Bicicleta {
    private int id;
    private String modelo;
    private String marca;
    private double tamanhoAro;
    private double aluguelBase;
    private double aluguelDia;
    private boolean alugada;

    public Bicicleta(int id, String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia, boolean alugada) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.tamanhoAro = tamanhoAro;
        this.aluguelBase = aluguelBase;
        this.aluguelDia = aluguelDia;
        this.alugada = alugada;
    }

    public Bicicleta(String modelo, String marca, double tamanhoAro, double aluguelBase, double aluguelDia, boolean alugada) {
        this.modelo = modelo;
        this.marca = marca;
        this.tamanhoAro = tamanhoAro;
        this.aluguelBase = aluguelBase;
        this.aluguelDia = aluguelDia;
        this.alugada = alugada;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public double getTamanhoAro() { return tamanhoAro; }
    public void setTamanhoAro(double tamanhoAro) { this.tamanhoAro = tamanhoAro; }
    public double getAluguelBase() { return aluguelBase; }
    public void setAluguelBase(double aluguelBase) { this.aluguelBase = aluguelBase; }
    public double getAluguelDia() { return aluguelDia; }
    public void setAluguelDia(double aluguelDia) { this.aluguelDia = aluguelDia; }
    public boolean isAlugada() { return alugada; }
    public void setAlugada(boolean alugada) { this.alugada = alugada; }
}