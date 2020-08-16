package com.kinnara.kecakplugins.stringtoolsformatter;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubStringFormatter extends DataListColumnFormatDefault {
    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        String result = value == null ? "" : value.toString();

        try {
            if (!getPropertyString("regex").isEmpty())
                result = byPattern(result);

            int start, end;
            try {
                start = Integer.valueOf(getPropertyString("start"));
            } catch (NumberFormatException e) {
                start = 0;
            }

            try {
                end = Integer.valueOf(getPropertyString("end"));
            } catch (NumberFormatException e) {
                end = result.length();
            }

            return result.substring(start, end);
        } catch (Exception e) {
            return result;
        }
    }

    private String byPattern(String value) {
        int sequence;
        try {
            sequence = Integer.parseInt(getPropertyString("sequence"));
        }catch (NumberFormatException e) {
            sequence = 0;
        }

        Pattern p = Pattern.compile(getPropertyString("regex"));
        Matcher m = p.matcher(value == null ? "" : value);

        for(int i = 0; m.find(); i++) {
            if(i == sequence)
                return m.group();
        }

        return "";
    }

    @Override
    public String getName() {
        return AppPluginUtil.getMessage("subStringFormatter.title", getClassName(), "/messages/SubStringFormatter");
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
        return AppUtil.readPluginResource(getClassName(), "/properties/SubStringFormatter.json", null, false, "/messages/SubStringFormatter");
    }
}
