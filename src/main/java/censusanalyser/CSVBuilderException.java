package censusanalyser;

public class CSVBuilderException extends RuntimeException {
    public CSVBuilderException(String message, String name) {
        super(message);
        ExceptionType.valueOf(name);
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE;
    }
    CensusAnalyserException.ExceptionType type;
}
