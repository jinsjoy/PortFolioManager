package com.portfolio.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.portfolio.fundWeightCalculator.PortfolioFundWeightCalculator;
import com.portfolio.interfaces.PortfolioWeightCalculatorInterface;

public class PortfolioFundManager {

	public static void main(String[] args) throws IOException {

		PortfolioWeightCalculatorInterface fundGraph = new PortfolioFundWeightCalculator();

		/**
		 * if(args.length < 1) { System.out.println("Error:Please run with a input text
		 * file as parameter"); System.exit(1); }
		 */

		// Scanner reader = new Scanner(new FileInputStream(args[0]));

		try {

			File file = new File("/Users/jinsjoy/Documents/file.txt");
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split("\\,");
				fundGraph.addEdge(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
			}
			reader.close();
		} catch (Exception e) {
			throw e;
		}

		List<String> fundweights = fundGraph.fundWeightCalculator();
		
		fundweights.forEach((element) -> {
			System.out.println(element);
		});
	}

}
