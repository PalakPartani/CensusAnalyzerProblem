package censusanalyser;

public class IndiaCensusDTO {
    public int population;
    public String state;
    public int areaInSqKm;
    public int densityPerSqKm;
    public String stateCode;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
    }
}
