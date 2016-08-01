package org.jenkinsci.main.edithelp;

import java.io.*;

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
        
        String dirName = Jenkins.getInstance().getRootDir().toString()+"/helpmanager";
        File f2 = new File(dirName);
        if(!f2.exists())
            f2.mkdirs();
        StringBuilder sb = new StringBuilder();
        File f1 = new File(dirName+"/"+class_name+".html");
        //URL file = Klass.java(clazz).getResource(class_name + ".html");
        //check if the file exists
        if (f1.exists()) {
            BufferedReader in = new BufferedReader(new FileReader(dirName+"/"+class_name+".html"));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }

            return sb.toString();
        }
        else {
            return null;
        }
    }

    public String getUpdateMyString() throws IOException {

        //process get request
        String class_name = Stapler.getCurrentRequest().getParameter("class");
        String updated_class_name = Stapler.getCurrentRequest().getParameter("textArea");

        if (class_name != null) {

            String dirName = Jenkins.getInstance().getRootDir().toString()+"/helpmanager";
            File f2 = new File(dirName);
            if(!f2.exists())
                f2.mkdirs();

            File newFile = new File(dirName + "/" + class_name + ".html");
            //check if the file exists
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(newFile);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(updated_class_name);
            out.flush();
            out.close();

        }


        return Jenkins.getInstance().getRootDir().toString()+"/helpmanager";
        //return (class_name+updated_class_name);
    }
}
