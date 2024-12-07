package com.kinnarastudio.kecakplugins.stringtoolsformatter;

import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.plugin.base.PluginManager;

import java.util.ResourceBundle;

/**
 * @author aristo
 *
 * String Replace Formatter
 */
public class StringReplaceFormatter extends DataListColumnFormatDefault {
    @Override
    public String format(DataList dataList, DataListColumn dataListColumn, Object row, Object value) {
        String replaceWith = getPropertyString("replaceWith");
        String replaceWhat = getPropertyString("regex");
        if(replaceWhat.isEmpty())
            return String.valueOf(value);
        return String.valueOf(value).replaceAll(replaceWhat, replaceWith);
    }

    @Override
    public String getName() {
        return "String Replace Formatter";
    }

    @Override
    public String getVersion() {
        PluginManager pluginManager = (PluginManager) AppUtil.getApplicationContext().getBean("pluginManager");
        ResourceBundle resourceBundle = pluginManager.getPluginMessageBundle(getClassName(), "/messages/BuildNumber");
        String buildNumber = resourceBundle.getString("buildNumber");
        return buildNumber;
    }

    @Override
    public String getDescription() {
        return getClass().getPackage().getImplementationTitle();
    }

    @Override
    public String getLabel() {
        return getName();
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }

    @Override
    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClassName(), "/properties/StringReplaceFormatter.json", null, false, "/messages/StringReplaceFormatter");
    }
}
