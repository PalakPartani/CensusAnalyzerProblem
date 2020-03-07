package censusanalyser;

public class CensusDTO {
    public double populationDensity;
    public String state;
    public double totalArea;
    public int population;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV next) {
        state = next.state;
        totalArea = next.areaInSqKm;
        populationDensity = next.densityPerSqKm;
        population = next.population;
    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        totalArea = usCensusCSV.totalArea;
    }
/*
    public Object getCensus(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaCensusCSV(stateCode, state, populationDensity, population);
    }*/
}

