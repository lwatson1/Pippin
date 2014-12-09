package exam2;

import java.util.ArrayList;

public class FileStorage {
	private long capacity;
	private long currentStorage;
	private ArrayList<File> files = new ArrayList<>();
	
	public FileStorage(long capacity) {
		this.capacity = capacity;
	}
	
	public long getCurrentStorage() {
		return currentStorage;
	}
	
	public ArrayList<File> getFiles() {
		return files;
	}
	
	public int getNumFiles() {
		return files.size();
	}
	
	public boolean fileExists(String name) {
		for(File f : files) if(f.getName() == name) return true;
		return false;
	}
	
	public File newFile(String name) {
		if(fileExists(name)) return null;
		File new_file = new File(name, 0);
		files.add(new_file);
		return new_file;
	}
	
	public void addToFile(File file, int amount) {
		if((currentStorage + amount) > capacity) return;
		int sizeKB = file.getSizeKB();
		file.setSizeKB(sizeKB+amount);
		currentStorage+=amount;
	}
	
	public void removeFile(String name) {
		for(File f : files) {
			if(f.getName() == name) {
				currentStorage-=(f.getSizeKB());
				files.remove(f);
				return;
			}
		}
	}
}
