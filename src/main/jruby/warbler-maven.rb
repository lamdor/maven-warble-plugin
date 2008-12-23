$: << File.join(File.dirname(__FILE__), "warbler", "lib")
$: << File.join(File.dirname(__FILE__), "rake", "lib")

require 'warbler'

Warbler::Task.new do |t|
    t.config.autodeploy_dir = "tmp/"
    t.config.war_name = $maven_project.artifact_id + "-" + $maven_project.version
end

Rake.application["war"].invoke