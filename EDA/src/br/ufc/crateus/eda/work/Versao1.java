package br.ufc.crateus.eda.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;
import br.ufc.crateus.eda.string.StringST;

public class Versao1 {

	public SeparateChainingHashST<String, Integer> docId(List<String> names) {
		int m = names.size();
		SeparateChainingHashST<String, Integer> docAux = new SeparateChainingHashST<>(m);
		for (int i = 0; i < m; i++) {
			docAux.put(names.get(i), i + 1);
		}

		return docAux;
	}

	public String normalizeStr(String str) {
		String semAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
		semAcentos = semAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		semAcentos = semAcentos.replaceAll("\\p{Punct}", "");
		semAcentos = semAcentos.replace("“", "");
		semAcentos = semAcentos.replace("”", "");
		semAcentos = semAcentos.replace("�", "");
		semAcentos = semAcentos.replace("∞", "");
		semAcentos = semAcentos.replace("°", "");
		return semAcentos.toLowerCase();
	}

	public BufferedReader open(String name) throws IOException {
		File file = new File(name);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
		} catch (FileNotFoundException e) {
			System.err.println("Erro ao tentar abrir o arquivo > " + e.getMessage());
		}
		return reader;
	}

	public void close(BufferedReader reader) {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String removeCs(String r) {

		char[] charArray = new char[5];
		for (int i = 0; i < 5; i++) {
			charArray[i] = r.charAt(i);
			r = String.copyValueOf(charArray);
		}
		return r;
	}

	public List<String> captureEnter(String str) throws IOException {
		List<String> auxFile = new ArrayList<>();
		int count = 0;
		BufferedReader reader = open(str);
		String lineCount = reader.readLine();
		int quant = Integer.parseInt(lineCount.replaceAll("\\s+$", ""));
		if (quant > 0) {
			while (reader.ready() || quant > count) {
				String line = reader.readLine();
				auxFile.add(line);
				count++;
			}
		}
		reader.close();
		return auxFile;
	}

	public Double Format(Double value) {
		DecimalFormat df = new DecimalFormat("0.00");
		String dx = df.format(value);
		dx = dx.replace(",", ".");
		if (dx.equals("�") || dx.equals("∞"))
			dx = "0.0";
		return Double.parseDouble(dx.trim());
	}

	@SuppressWarnings("unused")
	public boolean isDigit(String st) {
		try {
			int b = Integer.parseInt(st);
			return false;
		} catch (NumberFormatException nfe) {
			return true;
		}
	}

	public Double log(int n) {
		return Math.log(n) / Math.log(2);
	}

	public List<String> separateWord(String line) {
		List<String> aux = new ArrayList<>();
		for (String word : line.split("\\s+")) {
			if (word.length() > 3) {
				word = normalizeStr(word);
				aux.add(word);
			}
		}
		return aux;
	}

	public SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> createInvertedIndex2(
			SeparateChainingHashST<String, Integer> codId, List<Integer> numberWords, StringST<String> suggestions,
			String path) throws IOException {
		SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> separateChainingHashST = new SeparateChainingHashST<>(
				97);
		Integer countNumberWords = 0;
		BinarySearchTree<Integer, Integer> aux;
		for (String cod : codId.keys()) {
			BufferedReader reader = open(path + cod);
			while (reader.ready()) {
				String line = reader.readLine();
				for (String word : line.split("\\s+")) {
					word = normalizeStr(word).trim();
					if (word.length() > 3 && isDigit(word)) {
						if (separateChainingHashST.contains(word)) {
							aux = separateChainingHashST.get(word);
							Integer count = aux.get(codId.get(cod));
							count = (count != null) ? count + 1 : 1;
							aux.put(codId.get(cod), count);
							separateChainingHashST.put(word, aux);
						} else {
							System.out.println(word);
							suggestions.put(word, word);
							countNumberWords++;
							aux = new BinarySearchTree<>();
							aux.put(codId.get(cod), 1);
							separateChainingHashST.put(word, aux);
						}
					}
				}
			}
			numberWords.add(countNumberWords);
			countNumberWords = 0;
			reader.close();
		}
		return separateChainingHashST;
	}

	public BinarySearchTree<Double, String> sort(BinarySearchTree<String, Double> accumulator,
			List<Integer> numberWords) {
		BinarySearchTree<Double, String> aux = new BinarySearchTree<>();
		int i = 0;
		for (String file : accumulator.keys()) {
			aux.put(Format((accumulator.get(file) / numberWords.get(i))), file);
			i++;
		}
		return aux;
	}

	public BinarySearchTree<Double, String> calculate(List<String> termos,
			SeparateChainingHashST<String, Integer> codId, List<Integer> numberWords,
			SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> map, List<String> listFile) {
		BinarySearchTree<Integer, Integer> aux;
		String word;
		Double count;
		int f;
		Double w;
		int numberDocuments = listFile.size();
		BinarySearchTree<String, Double> accumulator = new BinarySearchTree<>();
		for (int i = 0; i < termos.size(); i++) {
			word = termos.get(i);
			if (map.contains(word)) {
				aux = map.get(word);
				int d = aux.size();
				for (String string : listFile) {
					f = (aux.get(codId.get(string)) != null) ? aux.get(codId.get(string)) : 0;
					if (f > 0) {
						w = f * (log(numberDocuments) / d);
					} else {
						w = 0.0;
					}
					count = accumulator.get(string);
					count = (count != null) ? count + w : w;
					accumulator.put(string, count);
				}
			}
		}
		return sort(accumulator, numberWords);
	}

	public void decreasing(BinarySearchTree<Double, String> toPrint, List<Double> decreasing) {
		for (Double key : toPrint.keys()) {
			if (key > 0.0)
				decreasing.add(key);
		}
		Collections.reverse(decreasing);
	}

	public static void main(String[] args) throws IOException {
		Runtime.getRuntime().exec("notepad C://Trabalho//Entrada.txt");
	}

}
