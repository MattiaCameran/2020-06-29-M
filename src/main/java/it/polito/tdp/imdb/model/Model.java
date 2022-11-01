package it.polito.tdp.imdb.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private Graph<Director, DefaultWeightedEdge> grafo;
	
	private ImdbDAO dao;
	
	private Map<Integer, Director> idMap;
	
	public Model() {
		
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<Integer, Director>();
		
		this.dao.listAllDirectors(idMap);
	}
	
	
	public List<Integer> getAnni(){
		List<Integer> temp = new LinkedList<>();
		
		int a1 = 2004;
		int a2 = 2005;
		int a3 = 2006;
		
		temp.add(a1);
		temp.add(a2);
		temp.add(a3);
		return temp;
	}
	
	public List<Director> getDirettori(){
		List<Director> direttori = new LinkedList<Director>(this.grafo.vertexSet());
		Collections.sort(direttori);
		return direttori;
	}
	
	public String creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Director> vertici = this.dao.getDirectorsAnno(anno, idMap);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		//Archi
		for(Adiacenza a: this.dao.getAdiacenze(idMap, anno)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
		}
		
		return "Grafo creato!\n"+
		"Numero vertici: "+this.grafo.vertexSet().size()+"\n"+
		"Numero archi: "+this.grafo.edgeSet().size();
				
	}
	
	public List<DirectorPesato> getListaDirectorPesato(Director d) {
		
		List<Director> adiacenti = Graphs.neighborListOf(this.grafo, d);
		List<DirectorPesato> result = new LinkedList<DirectorPesato>();
		
		for(Director d1: adiacenti) {
			
		int peso = (int)this.grafo.getEdgeWeight(this.grafo.getEdge(d, d1));
		
		DirectorPesato dp = new DirectorPesato(d1, peso);
		if(dp!= null) {
			result.add(dp);
		}
		}
		Collections.sort(result);
		return result;
	}
	
	
	public List<DirectorPesato> cercaPercorso(int c, Director d){
		
		List<DirectorPesato> camminoMinimo = new LinkedList<DirectorPesato>();
		
		List<Director> parziale = new LinkedList<Director>();
		
		cerca(parziale, d, c);
		parziale.add(d);
		
		return camminoMinimo;
		
	}


	private void cerca(List<Director> parziale, Director d, int c) {
	
		//Stato terminale
		
		
		//Stato normale
		
		
	}
}
