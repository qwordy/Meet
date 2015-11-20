package sjtu.se.UserInformation;

public class Hobby {
	public String Game;
	public String Sport;
	public String Comic;
	public String Music;
	public String Books;
	public String Movie;
	public String Other;
	
	public Hobby(){
		this.Game = "";
		this.Sport = "";
		this.Comic = "";
		this.Music = "";
		this.Books = "";
		this.Movie = "";
		this.Other = "";
	}
	
	public Hobby(Hobby h){
		this.Game = h.Game;
		this.Sport = h.Sport;
		this.Comic = h.Comic;
		this.Music = h.Music;
		this.Books = h.Books;
		this.Movie = h.Movie;
		this.Other = h.Other;
	}
}
