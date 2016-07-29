package hello_world.my_hello_world;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.RootAction;
import hudson.model.View;
import jenkins.model.Jenkins;
import org.apache.tools.ant.taskdefs.Recorder;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.lang.Klass;
import sun.misc.URLClassPath;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

@Extension
public class MyHelloRootAct extends Recorder.ActionChoices implements RootAction {
    public transient final Class<?> clazz = (Class)getClass();

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return "helpmanager";
    }

    public String getMyString() throws IOException {

        //process get request
        String class_name = Stapler.getCurrentRequest().getParameter("class");

        URL file = Klass.java(clazz).getResource(class_name + ".html");
        //check if the file exists
        if (file != null) {
            //read the file
            BufferedInputStream in = (BufferedInputStream) file.getContent();
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            String strFileContents = "";
            while((bytesRead = in.read(contents)) != -1) {
                strFileContents += new String(contents, 0, bytesRead);
            }
            //return needed string
            return strFileContents;
        }
        else {
            return null;
        }
    }
}
