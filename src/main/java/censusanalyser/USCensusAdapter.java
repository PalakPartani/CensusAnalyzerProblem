package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDTO> censusCSVMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusCSVMap;
    }
}

