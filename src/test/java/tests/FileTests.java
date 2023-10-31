package tests;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.ResumeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import static com.codeborne.pdftest.PDF.containsText;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileTests {
    ClassLoader cl = FileTests.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void zipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("Test.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.equals("example.csv")){
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    String[] firstRow = content.get(0);
                    Assertions.assertArrayEquals(new String[] {"Name","Job Title",
                            "Address","State","City"}, firstRow);
                    continue;
                }
                if (name.equals("Test.pdf")){
                    PDF pdf = new PDF(zis);
                    assertThat(pdf, containsText("Introduction"));
                    continue;
                }
                if (name.equals("List.xls")){
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals("Mexico",
                                    xls.excel.getSheet("Sheet1")
                                             .getRow(5)
                                             .getCell(1)
                                             .getStringCellValue());
                }

            }
        }

    }

    @Test
    void jsonTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("Test.json");
             InputStreamReader reader = new InputStreamReader(is)) {
            ResumeModel resumeModel = mapper.readValue(reader, ResumeModel.class);

            Assertions.assertEquals("Alexandr", resumeModel.getName());
            Assertions.assertEquals("Volkov", resumeModel.getSurname());
            Assertions.assertEquals(1990, resumeModel.getYearOfBirth());
            Assertions.assertEquals(resumeModel.getSkills().get(1), "Communication");
        }
    }
}
