package com.froobworld.frooblib.data;

import java.io.File;

public class Storage {
	private File file;
	
	public Storage(String path){
		file = new File(path);
		createIfNotExist();
	}
	
	
	public void createIfNotExist(){
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public File[] listFiles(){
		
		return file.listFiles();
	}
	
	public File getTopFile() {
		
		return file;
	}
	
	public File getFile(String name){
		
		return new File(file, name);
	}
	
	public File getDirectory(String name){
		
		return new File(file + "/" + name);
	}
	
	public void createDirectory(String name){
		new File(file + "/" + name).mkdirs();
	}

}
