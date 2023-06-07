package lab.dao;

import lab.Reactor;
import lab.Readers.FileReader;
import lab.Readers.JSONReader;
import lab.Readers.XMLReader;
import lab.domain.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static lab.JFrame.getXmlReader;

public class DBManipulator {

    private static String CREATE_DB = "DROP TABLE IF EXISTS public.units; " +
            "DROP TABLE IF EXISTS public.sites; " +
            "DROP TABLE IF EXISTS public.companies; " +
            "DROP TABLE IF EXISTS public.countries; " +
            "DROP TABLE IF EXISTS public.regions; " +
            "CREATE TABLE IF NOT EXISTS public.regions (\n" +
            "                                              id SERIAL PRIMARY KEY,\n" +
            "                                              region_name TEXT\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS public.countries (\n" +
            "                                                id SERIAL PRIMARY KEY,\n" +
            "                                                country_name TEXT,\n" +
            "                                                subregion TEXT,\n" +
            "                                                region TEXT,\n" +
            "                                                region_id INT NOT NULL DEFAULT 15,\n" +
            "                                                FOREIGN KEY (region_id) REFERENCES public.regions (id) ON DELETE CASCADE\n" +
            "    );\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS public.companies (\n" +
            "                                                id SERIAL PRIMARY KEY,\n" +
            "                                                companies_name TEXT,\n" +
            "                                                full_name TEXT,\n" +
            "                                                country_id INT DEFAULT 1,\n" +
            "                                                FOREIGN KEY (country_id) REFERENCES public.countries (id) ON DELETE CASCADE\n" +
            "    );\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS public.sites (\n" +
            "                                            id SERIAL PRIMARY KEY,\n" +
            "                                            npp_name TEXT,\n" +
            "                                            place INT NOT NULL DEFAULT 1,\n" +
            "                                            owner_id INT NOT NULL DEFAULT 1,\n" +
            "                                            operator INT,\n" +
            "                                            builder INT,\n" +
            "                                            FOREIGN KEY (place) REFERENCES public.countries (id) ON DELETE CASCADE,\n" +
            "    FOREIGN KEY (owner_id) REFERENCES public.companies (id) ON DELETE CASCADE\n" +
            "    );\n" +
            "\n" +
            "CREATE TABLE IF NOT EXISTS public.units (\n" +
            "                                            id SERIAL PRIMARY KEY,\n" +
            "                                            code TEXT,\n" +
            "                                            unit_name TEXT,\n" +
            "                                            site INT DEFAULT 247,\n" +
            "                                            status TEXT,\n" +
            "                                            type TEXT,\n" +
            "                                            model TEXT,\n" +
            "                                            class TEXT,\n" +
            "                                            ru_design TEXT ,\n" +
            "                                            operator INT,\n" +
            "                                            nsss_supplier INT,\n" +
            "                                            thermal_capacity INT,\n" +
            "                                            net_capacity INT,\n" +
            "                                            gross_capacity INT,\n" +
            "                                            construction_start DATE,\n" +
            "                                            commercial_operation DATE,\n" +
            "                                            date_shutdown DATE,\n" +
            "                                            enrichment NUMERIC(6,5),\n" +
            "    load_factor INT DEFAULT 90,\n" +
            "    burnup NUMERIC(6,3) DEFAULT 0,\n" +
            "    first_load NUMERIC(6,3) DEFAULT 0,\n" +
            "    FOREIGN KEY (site) REFERENCES public.sites (id) ON DELETE CASCADE\n" +
            "    );";

    private static final String FILL_REGIONS = "INSERT INTO public.regions(\n" +
            "\tid, region_name)\n" +
            "\tVALUES (?, ?);";

    private static final String FILL_COUNTRIES = "INSERT INTO public.countries(\n" +
            "\tid, country_name, subregion, region, region_id)\n" +
            "\tVALUES (?, ?, ?, ?, ?);";

    private static final String FILL_COMPANIES = "INSERT INTO public.companies(\n" +
            "\tid, companies_name, full_name, country_id)\n" +
            "\tVALUES (?, ?, ?, ?);";

    private static final String FILL_SITES = "INSERT INTO public.sites(\n" +
            "\tid, npp_name, place, owner_id, operator, builder)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?);";

    private static final String FILL_UNITS = "INSERT INTO public.units(\n" +
            "\tid, code, unit_name, site, status, type, model, class, ru_design, operator, nsss_supplier, thermal_capacity, net_capacity, gross_capacity, construction_start, commercial_operation, date_shutdown, enrichment, load_factor)\n" +
            "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_CLASSES = "UPDATE public.units SET class='MAGNOX' WHERE class LIKE '%AGR%'; " +
            "UPDATE public.units SET class='PWR' WHERE class LIKE '%PWR%'; " +
            "UPDATE public.units SET class='CPR-1000' WHERE class LIKE '%CNP%' " +
            "OR class LIKE '%Hualong%' OR class LIKE '%APR%' OR class LIKE '%ACP%' OR class LIKE '%AÑPR-1000%'; " +
            "UPDATE public.units SET class='VVER-1200' WHERE class LIKE '%VVER%'; " +
            "UPDATE public.units SET class='MAGNOX' WHERE class LIKE '%HTGR%'; " +
            "UPDATE public.units SET load_factor=90 WHERE load_factor = 0;";

    private static final String SET_BURNUP_AND_FISTLOAD = "UPDATE public.units\n" +
            "\t SET burnup=?, first_load=?\n " +
            "\tWHERE type lIKE ? OR class LIKE ?;";

    public static final String GET_REACTORS_ANNUAL = "SELECT thermal_capacity*load_factor*365/burnup/1000/100 AS annuel_fuel, unit_name\n" +
            "                       FROM public.units\n" +
            "                       WHERE commercial_operation < '2023-01-01' AND date_shutdown > '2023-12-31'\n" +
            "                       ORDER BY annuel_fuel DESC;";

    private static final String SUBSTRING = "WITH sampleOFUnits AS\n" +
            "                            (SELECT thermal_capacity*load_factor*365/burnup/1000/100 AS annuel_fuel, unit_name, site\n" +
            "                            FROM public.units\n" +
            "                            WHERE commercial_operation < '2023-01-01' AND date_shutdown > '2023-12-31'\n" +
            "                            UNION\n" +
            "                            SELECT first_load AS annuel_fuel, unit_name, site\n" +
            "                            FROM public.units\n" +
            "                            WHERE commercial_operation > '2022-12-31' AND commercial_operation < '2024-01-01'\n" +
            "                            ORDER BY annuel_fuel DESC) ";

    public static final String GET_REGION_ANNUAL = SUBSTRING + "SELECT SUM(annuel_fuel) AS annuel_fuel, region_name\n" +
            "                       FROM sampleOFUnits \n" +
            "                       JOIN public.sites ON sampleOFUnits.site = public.sites.id\n" +
            "                       JOIN public.countries ON public.sites.place = public.countries.id\n" +
            "                       JOIN public.regions ON public.countries.region_id = public.regions.id\n" +
            "                       GROUP BY public.regions.region_name\n" +
            "                       ORDER BY annuel_fuel DESC;";

    public static final String GET_COUNTRY_ANNUAL = SUBSTRING + "SELECT SUM(annuel_fuel) AS annuel_fuel, country_name\n" +
            "                       FROM sampleOFUnits \n" +
            "                       JOIN public.sites ON sampleOFUnits.site = public.sites.id\n" +
            "                       JOIN public.countries ON public.sites.place = public.countries.id\n" +
            "                       GROUP BY public.countries.country_name\n" +
            "                       ORDER BY annuel_fuel DESC;";

    public static final String GET_COMPANY_ANNUAL = SUBSTRING + "SELECT SUM(annuel_fuel) AS annuel_fuel, companies_name\n" +
            "                       FROM sampleOFUnits \n" +
            "                       JOIN public.sites ON sampleOFUnits.site = public.sites.id\n" +
            "                       JOIN public.companies ON public.sites.owner_id = public.companies.id\n" +
            "                       GROUP BY public.companies.companies_name\n" +
            "                       ORDER BY annuel_fuel DESC;";

    public static final String GET_COMPANY = "SELECT * FROM companies";
    public static final String GET_COUNTRY = "SELECT * FROM countries";
    public static final String GET_REGIONS = "SELECT * FROM regions";
    public static final String GET_SITES = "SELECT * FROM sites";

    public static final String GET_REACTORS = "SELECT * FROM units " +
            "WHERE status LIKE '%in operation%';";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS public.units; " +
            "DROP TABLE IF EXISTS public.sites; " +
            "DROP TABLE IF EXISTS public.companies; " +
            "DROP TABLE IF EXISTS public.countries; " +
            "DROP TABLE IF EXISTS public.regions;";


    public static void fillDB(Connection con, XSSFWorkbook workbook) throws IOException, InvalidFormatException, SQLException {
        createDB(con);
        ArrayList<Region> regions = Region.getRegionsFromExcel(workbook);
        ArrayList<Country> countries = Country.getCountriesFromExcel(workbook);
        ArrayList<Company> companies = Company.getCompaniesFromExcel(workbook);
        ArrayList<Unit> units = Unit.getUnitsFromExcel(workbook);
        ArrayList<Site> sites = Site.getSitesFromExcel(workbook);

        fillRegions(con,regions);
        fillCountries(con, countries);
        fillCompanies(con, companies);
        fillSites(con, sites);
        fillUnits(con, units);
        setUpdateClasses(con);
//        updateParamets(con, "D://JavaProjects//lab//src//main//resources//ReactorType.json");
    }

    public static void createDB(Connection con){
        try (PreparedStatement stmt = con.prepareStatement(CREATE_DB)) {
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private   static void fillRegions(Connection con, ArrayList<Region> regions) throws SQLException {
        for (Region region : regions) {
            try (PreparedStatement stmt = con.prepareStatement(FILL_REGIONS)) {
                stmt.setInt(1, region.getId());
                stmt.setString(2, region.getName());
                stmt.execute();
            }
        }
    }

    private static void fillCountries(Connection con, ArrayList<Country> countries) throws SQLException {
        for(Country country: countries){
            try (PreparedStatement stmt = con.prepareStatement(FILL_COUNTRIES)) {
                stmt.setInt(1, country.getId());
                stmt.setString(2, country.getName());
                stmt.setString(3, country.getSubregion());
                stmt.setString(4, country.getRegion());
                stmt.setInt(5, country.getRegionId());
                stmt.execute();
            }
        }
    }

    private static void fillCompanies(Connection con, ArrayList<Company> companies) throws SQLException {
        for(Company company: companies){
            try (PreparedStatement stmt = con.prepareStatement(FILL_COMPANIES)) {
                stmt.setInt(1, company.getId());
                stmt.setString(2, company.getName());
                stmt.setString(3, company.getFullName());
                stmt.setInt(4, company.getCountryId());

                stmt.execute();
            }
        }
    }

    private static void fillSites (Connection con, ArrayList<Site> sites) throws SQLException {
        for(Site site: sites){
            try (PreparedStatement stmt = con.prepareStatement(FILL_SITES)) {
                stmt.setInt(1, site.getId());
                stmt.setString(2, site.getNpp_name());
                stmt.setInt(3, site.getPlace());
                stmt.setInt(4, site.getOwnerId());
                stmt.setInt(5, site.getOperator());
                stmt.setInt(6, site.getBuilder());

                stmt.execute();
            }
        }
    }

    private static void fillUnits (Connection con, ArrayList<Unit> units) throws SQLException {
        for(Unit unit: units){
            try (PreparedStatement stmt = con.prepareStatement(FILL_UNITS)) {
                stmt.setInt(1, unit.getId());
                stmt.setString(2, unit.getCode());
                stmt.setString(3, unit.getName());
                stmt.setInt(4, unit.getSite());
                stmt.setString(5, unit.getStatus());
                stmt.setString(6, unit.getType());
                stmt.setString(7, unit.getModel());
                stmt.setString(8, unit.getUnitClass());
                stmt.setBoolean(9, unit.isRuDesign());
                stmt.setInt(10, unit.getOperator());
                stmt.setInt(11, unit.getNsssSuplier());
                stmt.setInt(12, unit.getThermalCapacity());
                stmt.setInt(13, unit.getGrossCapacity());
                stmt.setInt(14, unit.getNetCapacity());

                if(unit.getConstructionStart() != null){
                    stmt.setDate(15, java.sql.Date.valueOf(unit.getConstructionStart()));
                } else {
                    stmt.setDate(15, null);
                }
                if(unit.getCommercialOperation() != null){
                    stmt.setDate(16, java.sql.Date.valueOf(unit.getCommercialOperation()));
                } else {
                    stmt.setDate(16, null);
                }
                if(unit.getDateShutDown() != null){
                    stmt.setDate(17, java.sql.Date.valueOf(unit.getDateShutDown()));
                } else {
                    stmt.setDate(17, null);
                }
                stmt.setDouble(18, unit.getEnrichment());
                stmt.setInt(19, unit.getLoadFactor());

                stmt.execute();
            }
        }
    }

    private static void setUpdateClasses (Connection con) throws SQLException {
        try (PreparedStatement stmt = con.prepareStatement(UPDATE_CLASSES)) {
            stmt.execute();
        }
    }

    public static ResultSet doQuery(Connection con, String query) {
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateParamets(Connection con, String filePath) throws SQLException {
        XMLReader xmlReader = getXmlReader(); // create start reader and set chain for readers
        FileReader filer = xmlReader.createAndRead(filePath);
        ArrayList<Reactor> reactors = filer.getDs().getReactors();
//        JSONReader jsonReader = new JSONReader();
//        jsonReader.readFile(filePath);
//        ArrayList<Reactor> reactors = jsonReader.getDs().getReactors();
        for(Reactor reactor: reactors){
            try (PreparedStatement stmt = con.prepareStatement(SET_BURNUP_AND_FISTLOAD)) {
                stmt.setDouble(1, reactor.getBurnup());
                stmt.setDouble(2, reactor.getFirst_load());
                stmt.setString(3, "%" +reactor.getType()+"%");
                stmt.setString(4, "%" +reactor.getType()+"%");

                stmt.execute();
            }
        }
    }

    public static void dropTables (Connection con){
        try (PreparedStatement stmt = con.prepareStatement(DROP_TABLE)) {
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
