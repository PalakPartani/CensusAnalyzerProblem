package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "StateName",required = true)
    private String stateCode;

    @CsvBindByName(column = "StateCode",required = true)
    private String StateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "stateCode='" + stateCode + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
