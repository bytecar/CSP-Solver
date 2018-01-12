import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import cspsolver.instance.tools.solver.Solver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSPBTTest {
	OutputStream os;
	PrintStream ps;
	
	@BeforeEach
	public void setup()	{
		os = new ByteArrayOutputStream(); 
		ps = new PrintStream(os); 
		System.setOut(ps);
        //System.setOut(System.out);
	}

    @AfterEach
    public void setdown()	{
        ps.close();
        System.setOut(System.out);
    }

	@Test
	public void test3qc() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/3qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:0"));
	}
	
	@Test
	public void test3qi() {
		String[] args  = new String[]{ "-a", "BT", "-f", "./src/test/java/resources/3qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:0"));
	}
	
	@Test
	public void test4qc() {
		String[] args = { "-a", "BT", "-f", "\"./src/test/java/resources/4qc.xml\"", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test4qs() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/4qs.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test5qi() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/5qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:10"));
	}
	
	@Test
	public void test6qc() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/6qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:4"));
	}
	
	@Test
	public void test6qi() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/6qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:4"));
	}
	
	
	@Test
	public void test_20_8_100_20() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/20_8_100_20.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:15"));
	}
	

	@Test
	public void test_chain4_conflicts() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/chain4-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}

	
	@Test
	public void test_ColAustralia_conflicts() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/ColAustralia-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:18"));
	}
	
	@Test
	public void test_ColAustralia_intension() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/ColAustralia-intension.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:18"));
	}

	@Test
	public void test_ColK4_conflicts() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/ColK4-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test_zebra_extension() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/zebra-extension.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_zebra_intension_binary() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/zebrai.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_zebra_supports2() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/zebra-supports2.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_12qc() {
		String[] args = { "-a", "BT", "-f", "./src/test/java/resources/12qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:14200"));
	}
	
}


