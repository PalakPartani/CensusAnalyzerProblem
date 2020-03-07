package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_FILE_TYPE = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH, INDIA_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFileReturnsCorrectIfTrue() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int indiaStateCode = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            Assert.assertEquals(29, indiaStateCode);
        } catch (CensusAnalyserException e) {
        }

    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        int numOfState = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numOfState);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedStateResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.STATE);
            IndiaStateCode[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCode[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnState_ShouldReturnSortedStateResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[censusCSV.length - 1].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.TOTAL_AREA);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(0, censusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION_DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1382611, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnMostPopulationState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnMostPopulationState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.TOTAL_AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnMostPopulationState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenUSCensusData_WhenSortedOnTotalArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.TOTAL_AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(177.0, censusCSV[0].totalArea, 0.0);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(563626.0, censusCSV[0].population, 0.0);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(0.46, censusCSV[0].populationDensity, 0.0);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnStateId_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCensusData_WhenSortedUSAndIndiaOnPopulation_ShouldReturnPopulationState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
        String indiaSortedData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION);
        IndiaStateCode[] censusCSV = new Gson().fromJson(indiaSortedData, IndiaStateCode[].class);
        Assert.assertEquals("Sikkim", censusCSV[0].state);


        censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
        String usCensusData = censusAnalyser.getStateWiseSortedCensusData(SortData.POPULATION);
        USCensusCSV[] usCensusCSVS = new Gson().fromJson(usCensusData, USCensusCSV[].class);
        Assert.assertEquals("Wyoming", usCensusCSVS[0].state);

        System.out.println(censusCSV[0].state + "  " + usCensusCSVS[0].state);
    }
}



