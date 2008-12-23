import org.jruby.plugins.WarFile

WarFile warFile = new WarFile("src/it/simple-rails/tmp/simple-rails-1.0-SNAPSHOT.war")
assert warFile.exists()
assert warFile.contains("WEB-INF/web.xml")

return true
