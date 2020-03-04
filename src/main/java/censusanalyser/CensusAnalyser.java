package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList=null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider iterator = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = iterator.getIndiaCensusCSVList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
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
            Iterator<IndiaStateCodeCSV> censusCSVIterator = new CSVBuilder().getIndiaCensusCSVIterator(reader, IndiaStateCodeCSV.class);
          return getCount(censusCSVIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensus() {

            if(censusCSVList==null || censusCSVList.size()==0) {
                throw new CensusAnalyserException("NO_CENSUS", CensusAnalyserException.ExceptionType.CENSUS_NO_DATA);
            }Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
            this.sort(censusComparator);
            String sortedStateJson = new Gson().toJson(censusCSVList);
            return sortedStateJson;

    }

    private void sort( Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV indiaCensusCSV1 = censusCSVList.get(j);
                IndiaCensusCSV indiaCensusCSV2 = censusCSVList.get(j + 1);
                if (censusComparator.compare(indiaCensusCSV1, indiaCensusCSV2) > 0) {
                    censusCSVList.set(j, indiaCensusCSV2);
                    censusCSVList.set(j + 1, indiaCensusCSV1);
                }

            }
        }
    }
}

