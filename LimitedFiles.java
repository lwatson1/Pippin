package exam2;

public class LimitedFiles extends FileStorage {
	private int maxNumFiles;
	
	public LimitedFiles(int maxNumFiles, long capacity) {
		super(capacity);
		this.maxNumFiles = maxNumFiles;
	}
	
	@Override
	public File newFile(String name) {
		if(getNumFiles() >= maxNumFiles) return null;
		return super.newFile(name);
	}
}
