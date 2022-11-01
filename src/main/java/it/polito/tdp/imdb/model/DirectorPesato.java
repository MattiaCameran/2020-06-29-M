package it.polito.tdp.imdb.model;

public class DirectorPesato implements Comparable<DirectorPesato>{
	
	Director d;
	int peso;
	public DirectorPesato(Director d, int peso) {
		super();
		this.d = d;
		this.peso = peso;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(DirectorPesato o) {
		// TODO Auto-generated method stub
		return -(this.peso- o.getPeso());
	}
	
	public String toString() {
		return this.d.toString()+" - #attori condivisi: "+this.peso;
	}

}
