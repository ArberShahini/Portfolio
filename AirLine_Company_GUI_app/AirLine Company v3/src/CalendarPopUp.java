import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Objects;
public class CalendarPopUp extends JFrame {
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
    private GregorianCalendar calendar = new GregorianCalendar();
    private JScrollPane calendarContainer;
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints c = new GridBagConstraints();
    private JButton left = new JButton("\uD83E\uDC78");
    private JButton right = new JButton("\uD83E\uDC7A");
    private JLabel month = new JLabel(sdf.format(calendar.getTime()));
    private JButton yearLeft = new JButton("⇇");
    private JButton yearRight = new JButton("⇉");
    private String date;
    private JButton submit = new JButton("Submit");
    private boolean dateHasBeenSelected = false;
    private <T> void addComponent(T cmp, JPanel panel, int x, int y, int width, int anchor){
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.anchor = anchor;
        panel.add((Component)cmp, c);
    }
    CalendarPopUp(){
        super("Select Date");
        month.setFont(new Font("Arial", Font.PLAIN, 25));
        calendarContainer = createCalendarTable();

        left.addActionListener(e -> {
            int m = calendar.get(GregorianCalendar.MONTH);
            m--;
            if(m < 0) calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR) - 1, GregorianCalendar.DECEMBER, 1);
            else calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), m, 1);

            month.setText(sdf.format(calendar.getTime()));
            panel.remove(calendarContainer);
            calendarContainer = createCalendarTable();
            addComponent(calendarContainer, panel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        right.addActionListener(e -> {
            int m = calendar.get(GregorianCalendar.MONTH);
            m++;
            if(m > 11) calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR) + 1, GregorianCalendar.JANUARY, 1);
            else calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), m, 1);

            month.setText(sdf.format(calendar.getTime()));
            panel.remove(calendarContainer);
            calendarContainer = createCalendarTable();
            addComponent(calendarContainer, panel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        yearRight.addActionListener(e -> {
            int y = calendar.get(GregorianCalendar.YEAR);
            y++;
            calendar = new GregorianCalendar(y, calendar.get(GregorianCalendar.MONTH), 1);

            month.setText(sdf.format(calendar.getTime()));
            panel.remove(calendarContainer);
            calendarContainer = createCalendarTable();
            addComponent(calendarContainer, panel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        yearLeft.addActionListener(e -> {
            int y = calendar.get(GregorianCalendar.YEAR);
            y--;
            calendar = new GregorianCalendar(y, calendar.get(GregorianCalendar.MONTH), 1);

            month.setText(sdf.format(calendar.getTime()));
            panel.remove(calendarContainer);
            calendarContainer = createCalendarTable();
            addComponent(calendarContainer, panel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        submit.addActionListener(e -> {
            if(dateHasBeenSelected){
                setVisible(false);
                dateHasBeenSelected = false;
            }
            else JOptionPane.showMessageDialog(null, "Please select a date");
        });

        month.setPreferredSize(new Dimension(220, 50));
        c.insets = new Insets(5,5,5,5);
        addComponent(yearLeft, panel, 0, 0, 1, GridBagConstraints.LINE_START);
        addComponent(left, panel, 1, 0, 1, GridBagConstraints.LINE_START);
        addComponent(month, panel, 2, 0, 1, GridBagConstraints.CENTER);
        addComponent(right, panel, 3, 0, 1, GridBagConstraints.LINE_END);
        addComponent(yearRight, panel, 4, 0, 1, GridBagConstraints.LINE_END);
        addComponent(calendarContainer, panel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(submit, panel, 0,2, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        add(panel);

        setSize(500,400);
        setVisible(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public String getDate() {
        return date;
    }
    private JScrollPane createCalendarTable(){
        final int[] selectedColumn = new int[1];
        String [] columns = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), 1);
        String [][] data = new String[6][7];
        int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK);
        int currDate = 1;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                if(i == 0 && j == 0) j = dayOfWeek - 1;
                if(currDate > calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) break;
                data[i][j] = String.valueOf(currDate);
                currDate++;
            }
        }

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 7; j++){
                if(data[i][j] == null) data[i][j] = "";
            }
        }

        JTable table = new JTable(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public boolean isRowSelected(int row) {
                return false;
            }
        };

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                table.getColumnModel().getColumn(selectedColumn[0]).setCellRenderer(new DefaultTableCellRenderer());
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String calendarTableData = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                if(Objects.equals(calendarTableData, "")){
                    dateHasBeenSelected = false;
                    return;
                }

                String instanceDate;
                if(Objects.equals(calendarTableData, "") || Objects.equals(calendarTableData, null)) return;
                if(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString().length() == 1) instanceDate = "0";
                else instanceDate = "";
                instanceDate += (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                if(instanceDate.length() == 1) date = "0" + date;
                SimpleDateFormat formater = new SimpleDateFormat("-MM-yyyy");
                date = instanceDate + formater.format(calendar.getTime());
                dateHasBeenSelected = true;

                table.getColumnModel().getColumn(table.getSelectedColumn()).setCellRenderer(new DefaultTableCellRenderer(){
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if(row == table.getSelectedRow() && column == table.getSelectedColumn()) {
                            l.setBackground(new Color(75,110,175));
                            selectedColumn[0] = table.getSelectedColumn();
                        }
                        else l.setBackground(new Color(70,73,75));
                        return l;
                    }
                });

                revalidate();
                repaint();
            }
        });

        for(int i = 0; i < 7; i++){
            table.getColumnModel().getColumn(i).setMaxWidth(50);
        }
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableContainer = new JScrollPane(table);
        tableContainer.setPreferredSize(new Dimension(350, 220));

        return tableContainer;
    }
}