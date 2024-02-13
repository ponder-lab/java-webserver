package au.id.deejay.webserver.server;

import au.id.deejay.webserver.response.ResponseFactory;
import org.junit.After;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author David Jessup
 */
@State(Scope.Benchmark)
public class WebServerExecutorTest {

	private WebServerExecutor executor;

	@TearDown(Level.Invocation)
	public void tearDown() throws Exception {
		if (executor != null && executor.running()) {
			executor.stop();
		}
	}

	@Benchmark
	public void testConstructor() throws Exception {
		executor = new WebServerExecutor(0, 10, 10, mock(ResponseFactory.class));
		assertThat(executor.running(), is(false));
	}

	@Benchmark
	public void run() throws Exception {
		executor = new WebServerExecutor(0, 10, 10, mock(ResponseFactory.class));

		Thread executorThread = new Thread(executor);
		executorThread.start();

		await().until(executorThread::isAlive);

		assertThat(executor.running(), is(true));
	}

	@Benchmark
	public void running() throws Exception {

	}

	@Benchmark
	public void stop() throws Exception {

	}

}
