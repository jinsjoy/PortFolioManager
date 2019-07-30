package com.portfolio.interfaces;

import java.util.List;

public interface PortfolioWeightCalculatorInterface {
	
	public boolean addEdge(final String source, final String destination, final int data);
	
	public List<String> fundWeightCalculator();
	
	

}
