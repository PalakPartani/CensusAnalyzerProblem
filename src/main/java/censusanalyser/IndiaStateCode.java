package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {

    @CsvBindByName(column = "StateName", required = true)
    public String state;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndianStateCode{ 'State Name='" + state + '\'' + "'State Code'" + stateCode + '\'' + '}';
    }
}
