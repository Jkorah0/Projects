import static org.junit.Assert.*;
import org.junit.*;

public class FileDataTest {
    @Test
	public void testToString() {
		FileData fileData = new FileData("test.java", "/Jeremy", "07/15/2004");
		assertEquals("{name: testingFile.java, directory: /user, modified date: 07/15/2004}", fileData.toString());
    }
}
