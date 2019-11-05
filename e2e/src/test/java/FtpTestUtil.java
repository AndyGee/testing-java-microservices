import org.apache.commons.lang3.SystemUtils;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;
import testing.microservices.cfg.FtpConfig;

import java.io.File;
import java.io.IOException;

public class FtpTestUtil {

  public static FakeFtpServer getFakeFtpServer(final FtpConfig fc) {
    final FakeFtpServer ftps = new FakeFtpServer();
    final int port = fc.getPort();
    ftps.setServerControlPort(port); // use any free port
    final FileSystem fileSystem = SystemUtils.IS_OS_WINDOWS ? new WindowsFakeFileSystem() : new UnixFakeFileSystem();
    final File file = new File("./target");
    final String path;
    try {
      final File f = file.getCanonicalFile();
      if (!f.exists() && !f.mkdirs()) {
        throw new IOException("Failed to create: " + f);
      }
      path = f.getPath();
    } catch (final IOException e) {
      throw new RuntimeException("Failed getCanonicalPath for: " + file);
    }
    fileSystem.add(new DirectoryEntry(path));

    ftps.setFileSystem(fileSystem);
    final UserAccount userAccount = new UserAccount(fc.getUser(), fc.getPassword(), path);
    ftps.addUserAccount(userAccount);
    ftps.start();
    return ftps;
  }
}
