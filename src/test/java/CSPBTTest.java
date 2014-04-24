import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import cspsolver.instance.tools.solver.Solver;

public class CSPBTTest {

	OutputStream os;
	PrintStream ps;
	
	@Before
	public void setup()	{
		os = new ByteArrayOutputStream(); 
		ps = new PrintStream(os); 
		System.setOut(ps); 
	}

	@Test
	public void test3qc() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/3qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:0"));
	}
	
	@Test
	public void test3qi() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/3qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:0"));
	}
	
	@Test
	public void test4qc() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/4qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test4qs() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/4qs.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test5qi() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/5qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:10"));
	}
	
	@Test
	public void test6qc() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/6qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:4"));
	}
	
	@Test
	public void test6qi() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/6qi.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:4"));
	}
	
	
	@Test
	public void test_20_8_100_20() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/20_8_100_20.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:15"));
	}
	

	@Test
	public void test_chain4_conflicts() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/chain4-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}

	
	@Test
	public void test_ColAustralia_conflicts() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/ColAustralia-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:18"));
	}
	
	@Test
	public void test_ColAustralia_intension() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/ColAustralia-intension.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:18"));
	}

	@Test
	public void test_ColK4_conflicts() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/ColK4-conflicts.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:2"));
	}
	
	@Test
	public void test_zebra_extension() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/zebra-extension.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_zebra_intension_binary() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/zebrai.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_zebra_supports2() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/zebra-supports2.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:1"));
	}
	
	@Test
	public void test_12qc() {
		String[] args = { "-a", "BT", "-f", "src/main/resources/12qc.xml", "-p", "n" };
		Solver.main(args);
		assertTrue(os.toString().contains("Solutions:14200"));
	}
	
}


