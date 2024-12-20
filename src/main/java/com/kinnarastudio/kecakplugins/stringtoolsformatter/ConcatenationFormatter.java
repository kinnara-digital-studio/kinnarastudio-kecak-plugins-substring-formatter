package com.kinnarastudio.kecakplugins.stringtoolsformatter;

import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormat;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.plugin.base.Plugin;
import org.joget.plugin.base.PluginManager;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.ResourceBundle;

public class ConcatenationFormatter extends DataListColumnFormatDefault {
    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        AppDefinition appDefinition = AppUtil.getCurrentAppDefinition();
        DataListColumnFormat formatter1 = getPluginFormatter("string1");
        DataListColumnFormat formatter2 = getPluginFormatter("string2");
        final String delimiter = AppUtil.processHashVariable(getPropertyString("delimiter"), null, null, null);
        return (formatter1 == null ? "" : formatter1.format(dataList, column, row, value))
                + (delimiter.isEmpty() ? "" : delimiter)
                + (formatter2 == null ? "" : formatter2.format(dataList, column, row, value));
    }

    @Nullable
    private DataListColumnFormat getPluginFormatter(String propertyName) {
        PluginManager pluginManager = (PluginManager)AppUtil.getApplicationContext().getBean("pluginManager");
        Map<String, Object> plugin = (Map<String, Object>)getProperty(propertyName);
        Map<String, Object> pluginProperties = (Map<String, Object>)plugin.get("properties");
        String pluginClassName = String.valueOf(plugin.get("className"));
        Plugin pluginObject = pluginManager.getPlugin(pluginClassName);
        if(pluginObject instanceof DataListColumnFormat) {
            ((DataListColumnFormat) pluginObject).setProperties(pluginProperties);
            return ((DataListColumnFormat) pluginObject);
        }
        return null;
    }

    @Override
    public String getName() {
        return AppPluginUtil.getMessage("concatenationFormatter.title", getClassName(), "/messages/ConcatenationFormatter");
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
        return AppUtil.readPluginResource(getClassName(), "/properties/ConcatenationFormatter.json", null, false, "/messages/ConcatenationFormatter");
    }
}
