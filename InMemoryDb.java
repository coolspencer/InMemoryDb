import java.util.*;

enum DataType {
    String,
    Integer;
}

class Column {
    String columnName;
    DataType columnType;

    Column(String columnName, DataType columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }
}

class Row {
    Object[] values;

    Row(Object[] val) {
        this.values = val;
    }
}

class Table {
    List<Column> columnName;
    String tableName;
    List<Row> listOfRow;

    Table(String tableName, List<Column> columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
        listOfRow = new ArrayList<>();
    }

    public void insertEntry(Object[] row) {
        if (row.length != columnName.size()) {
            System.out.println("Columns size define and row length you are trying to insert are of diffrent length");
            return;
        }
        listOfRow.add(new Row(row));
        return;
    }

    public void printAllRow() {
        for (int i = 0; i < columnName.size(); i++)
            System.out.print(columnName.get(i).columnName + " | ");
        System.out.println();
        for (int i = 0; i < listOfRow.size(); i++) {
            for (int j = 0; j < listOfRow.get(i).values.length; j++) {
                System.out.print(listOfRow.get(i).values[j] + " | ");
            }
            System.out.println();
        }
        return;
    }

    public void filterAndDisplay(String cName, Object value) {
        int cIndex = -1;
        for (int i = 0; i < columnName.size(); i++) {
            if (columnName.get(i).columnName.equals(cName)) {
                cIndex = i;
                break;
            }

        }
        if (cIndex == -1)
            throw new IllegalArgumentException("Column " + cName + " not found.");

        List<Row> tempList = new ArrayList<>();
        for (int i = 0; i < listOfRow.size(); i++) {
            if (listOfRow.get(i).values[cIndex].equals(value))
                tempList.add(listOfRow.get(i));
        }
        if (tempList.size() == 0)
            throw new IllegalArgumentException("No value exist at all rows " + cName + " not found.");
        for (Row temp : tempList) {
            for (Object val : temp.values) {
                System.out.print(val);
            }
            System.out.println();
        }
    }
}

public class InMemoryDb {
    List<Table> tables;

    public InMemoryDb() {
        this.tables = new ArrayList<>();
    }

    public void createTable(String tableName, List<Column> columns) {
        tables.add(new Table(tableName, columns));
    }

    public void deleteTable(String tableName) {
        tables.removeIf(table -> table.tableName.equals(tableName));
    }

    public static void main(String[] args) {
        InMemoryDb db = new InMemoryDb();

        // Create table
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("id", DataType.Integer));
        columns.add(new Column("First name", DataType.String));
        columns.add(new Column("Last name", DataType.String));

        db.createTable("employees", columns);

        // Insert records
        db.tables.get(0).insertEntry(new Object[] { 1, "John", "Snow" });
        db.tables.get(0).insertEntry(new Object[] { 2, "Alice", "Targaryn" });
        db.tables.get(0).insertEntry(new Object[] { 3, "Bob", "Stark" });
        db.tables.get(0).insertEntry(new Object[] { 4, "Bob", "Lannister" });
        db.tables.get(0).insertEntry(new Object[] { 5, "Arya", "Stark" });

        // Print all records
        db.tables.get(0).printAllRow();

        // Filter and display records
        db.tables.get(0).filterAndDisplay("Last name", "Stark");
    }
}