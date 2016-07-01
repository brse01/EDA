package br.ufc.crateus.eda.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.hashing.LinearProbingHashST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;

public class Versao2 {

	public LinearProbingHashST<String, Integer> docId(List<String> names) {
		int m = names.size();
		LinearProbingHashST<String, Integer> docAux = new LinearProbingHashST<>(m);
		for (int i = 0; i < m; i++) {
			docAux.put(names.get(i), i);
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

	public LinearProbingHashST<String, BinarySearchTree<Integer, Integer>> createInvertedIndex(
			LinearProbingHashST<String, Integer> codId) throws IOException {
		LinearProbingHashST<String, BinarySearchTree<Integer, Integer>> linearProbingHashST = new LinearProbingHashST<>(
				97);
		BinarySearchTree<Integer, Integer> aux;
		Integer count = 1;
		for (String cod : codId.keys()) {

			BufferedReader reader = open(cod);
			while (reader.ready()) {
				String line = normalizeStr(reader.readLine());
				for (String word : line.split("\\s+")) {
					if (word.length() > 3) {
						aux = linearProbingHashST.get(word);
						if (aux != null) {
							count = aux.get(codId.get(cod));
							count = (count != null) ? count + 1 : 1;
							aux.put(codId.get(cod), count);
							linearProbingHashST.put(word, aux);
						} else {
							aux = new BinarySearchTree<>();
							aux.put(codId.get(cod), count);
							linearProbingHashST.put(word, aux);
						}
					}
				}
			}
			reader.close();
		}
		return linearProbingHashST;
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

	public static void main(String[] args) throws IOException {
		Versao2 ver2 = new Versao2();
		List<String> listFile = new ArrayList<>();
		listFile = ver2.captureEnter("Entrada.txt");
		LinearProbingHashST<String, Integer> codId = ver2.docId(listFile);
		LinearProbingHashST<String, BinarySearchTree<Integer, Integer>> map = ver2.createInvertedIndex(codId);
		for (String string : map.keys()) {
			System.out.println(ver2.normalizeStr(string));
		}
	}
}
