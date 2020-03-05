package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "StateName",required = true)
    public String stateName;

    @CsvBindByName(column = "StateCode",required = true)
    public String StateCode;
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "stateCode='" + stateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
