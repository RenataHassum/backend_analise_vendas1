package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		List<Sale> sale = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				sale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
			Comparator<Sale> comp = (v1, v2) -> v1.averagePrice().compareTo(v2.averagePrice());
			List<Sale> first5 = sale.stream().filter(x -> x.getYear() == 2016).sorted(comp.reversed()).limit(5)
					.toList();
			first5.forEach(System.out::println);

			List<Sale> logan = sale.stream().filter(x -> x.getSeller().equals("Logan")).toList();

			Double total1 = logan.stream().filter(x -> x.getMonth() == 1).map(x -> x.getTotal()).reduce(0.00,
					(x, y) -> x + y);
			Double total2 = logan.stream().filter(x -> x.getMonth() == 7).map(x -> x.getTotal()).reduce(total1,
					(x, y) -> x + y);

			System.out.printf("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f", total2);

		} catch (Exception e) {
			System.out.println("Erro " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();
	}

}