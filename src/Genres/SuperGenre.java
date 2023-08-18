package Genres;
import logging.AssignmentLogger;

public abstract class SuperGenre {
	
	//Strings to hold the genre information 
	private String genre;
	private String description;
	private String image;
	
	
	public void GenreInfo(String genre, String description, String image) {
		this.genre = genre;
		this.description = description;
		this.image = image; 
	}
	
	//Getters and Setters for tghe 
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
