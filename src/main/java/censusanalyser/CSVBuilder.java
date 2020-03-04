package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CSVBuilder<E> implements ICSVBulider {
    @Override
    public Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) {

        return this.getCsvToBean(reader, csvType).iterator();
    }

    private CsvToBean<E> getCsvToBean(Reader reader, Class csvType) {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvType);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    @Override
    public List<E> getIndiaCensusCSVList(Reader reader, Class csvType) {
        return this.getCsvToBean(reader, csvType).parse();
    }
}
