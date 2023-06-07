package lab.domain;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Site {

    private int id;
    private String npp_name;
    private int place;
    private int ownerId;
    private int operator;
    private int builder;

    public Site() {
    }

    public Site(int id, String npp_name, int place, int ownerId, int operator, int builder) {
        this.id = id;
        this.npp_name = npp_name;
        this.place = place;
        this.ownerId = ownerId;
        this.operator = operator;
        this.builder = builder;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNpp_name() {
        return npp_name;
    }

    public void setNpp_name(String npp_name) {
        this.npp_name = npp_name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getBuilder() {
        return builder;
    }

    public void setBuilder(int builder) {
        this.builder = builder;
    }

    public static ArrayList<Site> getSitesFromExcel(XSSFWorkbook workbook){
        ArrayList<Site> sites = new ArrayList<>();

        Sheet sheet = workbook.getSheet("sites");
        int rowNum = sheet.getLastRowNum();
        for(int i = 1; i <= rowNum; i++){

            Site site = new Site();
            Row row = sheet.getRow(i);

            site.setId((int) row.getCell(0).getNumericCellValue());
            site.setNpp_name(row.getCell(1).getStringCellValue());
            site.setPlace((int) row.getCell(2).getNumericCellValue());

            site.setOwnerId((int)row.getCell(3).getNumericCellValue());

            if(row.getCell(4).getCellType().toString().equals("STRING")){
                site.setOperator(0);
            } else {
                site.setOperator((int) row.getCell(4).getNumericCellValue());
            }
            if (row.getCell(5).getCellType().toString().equals("STRING")){
                site.setBuilder(0);
            } else {
                site.setBuilder((int) row.getCell(5).getNumericCellValue());
            }
            sites.add(site);
        }
        Site site = new Site(0, "NO DATA", 1, 1, 0, 0);
        sites.add(site);
        return sites;
    }
    public static ArrayList<Site> getSitesFromDB (ResultSet rs) throws SQLException {
        ArrayList<Site> sites = new ArrayList<>();
        while(rs.next()){
            Site site = new Site();
            site.setId(rs.getInt("id"));
            site.setNpp_name(rs.getString("npp_name"));
            site.setPlace(rs.getInt("place"));
            site.setOwnerId(rs.getInt("owner_id"));
            site.setOperator(rs.getInt("operator"));
            site.setBuilder(rs.getInt("builder"));

            sites.add(site);
        }
        return sites;
    }
}
