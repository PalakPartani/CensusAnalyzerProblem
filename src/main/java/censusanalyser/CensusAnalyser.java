package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    public  Country country;

    public enum Country {INDIA, US}


    Map<String, CensusDTO> censusCSVMap;
    Map<SortData, Comparator<CensusDTO>> field = null;
    List<CensusDTO> collect;

    public CensusAnalyser(Country country) {
       this.country=country;
        this.field=new HashMap<>();
        this.field.put(SortData.STATE, Comparator.comparing(census -> census.state));
        this.field.put(SortData.POPULATION, Comparator.comparing(census -> census.population));
        this.field.put(SortData.POPULATION_DENSITY, Comparator.comparing(census -> census.populationDensity));
        this.field.put(SortData.TOTAL_AREA, Comparator.comparing(census -> census.totalArea));

    }

    public int loadCensusData(Country country, String... csvFilePath) {
        censusCSVMap = new CensusAdapterFactory().getCensusAdapter(country, csvFilePath);
        System.out.println(censusCSVMap.size());
        return censusCSVMap.size();
    }

    public String getStateWiseSortedCensusData(SortData data) {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        collect = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(collect, this.field.get(data));
        String sortedStateCensusJson = new Gson().toJson(collect);
        return sortedStateCensusJson;
    }

    private void sort(List<CensusDTO> collect, Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i < this.collect.size() - 1; i++) {
            for (int j = 0; j < this.collect.size() - 1 - i; j++) {
                CensusDTO census1 = this.collect.get(j);
                CensusDTO census2 = this.collect.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    this.collect.set(j, census2);
                    this.collect.set(j + 1, census1);
                }
            }
        }
    }
}