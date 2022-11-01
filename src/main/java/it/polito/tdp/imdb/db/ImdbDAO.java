package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void listAllDirectors(Map<Integer, Director> idMap){
		String sql = "SELECT * FROM directors";

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				idMap.put(res.getInt("id"), director);
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	public List<Director> getDirectorsAnno(int anno, Map<Integer, Director> idMap){
		
		String sql = "SELECT d.id, COUNT(DISTINCT md.movie_id) "
				+ "FROM directors d, movies_directors md, movies m "
				+ "WHERE d.id = md.director_id AND md.movie_id = m.id AND m.year = ? "
				+ "GROUP BY d.id, d.first_name, d.last_name "
				+ "HAVING COUNT(DISTINCT md.movie_id) > 0";
		
		List<Director> result = new ArrayList<Director>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				if(!result.contains(idMap.get(res.getInt("id")))) {
				result.add(idMap.get(res.getInt("id")));
				}
			}
			conn.close();
			return result;

			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Director> idMap, int anno){
		
		String sql = "SELECT DISTINCT md1.director_id AS id1, md2.director_id AS id2, COUNT(DISTINCT r1.actor_id) AS peso "
				+ "FROM roles r1, roles r2, movies_directors md1, movies_directors md2, movies m1, movies m2 "
				+ "WHERE r1.movie_id = md1.movie_id AND r2.movie_id = md2.movie_id AND md1.director_id > md2.director_id "
				+ "AND r1.actor_id = r2.actor_id AND m1.id = md1.movie_id AND m1.year = ? AND m2.id = md2.movie_id "
				+ "AND m2.year = ? "
				+ "GROUP BY md1.director_id, md2.director_id";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(new Adiacenza(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getInt("peso")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}
	
	
}
