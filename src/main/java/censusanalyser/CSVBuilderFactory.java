package censusanalyser;

public class CSVBuilderFactory {
    public static ICSVBulider createCSVBuilder() {
        return new CSVBuilder();

    }
}
