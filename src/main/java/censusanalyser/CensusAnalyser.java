package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {

            ICSVBulider iterator = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = iterator.getIndiaCensusCSVIterator(reader, IndiaCensusCSV.class);
            return getCount(censusCSVIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (Exception e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> indiaCensusCSVS = () -> censusCSVIterator;
        int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
        return namOfEateries;
    }


    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {

            Iterator<IndiaStateCodeCSV> censusCSVIterator = new CSVBuilder().getIndiaCensusCSVIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indiaCensusCSVS = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
