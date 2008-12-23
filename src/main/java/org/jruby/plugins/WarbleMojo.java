package org.jruby.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.jruby.Ruby;
import org.jruby.RubyRuntimeAdapter;
import org.jruby.runtime.GlobalVariable;
import org.jruby.javasupport.JavaEmbedUtils;

import java.util.ArrayList;
import java.util.List;

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


    private Ruby ruby;


    public void execute() throws MojoExecutionException, MojoFailureException {
        List<String> loadPaths = new ArrayList<String>();
        loadPaths.add("warbler/lib");
        loadPaths.add("rake/lib");
        ruby = JavaEmbedUtils.initialize(loadPaths);

        setupMavenProjectGlobalVariable();
        setupArtifactHandlerGlobalVariable();

        JavaEmbedUtils.newRuntimeAdapter().eval(ruby, "load 'warbler-maven.rb'");
    }

    private void setupArtifactHandlerGlobalVariable() {
        GlobalVariable artifactHandlerManagerVar =
                new GlobalVariable(ruby, "$artifact_handler",
                        JavaEmbedUtils.javaToRuby(ruby, artifactHandlerManager.getArtifactHandler("warble")));
        ruby.defineVariable(artifactHandlerManagerVar);
    }

    private void setupMavenProjectGlobalVariable() {
        GlobalVariable mavenProject =
                new GlobalVariable(ruby, "$maven_project", JavaEmbedUtils.javaToRuby(ruby, project));
        ruby.defineVariable(mavenProject);
    }

}
