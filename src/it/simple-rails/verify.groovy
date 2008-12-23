import java.util.jar.JarInputStream
import java.util.zip.ZipInputStream
import java.io.InputStream
import java.util.zip.ZipEntry

File warFile = new File("src/it/simple-rails/tmp/simple-rails-1.0-SNAPSHOT.war")
assert warFile.exists()

FileInputStream war = new FileInputStream(warFile)

boolean zipContains(InputStream inputSteam, String name) {
    ZipInputStream zip = new ZipInputStream(inputSteam)
    while ((entry = zip.nextEntry) != null) {
        if (entry.name == name) {
            return true
        }
    }
    return false
}

assert zipContains(war, "WEB-INF/web.xml")

return true
