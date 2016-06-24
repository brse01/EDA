package br.ufc.crateus.eda.utils;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

import br.ufc.crateus.eda.st.hashing.SeparateChainingHashST;

public class WordCount2 {

	public String normalizeStr(String src) {
		String semAcentos = Normalizer.normalize(src, Normalizer.Form.NFD);
		semAcentos = semAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		semAcentos = semAcentos.replaceAll("\\p{Punct}", "");
		return semAcentos.toLowerCase();
	}

	public BufferedReader open(String name) throws IOException {
		String path = "\\UFC\\3º Semestre\\Estrutura de Dados Avançado - EDA\\arquivo\\";
		File file = new File(path + name);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao tentar abrir o arquivo.");
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
		return count;
	}

	public void close(BufferedReader reader) {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		WordCount2 w = new WordCount2();
		String word = "a";
		Scanner read = new Scanner(System.in);
		BufferedReader reader;
		ArrayList<String> lista = new ArrayList<>();
		lista.add("arquivo01.txt");
		lista.add("arquivo02.txt");
		lista.add("arquivo03.txt");
		lista.add("arquivo04.txt");
		lista.add("arquivo05.txt");
		lista.add("arquivo06.txt");
		String tt;
		int count = 0;
		int op = 0;
		do {			
			if (op == 0) {
				System.out.println("Digite a palavra:");
				word = read.nextLine();
			}
			for (int i = 0; i < lista.size(); i++) {
				tt = lista.get(i);
				reader = w.open(tt);
				count = w.wordCount(reader, word);
				System.out.println("No arquivo:>" + tt + "existe:> " + count + " da palavra:>" + word);
				w.close(reader);
			}						
		} while (op != 1);
	}
}
