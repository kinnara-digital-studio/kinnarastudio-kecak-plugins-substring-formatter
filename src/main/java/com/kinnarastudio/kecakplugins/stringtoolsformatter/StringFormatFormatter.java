package com.kinnarastudio.kecakplugins.stringtoolsformatter;

import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.PluginManager;

import java.util.Arrays;
import java.util.ResourceBundle;

public class StringFormatFormatter extends DataListColumnFormatDefault {
    public final static String LABEL = "String Format Formatter";

    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        final String format = getFormat();
        final Object[] arguments = getArguments();
        return String.format(format, arguments);
    }

    @Override
    public String getName() {
        return LABEL;
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
        return LABEL;
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }

    @Override
    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClassName(), "/properties/StringFormatFormatter.json");
    }

    protected String getFormat() {
        return getPropertyString("format");
    }

    protected Object[] getArguments() {
        return Arrays.stream(getPropertyGrid("arguments"))
                .map(m -> {
                    final String value = String.valueOf(m.get("value"));
                    final String dataType = String.valueOf(m.get("dataType"));
                    try {
                        switch (dataType) {
                            case "INTEGER":
                                return Integer.parseInt(value);
                            case "FLOAT":
                                return Float.parseFloat(value);
                            case "LONG":
                                return Long.parseLong(value);
                            case "DOUBLE":
                                return Double.parseDouble(value);
                            default:
                                return value;
                        }
                    } catch (NumberFormatException e) {
                        LogUtil.error(getClassName(), e, "Error parsing value [" + value + "] as [" + dataType + "]");
                        return value;
                    }
                })
                .toArray(Object[]::new);
    }
}
