package com.froobworld.frooblib.utils;

import java.util.ArrayList;

import com.froobworld.frooblib.data.Listable;

public class PageUtils {

	public static <T> ArrayList<String> toStringList(ArrayList<T> list){
		ArrayList<String> stringList = new ArrayList<String>();
		for(T element : list) {
			stringList.add(element.toString());
		}
		
		return stringList;
	}

	public static <T> String toString(ArrayList<T> listables){
		String string = "";
		
		for(Object listable : listables){
			
			string += (string == "" ? "":", ") + ((listable instanceof Listable) ? ((Listable) listable).listName():listable.toString());
		}
		
		return string;
	}

	public static <T> int pages(ArrayList<T> listables, int pageLength){
		
		return -Math.floorDiv(-listables.size(), pageLength);
	}

	public static <T> ArrayList<T> page(ArrayList<T> listables, int pageLength, int pageIndex){
		if(listables.size() == 0 || pageIndex < 1 || pageIndex > pages(listables, pageLength)){
			return null;
		}
		int toIndex = pageLength*pageIndex > listables.size() ? listables.size()-1:pageLength*pageIndex-1;
		
		return new ArrayList<T>(listables.subList(pageLength*(pageIndex-1), toIndex+1));
	}
}
