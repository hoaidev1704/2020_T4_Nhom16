package banks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Run {
	private Get ex;

	public Run()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ex = new Get();
		String fileName = ex.getFileName();
		List<DataRecord> data = ex.getData();

//		loadDataToStaging("D:/TestJsoup/TestJsoup/csvFile/" + fileName);
		

	}

	public void loadDimensionDataToStaging() {

	}

	public void loadDataToStaging(String filePath)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/staging", "root", "");

		String loadDataQuery = "LOAD DATA INFILE '" + filePath
				+ "' INTO TABLE data FIELDS ENCLOSED BY '\"' TERMINATED BY ',' ESCAPED BY '\"' LINES TERMINATED BY '\\n'";

		String loadBanksQuery = "LOAD DATA INFILE '" + "D:/TestJsoup/TestJsoup/csvFile/dim_file/banks.csv"
				+ "' INTO TABLE dim_bank FIELDS ENCLOSED BY '\"' TERMINATED BY ',' ESCAPED BY '\"' LINES TERMINATED BY '\\n'";

		String loadCurrentQuery = "LOAD DATA INFILE '" + "D:/TestJsoup/TestJsoup/csvFile/dim_file/current-unit.csv"
				+ "' INTO TABLE dim_current FIELDS ENCLOSED BY '\"' TERMINATED BY ',' ESCAPED BY '\"' LINES TERMINATED BY '\\n'";

//		PreparedStatement ps = conn.prepareStatement(loadBanksQuery, Statement.RETURN_GENERATED_KEYS);
//		ps.execute();
//
//		ps = conn.prepareStatement(loadCurrentQuery, Statement.RETURN_GENERATED_KEYS);
//		ps.execute();

		PreparedStatement ps = conn.prepareStatement(loadDataQuery, Statement.RETURN_GENERATED_KEYS);
		ps.execute();

//			Statement s = conn.createStatement();
//			s.executeQuery(query);
//			ResultSet rs = s.executeQuery(query);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
	}

	public static void main(String[] args)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		loadCsvFileToMySQL();
		new Run();
	}
}
