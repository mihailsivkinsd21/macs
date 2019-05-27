package test;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.TableCellEditor;
import org.jdesktop.beansbinding.Converter;



/**
 *
 * @author DmitrijsLi
 */
public class BindingColumn {

    boolean isCustomEditor() {
        return editor != null;
    }

    public TableCellEditor getEditor() {
        return editor;
    }

    public enum DateFormat {
          GERMAN
        , GERMAN_WITH_TIME
        , ISO_MONTH
        , DAY_OF_WEEK
        , DAY_WITH_TIME
        , TIME_WITH_SECONDS
    }

    private String name;
    private Class aClass;
    private Class editClass;
    private DateFormat dateFormat;
    private String separator;
    private TableCellEditor editor;
    private Boolean editable;

    public BindingColumn(String name, DateFormat df) {
        this(name, false, Date.class);
        this.dateFormat = df;
    }

    public BindingColumn(String listName, String separator) {
        this(listName, String.class, separator);
    }

    public BindingColumn(String listName, Class aClass, String separator) {
        this(listName, aClass);
        this.separator = separator;
        this.editable = false;
    }

    public BindingColumn(String listName, String separator, DateFormat df) {
        this(listName, Date.class);
        this.separator = separator;
        this.dateFormat = df;
    }

    public BindingColumn(String name, Class showAsClass, Class editAsClass) {
        this(name, showAsClass);
        this.editClass = editAsClass;
    }

    public BindingColumn(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public BindingColumn(String name) {
        this.name = name;
    }

    public BindingColumn(String name, boolean editable) {
        this(name);
        this.editable = editable;
    }

    public BindingColumn(String name, boolean editable, Class aClass) {
        this(name, aClass);
        this.editable = editable;
    }

    public BindingColumn(String name, Class aClass, TableCellEditor editor) {
        this(name, aClass);
        this.editor = editor;
        this.editable = true;
    }

    public Class getColumnClass() {
        return aClass;
    }

    public void setColumnClass(Class aClass) {
        this.aClass = aClass;
    }

    public Class getColumnEditClass() {
        return editClass;
    }

    public void setColumnEditClass(Class editClass) {
        this.editClass = editClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Boolean isEditable() {
        return editable;
    }

    public boolean convertorNeeded() {
        return false;
    }


    public Converter getConverter() {
//        if(isListConverterNeeded()){
//            return makeListConverter();
//        }
//        if(isDateConverterNeeded()){
//            return makeDateConverter();
//        }
//        if(isBigDecimalConverterNeeded()){
//            return makeBigDecimalConverter();
//        }
//        if(isPGinetConverterNeeded()){
//            return makePGinetConverter();
//        }
//        return new ObjectToStringConverter();
        return null;
    }
    

    
}
