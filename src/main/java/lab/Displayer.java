package lab;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Displayer{

    private final Vector colNames;
    private final Vector data;

    public Displayer(ResultSet resultTable) throws SQLException
    {
        Vector colNames = new Vector();
        Vector data = new Vector();

        ResultSetMetaData md = resultTable.getMetaData();
        int columns = md.getColumnCount();

        for(int i = 1; i <= columns; i++)
        {
            colNames.addElement(md.getColumnName(i));
        }
        while(resultTable.next())
        {
            Vector row = new Vector(columns);
            for(int i = 1; i<= columns; i++)
            {
                row.addElement(resultTable.getObject(i));
            }
            data.addElement(row);
        }
        resultTable.close();

        this.colNames =  colNames;
        this.data =  data;
    }

    public Vector getColNames()
    {
        return this.colNames;
    }

    public Vector getData()
    {
        return this.data;
    }

}