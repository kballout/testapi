package com.surecost.testapi.drugs;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class DrugController {
    @Autowired
    private DrugService drugService;


    //getting all the drugs on the main page
    @GetMapping("drugs")
    public ResponseEntity<List<Drug>> getAllDrugs(){
        List<Drug> drugs = drugService.getAllDrugs();
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    //get a single drug by id
    @GetMapping("getDrugById/{id}")
    public ResponseEntity<Drug> getDrug(@PathVariable UUID id){
        Optional<Drug> foundDrug = drugService.getDrugById(id);
        if(foundDrug.isPresent()){
            return new ResponseEntity<>(foundDrug.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //query drugs by manufacturer and/or name
    @GetMapping("/query")
    public ResponseEntity<List<Drug>> queryDrugs(@RequestParam Optional<String> manufacturer, @RequestParam Optional<String> name) {
        List<Drug> result = drugService.getAllDrugs();

        if (manufacturer.isPresent()) {
            result = result.stream().filter(drug -> drug.getManufacturer().equalsIgnoreCase(manufacturer.get().trim())).toList();
        }
        if (name.isPresent()) {
            result = result.stream().filter(drug -> drug.getName().equalsIgnoreCase(name.get().trim())).toList();
        }

        return ResponseEntity.ok(result);
    }


    //adding new drugs to the directory
    @PostMapping("/addDrugs")
    public ResponseEntity<String> addDrug(@RequestBody @Valid List<@Valid Drug> drugs){
        for(Drug next: drugs){
            drugService.addNewDrug(next);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Updating quantity and/or price of a drug by ID
    @PutMapping("/updateDrug/{id}")
    public ResponseEntity<String> updateDrug(
            @PathVariable UUID id,
            @RequestParam Optional<Integer> quantity,
            @RequestParam Optional<Double> price) {

        Optional<Drug> optionalDrug = drugService.getDrugById(id);

        if (optionalDrug.isPresent()) {
            Drug drug = optionalDrug.get();
            quantity.ifPresent(drug::setQuantity);
            quantity.ifPresent(drug::setQuantity);
            price.ifPresent(drug::setPrice);
            drugService.updateDrug(drug); // Assuming this method persists the changes
            return new ResponseEntity<>("Drug updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Drug not found", HttpStatus.NOT_FOUND);
    }

}
