package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    public abstract Map<String, CensusDTO> loadCensusData(String... csvFilePath);


    public <E> Map<String, CensusDTO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) {
        Map<String, CensusDTO> censusCSVMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            System.out.println(censusCSVClass.getClass());
            Iterable<E> csvIterable = () -> csvFileIterator;
            if (censusCSVClass.getName() == "censusanalyser.IndiaCensusCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(csvState -> censusCSVMap.put(csvState.state, new CensusDTO(csvState)));
            } else if (censusCSVClass.getName() == "censusanalyser.USCensusCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(csvState -> censusCSVMap.put(csvState.state, new CensusDTO(csvState)));
            }
            return censusCSVMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
