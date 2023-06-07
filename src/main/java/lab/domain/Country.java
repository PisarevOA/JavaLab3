package lab.domain;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Country {
    private int id;
    private String name;
    private String subregion;
    private String region;
    private int regionId;

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public static ArrayList<Country> getCountriesFromExcel(XSSFWorkbook workbook) throws IOException, InvalidFormatException {
        ArrayList<Country> countries = new ArrayList<>();
        Sheet sheet = workbook.getSheet("countries");
        int rowNum = sheet.getLastRowNum();
        for(int i = 1; i <= rowNum; i++){
            Country country = new Country();
            Row row = sheet.getRow(i);
            country.setId((int) row.getCell(0).getNumericCellValue());
            country.setName(row.getCell(1).getStringCellValue());
            country.setSubregion(row.getCell(2).getStringCellValue());
            country.setRegion(row.getCell(3).getStringCellValue());
            country.setRegionId((int) row.getCell(4).getNumericCellValue());
            countries.add(country);
        }
        return countries;
    }

    public static ArrayList<Country> getCountriesFromDB(ResultSet rs) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        while(rs.next()){
            Country country = new Country();
            country.setId(rs.getInt("id"));
            country.setName(rs.getString("country_name"));
            country.setSubregion(rs.getString("subregion"));
            country.setRegion(rs.getString("region"));
            country.setRegionId(rs.getInt("region_id"));

            countries.add(country);
        }
        return countries;
    }
}
