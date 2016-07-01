package br.ufc.crateus.eda.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;

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
		return semAcentos.toLowerCase();
	}

	public BufferedReader open(String name) throws IOException {
		String path = "\\UFC\\3º Semestre\\Estrutura de Dados Avançado - EDA\\arquivo\\Livio\\";
		File file = new File(path + name);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao tentar abrir o arquivo > " + e.getMessage());
		}
		return reader;
	}

	public int wordCount(BufferedReader reader, String pword) throws IOException {
		String normalizeWord = normalizeStr(pword);
		int count = 0;
		while (reader.ready()) {
			String line = normalizeStr(reader.readLine());
			for (String word : line.split("\\s+")) {
				if (word.equals(normalizeWord))
					count++;
			}
		}
		reader.close();
		return count;
	}

	public void close(BufferedReader reader) {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> createInvertedIndex(
			SeparateChainingHashST<String, Integer> codId) throws IOException {
		SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> separateChainingHashST = new SeparateChainingHashST<>(
				97);
		BinarySearchTree<Integer, Integer> aux;
		Integer count = 1;
		for (String cod : codId.keys()) {
			BufferedReader reader = open(cod);
			while (reader.ready()) {
				String line = normalizeStr(reader.readLine());
				for (String word : line.split("\\s+")) {
					if (word.length() > 3 && isDigit(word)) {
						aux = separateChainingHashST.get(word);
						if (aux != null) {
							count = aux.get(codId.get(cod));
							count = (count != null) ? count + 1 : 1;
							aux.put(codId.get(cod), count);
							separateChainingHashST.put(word, aux);
						} else {
							aux = new BinarySearchTree<>();
							aux.put(codId.get(cod), count);
							separateChainingHashST.put(word, aux);
						}
					}
				}
			}
			reader.close();
		}
		return separateChainingHashST;
	}

	public String removeCs(String r) {
		if (r.length() > 20) {
			char[] charArray = new char[20];
			for (int i = 0; i < 20; i++) {
				charArray[i] = r.charAt(i);
			}
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

	public long time() {
		return System.currentTimeMillis();
	}

	@SuppressWarnings("unused")
	private Double calculateFormatLog(int value) {
		DecimalFormat df = new DecimalFormat("0.00");
		String dx = df.format(Math.log(value));
		dx = dx.replace(",", ".");
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

	public static void main(String[] args) throws IOException {
		Versao1 ver1 = new Versao1();

		List<Integer> numberWords = new ArrayList<>();
		List<String> listFile = new ArrayList<>();
		listFile = ver1.captureEnter("Entrada.txt");
		SeparateChainingHashST<String, Integer> codId = ver1.docId(listFile);

		long tempoInicial = ver1.time();
		SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> map = ver1.createInvertedIndex(codId);
		long tempoFinal = ver1.time();
		for (String string : map.keys()) {
			System.out.println(string);
		}
		System.out.println(map.size());
	}
}
