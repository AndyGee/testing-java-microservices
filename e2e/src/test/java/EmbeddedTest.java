import com.google.common.base.Charsets;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileSystemUtils;
import testing.microservices.DemoApp;
import testing.microservices.cfg.FtpConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
  , classes = {DemoApp.class}
  , properties = {
  "i18n.mongodb.url:mongodb://localhost:37817",
  "i18n.mongodb.databasename=test-service-i18n",
  "testing.microservices.ftp.port=33035",
  "kafka.bootstrapServers=localhost:9096",
  "spring.application.name=EmbeddedTest"
})
@EmbeddedKafka(
  topics = {"crisp.common.translation.request.topic", "crisp.common.translation.request.topic"},
  controlledShutdown = true,
  ports = {9096},
  brokerProperties = {
    "log.dir=target/EmbeddedTest",
    "auto.create.topics.enable=true"})
public class EmbeddedTest {

  private static final String TESTCLASS = EmbeddedTest.class.getSimpleName();

  static {
    final File temp = new File("target", TESTCLASS + "-temp");
    temp.deleteOnExit();
    if (!temp.exists() && !temp.mkdirs()) {
      throw new RuntimeException("Failed to create: " + temp);
    }
    System.setProperty("java.io.tmpdir", temp.getAbsolutePath());
  }

  private static MongoServer server;
  private static FakeFtpServer fakeFtpServer;

  @BeforeClass
  public static void beforeClass() {
    server = new MongoServer(new MemoryBackend());
    server.bind("localhost", 37817);

    final File file = new File("target", TESTCLASS);
    file.deleteOnExit();
    FileSystemUtils.deleteRecursively(file);

    if (!file.exists() && !file.mkdirs()) {
      throw new RuntimeException("Failed to create: " + file.getAbsolutePath());
    }
  }

  @AfterClass
  public static void afterClass() {
    server.shutdown();

    if (null != fakeFtpServer) {
      fakeFtpServer.stop();
    }
  }

  @LocalServerPort
  int randomServerPort;

  @Autowired
  private FtpConfig config;

  @Before
  public void before() {

    if (null == fakeFtpServer) {
      fakeFtpServer = FtpTestUtil.getFakeFtpServer(this.config);
    }

    final URI uri = URI.create("http://localhost:" + this.randomServerPort);
  }

  /**
   * This test submits a collection of Translation objects and POSTs them to the service.
   * <p>
   * The response returns sample request status for each request object.
   */
  @Test
  public void submitTranslationRequest() throws Exception {
    simulateTranslationResponseOnFtp("test-" + this.randomServerPort);
    System.out.println("TESTCLASS = " + TESTCLASS);
    boolean run = true;

  }

  private void simulateTranslationResponseOnFtp(final String key) throws FileNotFoundException {
    final String user = this.config.getUser();
    final String home = fakeFtpServer.getUserAccount(user).getHomeDirectory();
    final FileSystem fileSystem = fakeFtpServer.getFileSystem();
    final String ftpDownload = this.config.getDownload().getDir().getSource();
    try {
      fileSystem.add(new DirectoryEntry(home + "/" + ftpDownload));
    } catch (final Exception ignore) {
      //no-op
    }
    final String fileNameResponse = "FTP-test_" + key + ".xml";
    fileSystem.add(new FileEntry(home + "/" + ftpDownload + "/" + fileNameResponse,
      IOUtils.toString(this.getClass().getResourceAsStream("logback.xml"),
        Charsets.UTF_8)));
    assertTrue(fileSystem.exists(home + "/" + ftpDownload + "/" + fileNameResponse));
  }
}
