package lab.dao;

import lab.domain.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

public class Excel {
    static HSSFWorkbook workbook;
//    static final String downloadPath = "C://Users//masha//Downloads";

    public Excel() {
        workbook = new HSSFWorkbook();
    }

    public static void createExcel(Connection con, String filePath) throws SQLException {
        Excel excel = new Excel();
        excel.fillExcel(con);
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fillExcel(Connection con) throws SQLException {
        ArrayList<Region> regions = Region.getRegionsFromDB(DBManipulator.doQuery(con, DBManipulator.GET_REGIONS));
        ArrayList<Country> countries = Country.getCountriesFromDB(DBManipulator.doQuery(con, DBManipulator.GET_COUNTRY));
        ArrayList<Company> companies = Company.hetCompaniesfromDB(DBManipulator.doQuery(con, DBManipulator.GET_COMPANY));
        ArrayList<Site> sites = Site.getSitesFromDB(DBManipulator.doQuery(con, DBManipulator.GET_SITES));
        ArrayList<Unit> units = Unit.getUnitsFromDB(DBManipulator.doQuery(con, DBManipulator.GET_REACTORS));
        fillRegions(regions);
        fillCompany(companies);
        fillCountry(countries);
        fillSite(sites);
        fillUnits(units);
    }

    public static void fillRegions (ArrayList<Region> regions){
        HSSFSheet sheet = workbook.createSheet("regions");
        int rowNum = 1;
        for(Region region: regions){
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(region.getId());
            row.createCell(1).setCellValue(region.getName());
            rowNum += 1;
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("region_name");
    }
    public static void fillCompany (ArrayList<Company> companies){
        HSSFSheet sheet = workbook.createSheet("companies");
        int rowNum = 1;
        for(Company company: companies){
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(company.getId());
            row.createCell(1).setCellValue(company.getName());
            row.createCell(2).setCellValue(company.getFullName());
            row.createCell(3).setCellValue(company.getCountryId());
            rowNum += 1;
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("companies_name");
        row.createCell(2).setCellValue("full_name");
        row.createCell(3).setCellValue("country_id");
    }

    public static void fillCountry(ArrayList<Country> countries){
        HSSFSheet sheet = workbook.createSheet("countries");
        int rowNum = 1;
        for(Country country: countries){
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(country.getId());
            row.createCell(1).setCellValue(country.getName());
            row.createCell(2).setCellValue(country.getSubregion());
            row.createCell(3).setCellValue(country.getRegion());
            row.createCell(4).setCellValue(country.getRegionId());
            rowNum += 1;
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("country_name");
        row.createCell(2).setCellValue("subregion");
        row.createCell(3).setCellValue("region");
        row.createCell(4).setCellValue("region_id");
    }

    public static void fillSite(ArrayList<Site> sites){
        HSSFSheet sheet = workbook.createSheet("sites");
        int rowNum = 1;
        for(Site site: sites){
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(site.getId());
            row.createCell(1).setCellValue(site.getNpp_name());
            row.createCell(2).setCellValue(site.getPlace());
            row.createCell(3).setCellValue(site.getOwnerId());
            row.createCell(4).setCellValue(site.getOperator());
            row.createCell(5).setCellValue(site.getBuilder());
            rowNum += 1;
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("npp_name");
        row.createCell(2).setCellValue("place");
        row.createCell(3).setCellValue("owner_id");
        row.createCell(4).setCellValue("operator");
        row.createCell(5).setCellValue("builder");

    }

    public static void fillUnits(ArrayList<Unit> units) {
        HSSFSheet sheet = workbook.createSheet("units");
        int rowNum = 1;
        for(Unit unit: units){
            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(unit.getId());
            row.createCell(1).setCellValue(unit.getCode());
            row.createCell(2).setCellValue(unit.getName());
            row.createCell(3).setCellValue(unit.getSite());
            row.createCell(4).setCellValue(unit.getStatus());
            row.createCell(5).setCellValue(unit.getType());
            row.createCell(6).setCellValue(unit.getModel());
            row.createCell(7).setCellValue(unit.getUnitClass());
            row.createCell(8).setCellValue(unit.isRuDesign());
            row.createCell(9).setCellValue(unit.getOperator());
            row.createCell(10).setCellValue(unit.getNsssSuplier());
            row.createCell(11).setCellValue(unit.getThermalCapacity());
            row.createCell(12).setCellValue(unit.getGrossCapacity());
            row.createCell(13).setCellValue(unit.getNetCapacity());
            row.createCell(14).setCellValue(unit.getConstructionStart());
            row.createCell(15).setCellValue(unit.getCommercialOperation());
            row.createCell(16).setCellValue(unit.getDateShutDown());
            row.createCell(17).setCellValue(unit.getEnrichment());
            row.createCell(18).setCellValue(unit.getLoadFactor());
            row.createCell(19).setCellValue(unit.getBurnup());
            row.createCell(20).setCellValue(unit.getFirstLoad());

            rowNum += 1;
        }
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("code");
        row.createCell(2).setCellValue("unit_name");
        row.createCell(3).setCellValue("site");
        row.createCell(4).setCellValue("status");
        row.createCell(5).setCellValue("type");
        row.createCell(6).setCellValue("model");
        row.createCell(7).setCellValue("class");
        row.createCell(8).setCellValue("ru_design");
        row.createCell(9).setCellValue("operator");
        row.createCell(10).setCellValue("nsss_supplier");
        row.createCell(11).setCellValue("thermal_capacity");
        row.createCell(13).setCellValue("gross_capacity");
        row.createCell(14).setCellValue("net_capacity");
        row.createCell(15).setCellValue("construction_start");
        row.createCell(16).setCellValue("commercial_operation");
        row.createCell(17).setCellValue("date_shutdown");
        row.createCell(18).setCellValue("enrichment");
        row.createCell(19).setCellValue("load_factor");
        row.createCell(20).setCellValue("burnup");
        row.createCell(21).setCellValue("first_load");
    }

    public static XSSFWorkbook getBook(String fileName) throws IOException, InvalidFormatException {
        File file = new File(fileName);
        return new XSSFWorkbook(file);
    }
}
