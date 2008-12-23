package org.jruby.plugins;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Uses Warble to change your Rails/Rack application into a WAR
 *
 * @goal warble
 * @phase package
 */
public class WarbleMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("I was running!!");
    }

}
