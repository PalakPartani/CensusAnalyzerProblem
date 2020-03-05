package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDTO> censusList = null;
    Map<String, IndiaCensusDTO> censusMap = null;

    public CensusAnalyser() {
        censusList = new ArrayList<IndiaCensusDTO>();
        censusMap = new HashMap<String, IndiaCensusDTO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider iterator = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = iterator.getIndiaCensusCSVIterator(reader, IndiaCensusCSV.class);
            while (censusCSVIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSVIterator = censusCSVIterator.next();
                this.censusMap.put(indiaCensusCSVIterator.state, new IndiaCensusDTO(indiaCensusCSVIterator));
            }
            censusList = censusMap.values().stream().collect(Collectors.toList());
            return censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> indiaCensusCSVS = () -> censusCSVIterator;
        int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
        return namOfEateries;
    }


    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider icsvBulider = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = new CSVBuilder().getIndiaCensusCSVIterator(reader, IndiaStateCodeCSV.class);

            while (censusCSVIterator.hasNext()) {
                IndiaStateCodeCSV indiastateCSV = censusCSVIterator.next();
                IndiaCensusDTO indiaCensusDTO = censusMap.get(indiastateCSV.stateCode);

                if (indiastateCSV.StateCode == null) {
                    continue;
                }
                indiaCensusDTO.stateCode = indiastateCSV.stateCode;
            }
            //return getCount(censusCSVIterator);
            return censusMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensus() {

        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS", CensusAnalyserException.ExceptionType.CENSUS_NO_DATA);
        }
        Comparator<IndiaCensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateJson = new Gson().toJson(censusList);
        return sortedStateJson;

    }

    private void sort(Comparator<IndiaCensusDTO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDTO indiaCensusCSV1 = censusList.get(j);
                IndiaCensusDTO indiaCensusCSV2 = censusList.get(j + 1);
                if (censusComparator.compare(indiaCensusCSV1, indiaCensusCSV2) > 0) {
                    censusList.set(j, indiaCensusCSV2);
                    censusList.set(j + 1, indiaCensusCSV1);
                }

            }
        }

    }
}
