package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.util.Ids;

public class Pelicula {
    private final String id = Ids.nuevo("PEL");
    private String titulo;
    private String genero;
    private int duracionMinutos;
    private String clasificacion;
    private String sinopsis;
    private boolean enCartelera = true;

    public Pelicula(String titulo, String genero, int duracionMinutos, String clasificacion, String sinopsis) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.sinopsis = sinopsis;
    }

    public String getId() {
        return id; }
    public String getTitulo() {
        return titulo; }
    public String getGenero() {
        return genero; }
    public int getDuracionMinutos() {
        return duracionMinutos; }
    public String getClasificacion() {
        return clasificacion; }
    public String getSinopsis() {
        return sinopsis; }
    public boolean isEnCartelera() {
        return enCartelera; }
    public void setTitulo(String titulo) {
        this.titulo = titulo; }
    public void setGenero(String genero) {
        this.genero = genero; }
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion; }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis; }
    public void setEnCartelera(boolean enCartelera) {
        this.enCartelera = enCartelera; }

    @Override
    public String toString() {
        return titulo; }
}
