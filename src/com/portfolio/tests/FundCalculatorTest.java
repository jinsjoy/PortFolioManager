package com.portfolio.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.portfolio.fundWeightCalculator.PortfolioFundWeightCalculator;



@RunWith(JUnit4.class)
public class FundCalculatorTest {

   
    
    public static PortfolioFundWeightCalculator g = new PortfolioFundWeightCalculator();

    @BeforeClass
    public static void makeGraphs() {
    	
    	g.addEdge("A", "B",1000);
    	g.addEdge("A", "C",2000);
    	g.addEdge("B", "D",500);
    	g.addEdge("B", "E",250);
    	g.addEdge("B", "F",250);
    	g.addEdge("C", "G",1000);
    	g.addEdge("C", "H",1000);      
    }
    
    String testOutput = "[A,D,0.167, A,F,0.083, A,E,0.083, A,H,0.333, A,G,0.333, B,D,0.5, B,F,0.25, B,E,0.25, C,H,0.5, C,G,0.5]";
    

    @Test
    public void findFundWeightOfPortFolio() {
    	assertEquals(testOutput, g.fundWeightCalculator().toString());
    }

 
}
