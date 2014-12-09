package exam2;

public class File {
	private String name;
	private int sizeKB;
	
	public File(String name, int sizeKB) {
		this.name = name;
		this.sizeKB = sizeKB;
	}

	public String getName() {
		return name;
	}

	public int getSizeKB() {
		return sizeKB;
	}

	public void setSizeKB(int sizeKB) {
		this.sizeKB = sizeKB;
	}
	
}
