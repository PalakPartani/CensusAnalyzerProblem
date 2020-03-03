package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

//import com.sun.tools.javac.code.Attribute;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {


        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaCensusCSV> censusCSVIterator = getIndiaCensusCSVIterator(reader,IndiaCensusCSV.class);
            return getCount(censusCSVIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (Exception e)
        {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> indiaCensusCSVS = () -> censusCSVIterator;
        int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
        return namOfEateries;
    }

    private <E>Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvType);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){

            Iterator<IndiaStateCodeCSV> censusCSVIterator =getIndiaCensusCSVIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> indiaCensusCSVS = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(indiaCensusCSVS.spliterator(), false).count();
            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
