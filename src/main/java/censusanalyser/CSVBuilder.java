package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class CSVBuilder {
    public <E> Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvType);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

}
