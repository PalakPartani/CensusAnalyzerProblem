package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBulider<E> {
    public <E> Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType);
    public <E> List<E> getIndiaCensusCSVList(Reader reader, Class csvType);

}
