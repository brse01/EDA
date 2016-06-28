package br.ufc.crateus.eda.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;
import br.ufc.crateus.eda.st.ordered.BinarySearchST;
import br.ufc.crateus.eda.st.ordered.BinarySearchTree;

public class Versao1 {

	public int doc_id(String key, int m) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	public BinarySearchST<String, Integer> docId(List<String> names) {
		BinarySearchST<String, Integer> docAux = new BinarySearchST<>();
		for (int i = 0; i < names.size(); i++) {
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
			BinarySearchST<String, Integer> codId) throws IOException {
		SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> separateChainingHashST = new SeparateChainingHashST<>(
				97);
		BinarySearchTree<Integer, Integer> aux;
		Integer count = 1;
		for (String cod : codId.keys()) {
			String file = cod;
			BufferedReader reader = open(file);
			while (reader.ready()) {
				String line = normalizeStr(reader.readLine());
				for (String word : line.split("\\s+")) {
					if (word.length() > 3) {
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

	public static void main(String[] args) throws IOException {
		Versao1 versao1 = new Versao1();
		List<String> listFile = new ArrayList<>();
		listFile = versao1.captureEnter("Entrada.txt");
		BinarySearchST<String, Integer> codId = versao1.docId(listFile);
		SeparateChainingHashST<String, BinarySearchTree<Integer, Integer>> map = versao1.createInvertedIndex(codId);
		for (String word : map.keys()) {
			System.out.println(word + " = " + map.get(word));
		}

	}
}
