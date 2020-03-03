package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBulider<E> {
    public <E> Iterator<E> getIndiaCensusCSVIterator(Reader reader, Class csvType);
}
