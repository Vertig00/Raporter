import operations.FileOperations;
import reports.Report1;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Report1 report1 = new Report1();
        report1.createReportList(FileOperations.open("C:\\Users\\Vertig0\\Desktop\\export.xlsx"));
        try {
            FileOperations.write(Report1.RESULT_CELLS, report1.process());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
