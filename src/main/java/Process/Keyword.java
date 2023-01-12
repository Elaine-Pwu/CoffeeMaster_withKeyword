package Process;

public class Keyword {
	private String name;
	private double weight;
	private int count;
	
	public Keyword(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public double getCount() {
		return this.count;
	}
}
