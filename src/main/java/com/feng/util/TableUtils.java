package com.feng.util;

import com.feng.annotaion.Column;
import com.feng.annotaion.Table;

import java.lang.reflect.Field;

/**
 * Created by Fengunion on 2017/6/6.
 */
public class TableUtils {
    public static String getCreateTable(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        //获取表明
        Table table = (Table) clazz.getAnnotation(Table.class);
        String tableName = table.tableName();
        sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";\n");
        sb.append("create table ");
        sb.append(tableName).append("(\n");
        Field[] fields = clazz.getDeclaredFields();
        String primaryKey = "";
        for (int i = 0; i < fields.length; i++) {
            Column column = (Column) fields[i].getAnnotations()[0];
            String field = column.field();
            String type = column.type();
            boolean defaultNull = column.defaultNull();
            sb.append("\t" + field).append(" ").append(type);
            if (defaultNull) {
                if (type.toUpperCase().equals("TIMESTAMP")) {
                    sb.append(",\n");
                } else {
                    sb.append(" DEFAULT NULL,\n");
                }
            } else {
                sb.append(" NOT NULL,\n");
                if (column.primaryKey()) {
                    primaryKey = "PRIMARY KEY (" + field + ")";
                }
            }
        }
        if (!StringUtils.isEmpty(primaryKey)) {
            sb.append("\t").append(primaryKey);
        }
        sb.append("\n) DEFAULT CHARSET=utf8");
        return sb.toString();
    }


}
