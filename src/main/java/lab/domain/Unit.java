package lab.domain;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Unit {
    private int id;
    private String code;
    private String name;
    private int site;
    private String status;
    private String type;
    private String model;
    private String unitClass;
    private boolean ruDesign;
    private int operator;
    private int nsssSuplier;
    private int thermalCapacity;
    private int grossCapacity;
    private int netCapacity;
    private LocalDate constructionStart;
    private LocalDate commercialOperation;
    private LocalDate dateShutDown;
    private double enrichment;
    private int loadFactor;
    private double burnup;
    private double firstLoad;

    public Unit() {
    }


    public int getNetCapacity() {
        return netCapacity;
    }

    public void setNetCapacity(int netCapacity) {
        this.netCapacity = netCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(String unitClass) {
        this.unitClass = unitClass;
    }

    public boolean isRuDesign() {
        return ruDesign;
    }

    public void setRuDesign(boolean ruDesign) {
        this.ruDesign = ruDesign;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getNsssSuplier() {
        return nsssSuplier;
    }

    public void setNsssSuplier(int nsssSuplier) {
        this.nsssSuplier = nsssSuplier;
    }

    public int getThermalCapacity() {
        return thermalCapacity;
    }

    public void setThermalCapacity(int thermalCapacity) {
        this.thermalCapacity = thermalCapacity;
    }

    public int getGrossCapacity() {
        return grossCapacity;
    }

    public void setGrossCapacity(int grossCapacity) {
        this.grossCapacity = grossCapacity;
    }

    public LocalDate getConstructionStart() {
        return constructionStart;
    }

    public void setConstructionStart(LocalDate constructionStart) {
        this.constructionStart = constructionStart;
    }

    public LocalDate getCommercialOperation() {
        return commercialOperation;
    }

    public void setCommercialOperation(LocalDate commercialOperation) {
        this.commercialOperation = commercialOperation;
    }

    public LocalDate getDateShutDown() {
        return dateShutDown;
    }

    public void setDateShutDown(LocalDate dateShutDown) {
        this.dateShutDown = dateShutDown;
    }

    public double getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(double enrichment) {
        this.enrichment = enrichment;
    }

    public int getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(int loadFactor) {
        this.loadFactor = loadFactor;
    }

    public double getBurnup() {
        return burnup;
    }

    public void setBurnup(double burnup) {
        this.burnup = burnup;
    }

    public double getFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(double firstLoad) {
        this.firstLoad = firstLoad;
    }

    public static ArrayList<Unit> getUnitsFromExcel(XSSFWorkbook workbook){
        ArrayList<Unit> units = new ArrayList<>();

        Sheet sheet = workbook.getSheet("units");
        int rowNum = sheet.getLastRowNum();
        for(int i = 1; i <= rowNum; i++){
            Row row = sheet.getRow(i);
            Unit unit = new Unit();
            unit.setId((int) row.getCell(0).getNumericCellValue());
            unit.setCode(row.getCell(1).getStringCellValue());
            unit.setName(row.getCell(2).getStringCellValue());
            unit.setSite((int) row.getCell(3).getNumericCellValue());
            unit.setStatus(row.getCell(4).getStringCellValue());
            unit.setType(row.getCell(5).getStringCellValue());
            unit.setModel(row.getCell(6).getStringCellValue());
            unit.setUnitClass(row.getCell(7).getStringCellValue());
            unit.setRuDesign(row.getCell(8).getBooleanCellValue());
            unit.setOperator((int) row.getCell(9).getNumericCellValue());
            unit.setNsssSuplier((int) row.getCell(10).getNumericCellValue());
            unit.setThermalCapacity((int) row.getCell(11).getNumericCellValue());
            unit.setGrossCapacity((int) row.getCell(12).getNumericCellValue());
            unit.setNetCapacity((int) row.getCell(13).getNumericCellValue());
            if(row.getCell(14).getDateCellValue() != null){
                unit.setConstructionStart(row.getCell(14).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                unit.setConstructionStart(null);
            }
            if(row.getCell(15).getDateCellValue() != null){
                unit.setCommercialOperation(row.getCell(15).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                unit.setCommercialOperation(null);
            }
            if (row.getCell(16).getDateCellValue() != null){
                unit.setDateShutDown(row.getCell(16).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                unit.setDateShutDown(null);
            }
            unit.setEnrichment(row.getCell(17).getNumericCellValue());
            unit.setLoadFactor((int)row.getCell(18).getNumericCellValue());

            units.add(unit);
        }
        return units;
    }

    public static ArrayList<Unit> getUnitsFromDB(ResultSet rs) throws SQLException {
        ArrayList<Unit> units = new ArrayList<>();
        while(rs.next()){
            Unit unit = new Unit();
            unit.setId(rs.getInt("id"));
            unit.setCode(rs.getString("code"));
            unit.setName(rs.getString("unit_name"));
            unit.setSite(rs.getInt("site"));
            unit.setStatus(rs.getString("status"));
            unit.setType(rs.getString("type"));
            unit.setModel(rs.getString("model"));
            unit.setUnitClass(rs.getString("class"));
            unit.setRuDesign(rs.getBoolean("ru_design"));
            unit.setOperator(rs.getInt("operator"));
            unit.setNsssSuplier(rs.getInt("nsss_supplier"));
            unit.setThermalCapacity(rs.getInt("thermal_capacity"));
            unit.setGrossCapacity(rs.getInt("net_capacity"));
            unit.setNetCapacity(rs.getInt("gross_capacity"));

            if(rs.getDate("construction_start") != null){
                unit.setConstructionStart(rs.getDate("construction_start").toLocalDate());
            } else {
                unit.setConstructionStart(null);
            }

            if(rs.getDate("commercial_operation") != null){
                unit.setCommercialOperation(rs.getDate("commercial_operation").toLocalDate());
            } else {
                unit.setCommercialOperation(null);
            }

            if(rs.getDate("date_shutdown") != null){
                unit.setDateShutDown(rs.getDate("date_shutdown").toLocalDate());
            } else {
                unit.setDateShutDown(null);
            }
            unit.setEnrichment(rs.getDouble("enrichment"));
            unit.setLoadFactor(rs.getInt("load_factor"));
            unit.setBurnup(rs.getDouble("burnup"));
            unit.setFirstLoad(rs.getDouble("first_load"));

            units.add(unit);
        }
        return units;
    }
}
