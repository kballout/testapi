package com.surecost.testapi.drugs;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrugService {
    public static Map<UUID, Drug> drugsMap = new HashMap<>();

    //get the entire list of drugs
    public List<Drug> getAllDrugs(){
        Collection<Drug> values = drugsMap.values();
        return new ArrayList<>(values);
    }

    //add a new drug
    public void addNewDrug(Drug drug){
        drugsMap.put(drug.getUid(), drug);
    }

    //get a specific drug based on the id
    public Optional<Drug> getDrugById(UUID id){
        Optional<Drug> foundDrug = Optional.empty();
        Drug search = drugsMap.get(id);
        if(search != null){
            foundDrug = Optional.of(search);
        }
        return foundDrug;
    }

    //update a drug by id
    public void updateDrug(Drug newDrug){
        drugsMap.put(newDrug.getUid(), newDrug);
    }
}
