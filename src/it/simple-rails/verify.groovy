import java.util.jar.JarInputStream

File warFile = new File("src/it/simple-rails/tmp/simple-rails-1.0-SNAPSHOT.war")
assert warFile.exists()

FileInputStream fileInputStream = new FileInputStream(warFile)
JarInputStream jarInputStream = new JarInputStream(fileInputStream)

Map warManifest = jarInputStream.getManifest().entries
println "Manifest ${warManifest}"
assert warManifest.containsKey("web.xml")

return true
