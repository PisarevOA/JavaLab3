package lab.domain;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Company {
    private int id;
    private String name;
    private String fullName;
    private int countryId;

    public Company() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public static ArrayList<Company> getCompaniesFromExcel (XSSFWorkbook workbook){
        ArrayList<Company> companies = new ArrayList<>();

        Sheet sheet = workbook.getSheet("companies");
        int rowNum = sheet.getLastRowNum();
        for(int i = 1; i <= rowNum; i++){
            Company company = new Company();
            Row row = sheet.getRow(i);
            company.setId((int) row.getCell(0).getNumericCellValue());
            company.setName(row.getCell(1).getStringCellValue());
            company.setFullName(row.getCell(2).getStringCellValue());
            if(row.getCell(3).getNumericCellValue() != 0){
                company.setCountryId((int) row.getCell(3).getNumericCellValue());
            } else {
                company.setCountryId(1);
            }

            companies.add(company);
        }
        return companies;
    }

    public static ArrayList<Company> hetCompaniesfromDB (ResultSet rs) throws SQLException {
        ArrayList<Company> companies = new ArrayList<>();
        while(rs.next()){
            Company company = new Company();
            company.setId(rs.getInt("id"));
            company.setName(rs.getString("companies_name"));
            company.setFullName(rs.getString("full_name"));
            company.setCountryId(rs.getInt("country_id"));

            companies.add(company);
        }
        return companies;
    }
}
