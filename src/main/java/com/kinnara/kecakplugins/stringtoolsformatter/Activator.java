package com.kinnara.kecakplugins.stringtoolsformatter;

import java.util.ArrayList;
import java.util.Collection;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    protected Collection<ServiceRegistration> registrationList;

    public void start(BundleContext context) {
        registrationList = new ArrayList<ServiceRegistration>();

        //Register plugin here
        registrationList.add(context.registerService(SubStringFormatter.class.getName(), new SubStringFormatter(), null));
        registrationList.add(context.registerService(ConcatenationFormatter.class.getName(), new ConcatenationFormatter(), null));
        registrationList.add(context.registerService(FreeTextFormatter.class.getName(), new FreeTextFormatter(), null));
    }

    public void stop(BundleContext context) {
        for (ServiceRegistration registration : registrationList) {
            registration.unregister();
        }
    }
}