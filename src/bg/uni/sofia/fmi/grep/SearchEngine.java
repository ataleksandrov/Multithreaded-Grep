package bg.uni.sofia.fmi.grep;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SearchEngine implements AutoCloseable {

	private ExecutorService executor;

	public SearchEngine(int maxThreads) {
		executor = Executors.newFixedThreadPool(maxThreads);
	}

	public void search(final Path path, String searchingString) {
		if (!Files.isDirectory(path)) {
			executor.execute(new SearchTask(path, searchingString));
		} else {
			try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
				for (final Path p : paths) {
					search(p, searchingString);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() throws Exception {
		executor.shutdown();
		final int time = 60;
		try {
			while (!executor.awaitTermination(time, TimeUnit.SECONDS)) {
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
