package org.jruby.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.jruby.Ruby;
import org.jruby.runtime.GlobalVariable;
import org.jruby.javasupport.JavaEmbedUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * Uses Warble to change your Rails/Rack application into a WAR
 *
 * @goal warble
 * @phase package
 */
@SuppressWarnings({"JavaDoc", "UnusedDeclaration"})
public class WarbleMojo extends AbstractMojo {

    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     *
     * @component
     */
    private ArtifactHandlerManager artifactHandlerManager;

    /**
     * @parameter expression="${plugin.artifactMap}"
     * @required
     * @readonly
     *
     */
    protected Map pluginArtifactMap;


    public void execute() throws MojoExecutionException, MojoFailureException {
        Ruby ruby = JavaEmbedUtils.initialize(Arrays.asList("warbler/lib", "rake/lib"));

        setupMavenProjectGlobalVariable(ruby);
        setupArtifactHandlerGlobalVariable(ruby);
        setupPluginArtifacts(ruby);

        JavaEmbedUtils.newRuntimeAdapter().eval(ruby, "load 'warbler-maven.rb'");
    }

    private void setupArtifactHandlerGlobalVariable(Ruby ruby) {
        GlobalVariable artifactHandlerManagerVar =
                new GlobalVariable(ruby, "$artifact_handler",
                        JavaEmbedUtils.javaToRuby(ruby, artifactHandlerManager.getArtifactHandler("warble")));
        ruby.defineVariable(artifactHandlerManagerVar);
    }

    private void setupPluginArtifacts(Ruby ruby) {
        GlobalVariable pluginArtifactVar =new GlobalVariable(ruby, "$warble_plugin_artifacts",
                JavaEmbedUtils.javaToRuby(ruby, pluginArtifactMap.values()));
        ruby.defineVariable(pluginArtifactVar);
    }

    private void setupMavenProjectGlobalVariable(Ruby ruby) {
        GlobalVariable mavenProject =
                new GlobalVariable(ruby, "$maven_project", JavaEmbedUtils.javaToRuby(ruby, project));
        ruby.defineVariable(mavenProject);
    }

}
