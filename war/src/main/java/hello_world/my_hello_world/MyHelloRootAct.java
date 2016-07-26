package hello_world.my_hello_world;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.RootAction;
import org.apache.tools.ant.taskdefs.Recorder;
import sun.misc.URLClassPath;

import javax.swing.*;

@Extension
public class MyHelloRootAct extends Recorder.ActionChoices implements RootAction {

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

    public String getMyString() {

        return "Hello Jenkins!";
    }



}