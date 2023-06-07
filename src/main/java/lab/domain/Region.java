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

public class Region {
    private int id;
    private String name;

    public Region() {
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

    public static ArrayList<Region> getRegionsFromExcel (XSSFWorkbook workbook) throws IOException, InvalidFormatException {
        ArrayList<Region> regions = new ArrayList<>();

        Sheet sheet = workbook.getSheet("regions");
        int rowNum = sheet.getLastRowNum();

        for(int i = 1; i <= rowNum; i++){
            Region region = new Region();
            Row row = sheet.getRow(i);
            region.setId((int) row.getCell(0).getNumericCellValue());
            region.setName(row.getCell(1).getStringCellValue());

            regions.add(region);
        }
        return regions;
    }

    public static ArrayList<Region> getRegionsFromDB(ResultSet rs) throws SQLException {
        ArrayList<Region> regions = new ArrayList<>();
        while (rs.next()){
            Region region = new Region();
            region.setId(rs.getInt("id"));
            region.setName(rs.getString("region_name"));
            regions.add(region);
        }
        return regions;
    }
}
