package testing.microservices.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FTP server configuration
 * <p>
 * "testing.microservices.ftp.upload.dir.source=src/test/resources/",
 * "testing.microservices.ftp.upload.dir.target=c:/data",
 * "testing.microservices.ftp.download.dir.source=CRISP_out",
 * "testing.microservices.ftp.host=localhost",
 * "testing.microservices.ftp.port=35001",
 * "testing.microservices.ftp.user=user",
 * "testing.microservices.ftp.password=password",
 */
@Component
@ConfigurationProperties("testing.microservices.ftp")
public class FtpConfig {

  private String host = "localhost";
  private int port = 35035;
  private String user = "user";
  private String password = "password";
  private Upload upload = new Upload();
  private Download download = new Download();

  public String getHost() {
    return this.host;
  }

  public void setHost(final String host) {
    this.host = host;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(final int port) {
    this.port = port;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(final String user) {
    this.user = user;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Upload getUpload() {
    return this.upload;
  }

  public void setUpload(final Upload upload) {
    this.upload = upload;
  }

  public Download getDownload() {
    return this.download;
  }

  public void setDownload(final Download download) {
    this.download = download;
  }

  public static class Upload {

    private Dir dir = new Dir();

    public Dir getDir() {
      return this.dir;
    }

    public void setDir(final Dir dir) {
      this.dir = dir;
    }

    public static class Dir {

      private String target = "ftp-data";

      public String getTarget() {
        return this.target;
      }

      public void setTarget(final String target) {
        this.target = target;
      }
    }
  }

  public static class Download {

    private Dir dir = new Dir();

    public Dir getDir() {
      return this.dir;
    }

    public void setDir(final Dir dir) {
      this.dir = dir;
    }

    public static class Dir {

      private String source = "ftp_out";

      public String getSource() {
        return this.source;
      }

      public void setSource(final String source) {
        this.source = source;
      }

    }
  }
}
