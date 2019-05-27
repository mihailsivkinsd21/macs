/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JRadioButton;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding.SyncFailure;
import org.jdesktop.beansbinding.ELProperty;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.FocusManager;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import static org.jdesktop.beansbinding.Bindings.createAutoBinding;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.BindingListener;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.ObjectProperty;
import org.jdesktop.beansbinding.Property;
import org.jdesktop.beansbinding.PropertyStateEvent;
import org.jdesktop.beansbinding.PropertyStateListener;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;


/**
 *
 * @author DmitrijsLi
 */
public class IridaBindingGroup<T> {



    private BindingGroup bg = new BindingGroup();
    private Object source;
    private Class sourceClass;
    private UpdateStrategy strategy = UpdateStrategy.READ_WRITE;
    private BindingListener bindingListener;

    public IridaBindingGroup() {

    }

    public IridaBindingGroup(Object source) {
        this.source = source;
        if(source != null) {
            sourceClass = source.getClass();
        }
    }

    public IridaBindingGroup(Object source, Class c) {
        this.source = source;
        sourceClass = c;
    }

    public SyncFailure save() {
        return null;
    }

    public void addEditable(Component component, String source) {
        ELProperty el = ELProperty.create("${" + source + "}");
        BeanProperty bp = BeanProperty.create("editable");
        Binding binding = createAutoBinding(strategy, this.source, el, component, bp);
        binding.setSourceUnreadableValue(false);
        bg.addBinding(binding);
    }

    public void addEnabled(String source, Component... component) {
        for (Component c : component) {
            addEnabled(c, source);
        }
    }
    public void addEnabled(Component component, String source) {
        ELProperty el = ELProperty.create("${" + source + "}");
        BeanProperty bp = BeanProperty.create("enabled");
        Binding binding = createAutoBinding(strategy, this.source, el, component, bp);
        binding.setSourceUnreadableValue(false);
        bg.addBinding(binding);
    }

    public void addProperty(Component component, String property, String source) {
        BeanProperty bp = BeanProperty.create(property);
        ELProperty el = ELProperty.create("${" + source + "}");
        Binding binding = createAutoBinding(strategy, this.source, el, component, bp);
        bg.addBinding(binding);
    }

    public void addVisible(String source, Component... components) {
        for (Component component : components) {
            addVisible(component, source);
        }
    }

    private void addVisible(Component component, String source) {
        ELProperty el = ELProperty.create("${" + source + "}");
        BeanProperty bp = BeanProperty.create("visible");
        Binding binding = createAutoBinding(strategy, this.source, el, component, bp);
        binding.setSourceUnreadableValue(false);
        bg.addBinding(binding);
    }

    public void add(ArrayList<Component> al, String prop) {
        ELProperty el = ELProperty.create("${" + prop + "}");
        BeanProperty editable = BeanProperty.create("editable");
        BeanProperty enabled = BeanProperty.create("enabled");
        for (Component c: al){
            Binding binding = null;
            if (c instanceof JCheckBox || c instanceof JComboBox || c instanceof JButton) {
                binding = createAutoBinding(strategy, source, el, c, enabled);
            } else {
                binding = createAutoBinding(strategy, source, el, c, editable);
            }
            bg.addBinding(binding);
        }
    }

    public void add(ArrayList<Component> al, JCheckBox source) {
        ELProperty el = ELProperty.create("${selected}");
        BeanProperty bp = BeanProperty.create("editable");
        for(Component c: al){
            Binding binding = createAutoBinding(strategy, source, el, c, bp);
            bg.addBinding(binding);
        }
    }

    public void add(String target, JInternalFrame frame) {
        BeanProperty bp = BeanProperty.create("title");
        bind(source, target, frame, bp);
    }
    
    public void add(String target, JButton button) {
        BeanProperty bp = BeanProperty.create("text");
        bind(source, target, button, bp);
    }

    /**
     * Биндим что то на панель(обозначает что вызываем сетер)
     * @param source - поле из source объектра
     * @param target - поле на панели
     * @param panel
     */
    public Binding add(String source, String target, JPanel panel) {
        Property op;
        if (source != null && !source.isEmpty()) {
            validate(source);
            op = ELProperty.create("${" + source + "}");
        } else {
            op = org.jdesktop.beansbinding.ObjectProperty.create();
        }
        validate(panel, target);
        Binding binding = createAutoBinding(strategy, this.source, op, panel, BeanProperty.create(target));
        binding.setSourceUnreadableValue(null);
        bg.addBinding(binding);
        return binding;
    }


    public Binding addOneWayUpdate(String source, String target, JPanel panel) {
        validate(source);
        validate(panel, target);
        ELProperty op = ELProperty.create("${" + source + "}");
        Binding binding = createAutoBinding(UpdateStrategy.READ, this.source, op, panel, BeanProperty.create(target));
        binding.setSourceUnreadableValue(null);
        bg.addBinding(binding);
        return binding;
    }
    
    public Binding addOneWayUpdate(String source, String target) {
        return addNotification(source, target);
    }
    public Binding addNotification(String source, String target) {
        validate(source);
        validate(target);
        ELProperty op = ELProperty.create("${" + source + "}");
        Binding binding = createAutoBinding(UpdateStrategy.READ, this.source, op, this.source, BeanProperty.create(target));
        binding.setSourceUnreadableValue(null);
        bg.addBinding(binding);
        return binding;
    }

    public void add(String source, String target) {
        Binding binding = createAutoBinding(strategy, this.source, ELProperty.create("${" + source + "}"), this.source, BeanProperty.create(target));
        addBinding(binding);
    }

    public void add(String target, JPanel panel) {
        ObjectProperty op = ObjectProperty.create();
        Binding binding = createAutoBinding(strategy, source, op, panel, BeanProperty.create(target));
        bg.addBinding(binding);
    }

    public void add(List<BindingColumn> list, JTable table) {
        JTableBinding tb = SwingBindings.createJTableBinding(strategy, (List) source, table);
        for (BindingColumn bc : list) {
            ELProperty el = ELProperty.create("${" + bc.getName() + "}");
            JTableBinding.ColumnBinding columnBinding = tb.addColumnBinding(el);
            columnBinding.setColumnName(bc.getName());
            columnBinding.setColumnClass(bc.getColumnClass());
            
            if(bc.isEditable() != null){
                columnBinding.setEditable(bc.isEditable());
            }
        }

        bg.addBinding(tb);
        tb.bind();
    }

    public void add(String sourceName, List<BindingColumn> list, JTable table) {
        ELProperty sourceProp = ELProperty.create("${" + sourceName + "}");
        JTableBinding tb = SwingBindings.createJTableBinding(strategy, source, sourceProp, table);
        for (final BindingColumn bc : list) {
            ELProperty el = ELProperty.create("${" + bc.getName() + "}");
            JTableBinding.ColumnBinding columnBinding = tb.addColumnBinding(el);
            columnBinding.setColumnName(sourceName + "." + bc.getName());
            columnBinding.setColumnClass(bc.getColumnClass());
            if(bc.convertorNeeded()){
                columnBinding.setConverter(bc.getConverter());
            }
            if(bc.isEditable() != null){
                columnBinding.setEditable(bc.isEditable());
            }
        }

        tb.setSourceUnreadableValue(Collections.emptyList());
        bg.addBinding(tb);
        tb.bind();
    }

//    public void addTable(String target, String listName, List<BindingColumn> list, JTable table){
//        add(target, listName, list, table);
//    }
    public Binding add(String target, String targetList, String listName, List<BindingColumn> list, JTable table) {
        ELProperty el = ELProperty.create("${" + target + "}");
        BeanProperty bp = BeanProperty.create("selectedElement");
        bg.addBinding(createAutoBinding(strategy, source, el, table, bp));

        Binding binding = add(targetList, listName, list, table);
        return binding;
    }
    /**
     * Биндим таблицу
     * @param target поле на которое бинтим то что выбрано в таблице
     * @param listName имя списка на который биндим содержание
     * @param list список колонок
     * @param table компонент
     */
    public Binding add(String target, String listName, List<BindingColumn> list, JTable table) {

        ELProperty sourceProp = ELProperty.create("${" + listName + "}");        
        Class listClazz = getPropertyType(listName);        
        if (listClazz == null) {
            throw new RuntimeException("property " + listName + " doesn't exist");
        }
        
        JTableBinding tb = SwingBindings.createJTableBinding(strategy, source, sourceProp, table);
        for (BindingColumn bc : list) {
            ELProperty el = ELProperty.create("${" + bc.getName() + "}");
            JTableBinding.ColumnBinding columnBinding = tb.addColumnBinding(el);
            columnBinding.setColumnName(listName + "." + bc.getName());
            Class clazz = bc.getColumnClass();
           if (sourceClass != null) {

                if(clazz == null) {
                    clazz = getPropertyType(target + "." + bc.getName());
                }
                if(clazz == null) {
                    throw new RuntimeException("property " + target + "." + bc.getName() + " doesn't exist");
                }
//                if(bc.convertorNeeded()){
//                    columnBinding.setConverter(bc.getConverter());
//                } else if(isCustomClass(clazz)){
//                    columnBinding.setConverter(new ObjectToStringConverter());
//                }
            }
            columnBinding.setColumnClass(clazz);
            if(bc.isEditable() != null){
                columnBinding.setEditable(bc.isEditable());
            }
        }

        tb.setSourceUnreadableValue(Collections.emptyList());
        bg.addBinding(tb);
        tb.bind();

        ELProperty el = ELProperty.create("${" + target + "}");
        BeanProperty bp = BeanProperty.create("selectedElement");
        if (sourceClass != null) {
            final Class clazz = getPropertyType(target);
            if (classImplementsList(clazz)) {
                bp = BeanProperty.create("selectedElements");
            }
        }
        bg.addBinding(createAutoBinding(strategy, source, el, table, bp));
        return tb;
    }

    /**
     * Биндим комбобокс
     * @param listName - список
     * @param fieldName - имя поля которое будет менятся(selectedItem)
     * @param component - сам комбобокс
     */
    public void add(String listName, String fieldName, JComboBox component) {
        validate(listName);
        ELProperty sourceProp = ELProperty.create("${" + listName + "}");
        bg.addBinding(SwingBindings.createJComboBoxBinding(strategy, source, sourceProp, component));
        Binding binding = bind(source, fieldName, component, BeanProperty.create("selectedItem"));
        binding.setSourceUnreadableValue(null);
    }
    
    public void addSelectedItem(String fieldName, JComboBox component) {
        Binding binding = bind(source, fieldName, component, BeanProperty.create("selectedItem"));
        binding.setSourceUnreadableValue(null);
    }

    public void add(String listName, String fieldName, final JList component) {
        ELProperty sourceProp = ELProperty.create("${" + listName + "}");
        bg.addBinding(SwingBindings.createJListBinding(strategy, source, sourceProp, component));
        BeanProperty bp = BeanProperty.create("selectedElement");
        if (sourceClass != null) {
            final Class clazz = getPropertyType(fieldName);
            if(clazz == null){
                throw new RuntimeException("property " + fieldName + " not found");
            }
            if (classImplementsList(clazz)) {
                bp = BeanProperty.create("selectedElements");
                ELProperty fieldProp = ELProperty.create("${" + fieldName + "}");
                fieldProp.addPropertyStateListener(source, new PropertyStateListener() {

                    @Override
                    public void propertyStateChanged(PropertyStateEvent pse) {
                        if(!PropertyStateEvent.UNREADABLE.equals(pse.getNewValue())) {
                            ListModel model = component.getModel();
                            List<Integer> selectedIndices = new ArrayList();
                            for (Object newValue : (List)pse.getNewValue()) {
                                for(int i = 0; i < model.getSize(); i++){
                                    if(newValue.toString().equals(model.getElementAt(i).toString())){
                                        selectedIndices.add(i);
                                        break;
                                    }
                                }
                            }
                            int[] ret = new int[selectedIndices.size()];
                            for(int i = 0;i < ret.length;i++) {
                                ret[i] = (int) selectedIndices.get(i);
                            }
                            component.setSelectedIndices(ret);
                        }
                    }
                });
            }
        }
        bind(source, fieldName, component, bp);
    }

    public void add(String listName, JComboBox component) {
        ELProperty sourceProp = ELProperty.create("${" + listName + "}");
        bg.addBinding(SwingBindings.createJComboBoxBinding(strategy, source, sourceProp, component));
    }

  
    public void add(String sourceName, JTextArea component) {
        component.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == KeyEvent.VK_TAB) {
                    FocusManager.getCurrentManager().focusNextComponent();
                    evt.consume();
                }
            }
        });
        add(source, sourceName, component);
    }

    public void add(String sourceName, JTextField component) {
        add(source, sourceName, component);
    }

    public void add(String sourceName, JTextPane component) {
        add(source, sourceName, component);
    }

    public Binding addByTyping(String sourceName, JTextComponent component) {
        BeanProperty target = BeanProperty.create("text");
        ELProperty sourceProp = ELProperty.create("${" + sourceName + "}");
        Binding binding = createAutoBinding(strategy, source, sourceProp, component, target);
        if(sourceClass != null){
            final Class clazz = getPropertyType(sourceName);
            if(clazz == null){
                throw new RuntimeException("property for " + sourceName + " not found");
            }
//            Converter converter = getConverter(clazz, component);
//            if (converter != null) {
//                binding.setConverter(converter);
//            }
        }
        binding.setSourceUnreadableValue(null);
        bg.addBinding(binding);
        return binding;
    }
  
    private void add(Object obj, String sourceName, JLabel component){
        BeanProperty target = BeanProperty.create("text");
        bind(obj, sourceName, component, target);
    }


    private void add(Object obj, String sourceName, JCheckBox component) {
        validate(sourceName);
        BeanProperty target = BeanProperty.create("selected");
        bind(obj, sourceName, component, target);
    }

    private void add(Object obj, String sourceName, JRadioButton component) {
        validate(sourceName);
        BeanProperty target = BeanProperty.create("selected");
        bind(obj, sourceName, component, target);
    }

    private Binding add(Object obj, String sourceName, final JTextComponent component) {

        BeanProperty target = BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST");
        ELProperty sourceProp = ELProperty.create("${" + sourceName + "}");
        Binding binding = createAutoBinding(strategy, obj, sourceProp, component, target);
        if(sourceClass != null){
            final Class clazz = getPropertyType(sourceName);
            if(clazz == null){
                throw new RuntimeException("property for " + sourceName + " not found");
            }
//            Converter converter = getConverter(clazz, component);
//            if (converter != null) {
//                binding.setConverter(converter);
//            }
        }
        binding.setSourceUnreadableValue(null);
        bg.addBinding(binding);
        return binding;
    }

    private Binding bind(Object obj, String sourceName, Object component, BeanProperty target) {
        validate(sourceName);
        ELProperty sourceProp = ELProperty.create("${" + sourceName + "}");
        Binding binding = createAutoBinding(strategy, obj, sourceProp, component, target);
        bg.addBinding(binding);
        return binding;
    }

    public void clearBinding() {
        bg.unbind();
        Class newClass = null;
        for (Binding binding : bg.getBindings()) {
            Class oldClass = null;
            if (binding.getSourceObject() != null) {
                oldClass = binding.getSourceObject().getClass();
            }

            if (binding.isManaged()) {
                bg.removeBinding(binding);
            }
        }
    }

    public void rebind(Object newSource) {
        this.source = newSource;
        bg.unbind();
        Class newClass = null;
        if (newSource != null) {
            newClass = newSource.getClass();
        }
        for (Binding binding : bg.getBindings()) {
            Class oldClass = null;
            if (binding.getSourceObject() != null) {
                oldClass = binding.getSourceObject().getClass();
            }

            // Тут иногда происходит фигня, из за того что бин "манагед", кстате непонятно что это значит,
            // Я не разобрался с этим, но фигня сама исчезла
            // Но что-то подсказывает мне, что она вернёться
            //
            // Происходит это в таком случае:
            // Мы редакитруем ячейку в таблице
            // например в SarkastPayments
            // она видать тоже как-то попадает в биндинг
            // и вот она и становиться "манагед"
            // в принципе, я пока все "манагед" бинды просто удаляю

            // В версии что была в Ириде тут был такой вот код
            // Но мне кажиться что он уже устаревший

            // А в ИридаПэйманс было вот так, поэтому я это и оставил
            if (binding.isManaged()) {
                bg.removeBinding(binding);
            } else if(JTable.class.equals(oldClass)){
                binding.setTargetObject(newSource);
            } else if (newClass != null && newClass.equals(oldClass)) {
                binding.setSourceObject(newSource);
            } else if (newClass != null && oldClass == null) {
                binding.setSourceObject(newSource);
            } else if (oldClass != null && oldClass.isAssignableFrom(newClass)) {
                binding.setSourceObject(newSource);
            }
        }
        bg.bind();

    }

    public void addBinding(Binding binding) {
        bg.addBinding(binding);
    }

    

    public void bind() {
        bg.bind();
    }

    private boolean classImplementsList(Class clazz) {
        if(List.class.equals(clazz)){
            return true;
        }

        if(clazz.getInterfaces() != null){
            for(Class cl:clazz.getInterfaces()){
                if(List.class.equals(cl) && cl.equals(clazz)){
                    return true;
                }
            }
        }
        return false;
    }

    private String getEqualName(String name){
        String[] parts = name.split("_");
        if(nameEndsWithEquals(name)){
            return parts[0];
        }
        return name;
    }

    private boolean nameEndsWithEquals(String name){
        String[] parts = name.split("_");
        if(parts.length != 2){
            return false;
        }
        if("equals".equals(parts[1])){
            return true;
        }
        return false;
    }

    private boolean isCustomClass(Class clazz) {
        return    !clazz.equals(String.class)
               && !clazz.equals(Integer.class)
               && !clazz.equals(BigDecimal.class)
               && !clazz.equals(Boolean.class)
               && !clazz.equals(Date.class)
               && !"boolean".equals(clazz.toString());
    }

    public BindingGroup getGroup() {
        return bg;
    }

    public boolean isBound() {
        if(bg.getBindings().size() > 0){
            return bg.getBindings().get(0).isBound();
        }
        return false;
    }

    public Object getSource() {
        return source;
    }


    public void getProperties() {
        for(Binding b: bg.getBindings()){
            if(b instanceof JTableBinding){
                String tableProp = ((ELProperty)((JTableBinding)b).getSourceProperty()).toString();
                String name = tableProp.substring(tableProp.indexOf("${") + 2, tableProp.indexOf("}"));
                Prop p = new Prop(name);
                for(Object o : ((JTableBinding)b).getColumnBindings()){
                    p.getProps().add(((JTableBinding.ColumnBinding) o).getColumnName());
                }
            }
        }
    }

    private void validate(String sourceName) {
        getPropertyType(sourceName);
    }
    private void validate(Object source, String sourceName) {
        getPropertyType(source.getClass(), sourceName);
    }

    private Class getPropertyType(String sourceName) {
        return getPropertyType(sourceClass, sourceName);
    }

    private Class getPropertyType(Class sourceClass, String sourceName) {
        if (sourceClass == null) {
            return String.class;
        }
        if (sourceName.startsWith("!")) {
            sourceName = sourceName.substring(1);
        }
        String[] props = sourceName.split("\\.");
        try {
            BeanInfo currentBeanInfo = Introspector.getBeanInfo(sourceClass);
            Class cl = sourceClass;
            for(String prop: props){
                boolean found = false;
                for (PropertyDescriptor pd : currentBeanInfo.getPropertyDescriptors()) {
                    if (pd.getName().equals(prop)) {
                        currentBeanInfo = Introspector.getBeanInfo(pd.getPropertyType());
                        cl = pd.getPropertyType();
                        found = true;
                        break;
                    }
                }
                if (! found && ! List.class.isAssignableFrom(cl)) {
                    throw new RuntimeException("Property type not found for '" + prop + "' of " + sourceName + "(" + cl + ")");
                }
            }
            return cl;
        } catch (IntrospectionException ex) {
            Logger.getLogger(IridaBindingGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("Property type not found");
    }

    public class Prop {
        String name;
        HashSet<String> props = new HashSet<String>();

        public Prop (String name){
            this.name = name;
        }

        public HashSet<String> getProps(){
            return props;
        }
    }
}
