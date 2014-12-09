package exam2;

import java.util.ArrayList;

public class Tester {
	public static void main(String [] args) {
		FileStorage file_store = new FileStorage(10000000);
		
		File file1 = file_store.newFile("file1");
		File file2 = file_store.newFile("file2");
		File file3 = file_store.newFile("file3");
		
		File new_file = file_store.newFile("file2");
		System.out.println("File with same name should be null: " + new_file);
		
		System.out.println("Current storage before: " + file_store.getCurrentStorage());
		
		file_store.addToFile(file1, 64);
		file_store.addToFile(file2, 128);
		file_store.addToFile(file3, 32);
		
		System.out.println("Current storage after: " + file_store.getCurrentStorage());
		
		System.out.println("List Files and Sizes: ");
		
		listFilesAndSizes(file_store);
		
		System.out.println("Clear: ");
		
		clear(file_store);
		
		System.out.println("New Current Storage: " + file_store.getCurrentStorage());
		System.out.println(" List Files and Sizes: ");
		listFilesAndSizes(file_store);
	}
	
	public static void listFilesAndSizes(FileStorage store) {
		for(File f : store.getFiles()) {
			System.out.println("name: " + f.getName() + " size: " + f.getSizeKB());
		}
		System.out.println("Current storage: " + store.getCurrentStorage());
	}
	
	public static void clear(FileStorage store) {
		ArrayList<File> files = store.getFiles();
		int numFiles = store.getNumFiles();
		for(int i = 0; i < numFiles; i++) store.removeFile(files.get(0).getName());
	}
}
