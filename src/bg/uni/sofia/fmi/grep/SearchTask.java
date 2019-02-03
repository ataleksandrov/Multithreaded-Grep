package bg.uni.sofia.fmi.grep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class SearchTask implements Runnable {

	private Path path;
	private String searchingString;

	public SearchTask(Path path, String searchingString) {
		this.path = path;
		this.searchingString = searchingString;
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
			int rowCnt = 1;
			final String delimiter = ":";
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(searchingString)) {
					System.out.println(path + delimiter + rowCnt + delimiter + line);
				}
				rowCnt++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
