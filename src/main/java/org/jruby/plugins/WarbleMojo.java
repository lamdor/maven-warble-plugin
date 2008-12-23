package org.jruby.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.jruby.Ruby;
import org.jruby.RubyRuntimeAdapter;
import org.jruby.runtime.GlobalVariable;
import org.jruby.javasupport.JavaEmbedUtils;

import java.util.ArrayList;

/**
 * Uses Warble to change your Rails/Rack application into a WAR
 *
 * @goal warble
 * @phase package
 */
public class WarbleMojo extends AbstractMojo {

    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    public void execute() throws MojoExecutionException, MojoFailureException {
        Ruby rubyRuntime = JavaEmbedUtils.initialize(new ArrayList());

        GlobalVariable mavenProject =
                new GlobalVariable(rubyRuntime, "$maven_project", JavaEmbedUtils.javaToRuby(rubyRuntime, project));
        rubyRuntime.defineVariable(mavenProject);

        RubyRuntimeAdapter rubyRuntimeAdapter = JavaEmbedUtils.newRuntimeAdapter();

        rubyRuntimeAdapter.eval(rubyRuntime, "load 'warbler-maven.rb'");
    }

}
