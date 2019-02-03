package bg.uni.sofia.fmi.grep;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		Path path = Paths.get(args[0]);
		String searchingString = args[1];
		int threadCnt = Integer.parseInt(args[2]);
		try (SearchEngine se = new SearchEngine(threadCnt)) {
			se.search(path, searchingString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
