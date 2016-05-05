package com.exmyth.wechat.test.sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordCountTest {

	public static void main(String[] args) {
		File file = new File("D:/test");
		File[] listFiles = file.listFiles();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		try {
			BufferedReader br;
			String line;
			String[] split;
			Integer n;
			for (File f : listFiles) {
				br = new BufferedReader(new FileReader(f));
				while ((line = br.readLine()) != null) {
					split = line.split("\\s+");
					for (int i = 0; i < split.length; i++) {
						n = map.get(split[i]);
						if (n == null) {
							n = 0;
						}
						map.put(split[i], ++n);
					}
				}
				br.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SortedMap<String, Integer> sortedMap = new TreeMap<String, Integer>(new MapValueComparator(map));
		sortedMap.putAll(map);
		System.out.println(sortedMap);
	}
}

class MapValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public MapValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}
}