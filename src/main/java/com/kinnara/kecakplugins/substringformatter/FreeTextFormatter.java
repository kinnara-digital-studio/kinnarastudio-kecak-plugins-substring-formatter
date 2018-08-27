package com.kinnara.kecakplugins.substringformatter;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;

public class FreeTextFormatter extends DataListColumnFormatDefault {
    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        if(getPropertyString("text").isEmpty())
            return String.valueOf(value);
        return AppUtil.processHashVariable(getPropertyString("text"), null, null, null, AppUtil.getCurrentAppDefinition());
    }

    @Override
    public String getName() {
        return AppPluginUtil.getMessage("freeTextFormatter.title", getClassName(), "/messages/FreeTextFormatter");
    }

    @Override
    public String getVersion() {
        return getClass().getPackage().getImplementationVersion();
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
        return AppUtil.readPluginResource(getClassName(), "/properties/FreeTextFormatter.json", null, false, "/messages/FreeTextFormatter");
    }
}
