package autocoding.common;
import java.util.List;
public class Table {
	//properties
	private String name;
	private String dbName;
	private List<Column> columns;
	private List<Column> pkColumns;
	//getter and setter
	public String getName() {
		return name;
	}
	public String getNameUpper() {
		return name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Column> getPkColumns() {
		return pkColumns;
	}
	public void setPkColumns(List<Column> pkColumns) {
		this.pkColumns = pkColumns;
	}
}
