package com.surecost.testapi;

import com.surecost.testapi.drugs.Drug;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

import static com.surecost.testapi.Constants.*;
import static com.surecost.testapi.drugs.DrugService.drugsMap;

@SpringBootApplication
public class TestapiApplication {

	public static void main(String[] args) {
		inputMockData();
		SpringApplication.run(TestapiApplication.class, args);
	}

	//fill the directory with dummy data
	public static void inputMockData(){
		Double nextPrice;
		int nextQty;
		String nextManu;
		Drug nextDrug;
		for (int i = 0; i < 1000; i++){
			Random index = new Random();
			nextPrice = prices[index.nextInt(prices.length)];
			nextQty = qtys[index.nextInt(qtys.length)];
			nextManu = manufacturers[index.nextInt(manufacturers.length)];
			nextDrug = new Drug(nextPrice, nextQty, "Drug " + (i+1), nextManu);
			drugsMap.put(nextDrug.getUid(), nextDrug);
		}
	}

}