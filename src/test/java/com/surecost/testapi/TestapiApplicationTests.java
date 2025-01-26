package com.surecost.testapi;

import com.surecost.testapi.drugs.Drug;
import com.surecost.testapi.drugs.DrugController;
import com.surecost.testapi.drugs.DrugService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.surecost.testapi.Constants.*;
import static com.surecost.testapi.Constants.manufacturers;
import static org.mockito.Mockito.*;

@SpringBootTest
class TestapiApplicationTests {

	@Mock
	private DrugService drugService;

	@InjectMocks
	private DrugController drugController;

	@Test
	void gettingAllDrugs(){
		// Create a sample drug
		Random index = new Random();
		Drug sampleDrug = new Drug(
				prices[index.nextInt(prices.length)],
				qtys[index.nextInt(qtys.length)],
				"Tylenol",
				manufacturers[index.nextInt(manufacturers.length)]
		);
		//Add the mock drug
		when(drugService.getAllDrugs()).thenReturn(List.of(sampleDrug));

		ResponseEntity<List<Drug>> response = drugController.getAllDrugs();
		Assertions.assertEquals(1, response.getBody().size(), "Expected 1 drug");
	}

	@Test
	void addingNewDrug() {
 		// Arrange: Create a sample drug
		Random index = new Random();
		Drug sampleDrug = new Drug(
				prices[index.nextInt(prices.length)],
				qtys[index.nextInt(qtys.length)],
				"Tylenol",
				manufacturers[index.nextInt(manufacturers.length)]
		);

		// Ensure no exception is thrown when adding the drug
		Assertions.assertDoesNotThrow(() -> {
			drugService.addNewDrug(sampleDrug);
		});


		// Ensure the addNewDrug method was called
		verify(drugService, times(1)).addNewDrug(sampleDrug);
	}

	@Test
	void getDrugById() {
		// Create a sample drug
		Random index = new Random();
		Drug sampleDrug = new Drug(
				prices[index.nextInt(prices.length)],
				qtys[index.nextInt(qtys.length)],
				"Tylenol",
				manufacturers[index.nextInt(manufacturers.length)]
		);

		//Add the mock drug
		when(drugService.getDrugById(sampleDrug.getUid())).thenReturn(Optional.of(sampleDrug));

		// Get the drug by id
		Drug result = drugController.getDrug(sampleDrug.getUid()).getBody();

		// Verify the result matches the expected drug
		Assertions.assertNotNull(result, "Drug should not be null");
		Assertions.assertEquals(sampleDrug.getUid(), result.getUid(), "Drug ID should match");
		Assertions.assertEquals("Tylenol", result.getName(), "Drug name should match");

		// Verify the service was called with the correct ID
		verify(drugService, times(1)).getDrugById(sampleDrug.getUid());
	}

	@Test
	void testQueryDrugs() {
		// Prepare mock data
		Random index = new Random();
		Drug drug1 = new Drug(
				prices[index.nextInt(prices.length)],
				qtys[index.nextInt(qtys.length)],
				"Drug 1",
				"Manufacturer 1"
		);

		Drug drug2 = new Drug(
				prices[index.nextInt(prices.length)],
				qtys[index.nextInt(qtys.length)],
				"Drug 2",
				"Manufacturer 2"
		);

		// Mock the behavior of getAllDrugs to return the prepared list
		List<Drug> mockDrugs = new ArrayList<>(List.of(drug1, drug2));
		when(drugService.getAllDrugs()).thenReturn(mockDrugs);

		// Test with manufacturer filter
		ResponseEntity<List<Drug>> response = drugController.queryDrugs(Optional.of("Manufacturer 1"), Optional.empty());
		Assertions.assertEquals(1, response.getBody().size(), "Expected 1 drug from Manufacturer 1");
		Assertions.assertEquals("Drug 1", response.getBody().get(0).getName(), "Expected Drug 1");

		// Test with name filter
		response = drugController.queryDrugs(Optional.empty(), Optional.of("Drug 2"));
		Assertions.assertEquals(1, response.getBody().size(), "Expected 1 drug named Drug 2");
		Assertions.assertEquals("Drug 2", response.getBody().get(0).getName(), "Expected Drug 2");

		// Test with no filters
		response = drugController.queryDrugs(Optional.empty(), Optional.empty());
		Assertions.assertEquals(2, response.getBody().size(), "Expected 2 drugs with no filters");
	}

	@Test
	void testUpdateDrug(){
		Random index = new Random();
		Drug sampleDrug = new Drug(
				20.0,
				150,
				"Tylenol",
				manufacturers[index.nextInt(manufacturers.length)]
		);

		//Add the mock drug
		when(drugService.getDrugById(sampleDrug.getUid())).thenReturn(Optional.of(sampleDrug));

		ResponseEntity<String> response = drugController.updateDrug(
				sampleDrug.getUid(),
				Optional.of(700), // New quantity
				Optional.of(25.99) // New price
		);

		// Verify the response
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals("Drug updated successfully", response.getBody());
	}

}
