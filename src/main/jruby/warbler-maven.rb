require 'warbler'

war_name = $maven_project.artifact_id + "-" + $maven_project.version

Warbler::Task.new do |t|
    t.config.autodeploy_dir = "tmp/"
    t.config.war_name = war_name
end

Rake.application["war"].invoke

artifact = $maven_project.artifact
war_file = java.io.File.new("tmp/#{war_name}.war")
artifact.file = war_file
artifact.artifact_handler = $artifact_handler