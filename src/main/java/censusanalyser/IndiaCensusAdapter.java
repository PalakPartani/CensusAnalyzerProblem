package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDTO> censusCSVMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateCode(censusCSVMap, csvFilePath[1]);
        return censusCSVMap;
    }

    private int loadIndianStateCode(Map<String, CensusDTO> censusCSVMap, String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCode.class);
            Iterable<IndiaStateCode> csvIterable = () -> censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).filter(csvState -> censusCSVMap.get(csvState.state) != null)
                    .forEach(csvState -> censusCSVMap.get(csvState.state).stateCode = csvState.stateCode);

            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}

