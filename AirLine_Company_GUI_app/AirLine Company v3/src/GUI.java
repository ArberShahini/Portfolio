import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Objects;

public class GUI extends JFrame {
    //Some main objects
    private String loggedUser;
    private JPanel loginPanel = new JPanel(new GridBagLayout());
    private JPanel registerPanel = new JPanel(new GridBagLayout());
    private AccountDataBase accountDataBase = new AccountDataBase("accounts");
    private TicketsDataBase ticketsDataBase = new TicketsDataBase();
    private BookingsDataBase bookingsDataBase = new BookingsDataBase();
    private CheckInDataBase checkInDataBase = new CheckInDataBase();
    private GridBagConstraints c = new GridBagConstraints();
    private GregorianCalendar calendar = new GregorianCalendar();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMMM yyyy");
    private boolean calendarLoaded = false;
    private boolean firstLoginHasHappened = false;


    //Login Components
    private JLabel loginLabel = new JLabel("Login");
    private JLabel usernameLabelLogin = new JLabel("Username: ");
    private JTextField usernameLogin = new JTextField();
    private JLabel passwordLabelLogin = new JLabel("Password: ");
    private JPasswordField passwordLogin = new JPasswordField();
    private JButton loginButton = new JButton("Login");

    //Register Components
    private JLabel registerLabel = new JLabel("Register");
    private JLabel usernameLabelRegister = new JLabel("Username: ");
    private JTextField usernameRegister = new JTextField();
    private JLabel passwordLabelRegister = new JLabel("Password: ");
    private JPasswordField passwordRegister= new JPasswordField();
    private JPasswordField passwordRegisterDupe= new JPasswordField();
    private JButton registerButton = new JButton("Register");
    private Border border = BorderFactory.createLineBorder(Color.gray);

    //Account Main Components
    private BufferedImage img = ImageIO.read(new File("C:\\Users\\Dell\\Desktop\\Coding\\UML\\AirLine Company v2\\Resources\\pfp.png"));
    private JButton logout = new JButton("Logout");
    private JButton home = new JButton("Home");
    private JLabel userImage = new JLabel();
    private JPanel buttons = new JPanel(new GridBagLayout());
    private JPanel functionPanel = new JPanel(new GridBagLayout());

    //Buttons for buttons panel
    private JScrollPane buttonsScrollPane;
    private JButton changePass = new JButton("Change password");
    private JButton bookings = new JButton("Bookings");
    private JButton searchTickets = new JButton("Search tickets");
    private JButton bookTickets = new JButton("Book tickets");
    private JButton checkIn = new JButton("Check-in");
    private JButton checkedIn = new JButton("Checked-in tickets");
    private JButton adminAddTickets = new JButton("Add a ticket to database");

    //Components for function panel
        //home
        private JLabel welcome = new JLabel("<html>Welcome to the<br>home screen :)</html>");
        //change password
        private JPanel passChangePanel = new JPanel(new GridBagLayout());
        private JLabel changePasswordLabel = new JLabel("Change Password");
        private JPasswordField existingPassword = new JPasswordField();
        private JPasswordField newPassword = new JPasswordField();
        private JPasswordField newPasswordDupe = new JPasswordField();
        private JLabel pass = new JLabel("Old Password: ");
        private JLabel newPass = new JLabel("New Password: ");
        private JButton changePasswordButton = new JButton("Change Password");
        //admin add tickets
        private JPanel adminAddTicketsPanel = new JPanel(new GridBagLayout());
        private JTextField destinationTF = new JTextField();
        private JLabel takeOffDateInput = new JLabel();
        private JButton selectTakeOffDate = new JButton("Select date");
        private CalendarPopUp takeOffDateCal = new CalendarPopUp();
        private JTextField takeoffHourTF = new JTextField();
        private JFormattedTextField priceTF;
        private JFormattedTextField availableCopiesTF;
        private JLabel destinationL = new JLabel("Destination: ");
        private JLabel takeOffDateL = new JLabel("Flight date: ");
        private JLabel takeOffHourL = new JLabel("Take off time: ");
        private JLabel priceL = new JLabel("Price: ");
        private JLabel availableCopiesL = new JLabel("Available copies: ");
        private JButton addTicketsButton = new JButton("Add tickets");
        private JLabel addTix = new JLabel("Add a ticket");

        //search tickets
        private JScrollPane tableCont;
        private JPanel searchTicketsPanel = new JPanel(new GridBagLayout());
        private JLabel searchTicketsTitle = new JLabel("Search tickets");
        private JLabel searchByDestination = new JLabel("Search by destination");
        private JLabel searchbyFlightDate = new JLabel("Search by flight date");
        private JTextField searchByDestinationTF = new JTextField();
        private JScrollPane calendarTablePane;
        private String calendarTableData;
        private JButton monthRight = new JButton("\uD83E\uDC7A");
        private JButton monthLeft = new JButton("\uD83E\uDC78");
        private JLabel month = new JLabel(sdf.format(calendar.getTime()));
        private JButton searchByDestinationBTN = new JButton("Search");
        private JButton combinedSearch = new JButton("Combined Search");

        //book tickets
        private JPanel bookingPanel = new JPanel(new GridBagLayout());
        private JLabel ticketBookingL = new JLabel("Ticket booking");
        private JLabel ticketIDL = new JLabel("Ticket ID: ");
        private JFormattedTextField ticketBookingIDTF;
        private JLabel firstName = new JLabel("Firstname: ");
        private JTextField firstNameTF = new JTextField();
        private JLabel lastName = new JLabel("Lastname: ");
        private JTextField lastNameTF = new JTextField();
        private JLabel gender = new JLabel("Select your gender: ");
        private JCheckBox male = new JCheckBox("Male");
        private JCheckBox female = new JCheckBox("Female");
        private JLabel checkedInBaggage = new JLabel("Checked-in baggage: ");
        private JCheckBox denyBaggageChechIn = new JCheckBox("I don't want a checked in bag");
        private JComboBox baggageSelection;
        private JButton bookTicketButton = new JButton("Book ticket");
        ArrayList<String> comboBoxList = new ArrayList<>();

        //show booked tickets button
        private JScrollPane tablePane;

        //check-in button
        private JLabel checkInLabel = new JLabel("Check-in");
        private JLabel ticketCodeL = new JLabel("Ticket Code: ");
        private JTextField ticketCodeTF = new JTextField();
        private JLabel nationality = new JLabel("Nationality: ");
        private JLabel passportID = new JLabel("Passport ID: ");
        private JLabel passportExpDate = new JLabel("Expiration date: ");
        private JLabel dateOfIssue = new JLabel("Date of issue: ");
        private JLabel email = new JLabel("Email: ");
        private JLabel phoneNumber = new JLabel("Phone number: ");
        private JTextField nationalityTF = new JTextField();
        private JTextField passportIDTF = new JTextField();
        private JLabel passportExpDateInput = new JLabel();
        private JLabel dateOfIssueInput = new JLabel();
        private JTextField emailTF = new JTextField();
        private JTextField phoneNumberTF = new JTextField();
        private JButton completeChechInBTN = new JButton("Complete Check-in");
        private JButton selectExpDate = new JButton("Select date");
        private JButton selectDateOfIssueDate = new JButton("Select date");
        private CalendarPopUp expDateCal = new CalendarPopUp();
        private CalendarPopUp dateOfIssueCal = new CalendarPopUp();

        //checked in section
        private JScrollPane checkedInScrollPane;
        private JPanel checkInInfoPanel = new JPanel(new GridBagLayout());
        private JLabel showTicketCode = new JLabel();
        private JLabel showNationality = new JLabel();
        private JLabel showPassportID = new JLabel();
        private JLabel showExpDate = new JLabel();
        private JLabel showDateOfIssue = new JLabel();
        private JLabel showEmail = new JLabel();
        private JLabel showPhoneNumber = new JLabel();
        private JPanel menu = new JPanel();

    private void loadCheckInPanel(){
        checkInLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        ticketCodeL.setFont(new Font("Arial", Font.PLAIN, 25));
        nationality.setFont(new Font("Arial", Font.PLAIN, 25));
        passportID.setFont(new Font("Arial", Font.PLAIN, 25));
        passportExpDate.setFont(new Font("Arial", Font.PLAIN, 25));
        dateOfIssue.setFont(new Font("Arial", Font.PLAIN, 25));
        email.setFont(new Font("Arial", Font.PLAIN, 25));
        phoneNumber.setFont(new Font("Arial", Font.PLAIN, 25));
        ticketCodeTF.setPreferredSize(new Dimension(200, 25));
        nationalityTF.setPreferredSize(new Dimension(200,25));
        passportIDTF.setPreferredSize(new Dimension(200,25));
        passportExpDateInput.setFont(new Font("Arial", Font.PLAIN, 25));
        dateOfIssueInput.setFont(new Font("Arial", Font.PLAIN, 25));
        emailTF.setPreferredSize(new Dimension(200,25));
        phoneNumberTF.setPreferredSize(new Dimension(200,25));

        addComponent(checkInLabel,functionPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(ticketCodeL, functionPanel, 0, 1, 1, GridBagConstraints.LINE_END);
        addComponent(ticketCodeTF, functionPanel, 1, 1, 1, GridBagConstraints.LINE_START);
        addComponent(nationality, functionPanel, 0, 2, 1, GridBagConstraints.LINE_END);
        addComponent(nationalityTF, functionPanel, 1, 2, 1, GridBagConstraints.LINE_START);
        addComponent(passportID, functionPanel, 0, 3, 1, GridBagConstraints.LINE_END);
        addComponent(passportIDTF, functionPanel, 1, 3, 1, GridBagConstraints.LINE_START);
        addComponent(passportExpDate, functionPanel, 0, 4, 1, GridBagConstraints.LINE_END);
        addComponent(passportExpDateInput, functionPanel, 1, 4, 1, GridBagConstraints.LINE_START);
        addComponent(dateOfIssue, functionPanel, 0, 5, 1, GridBagConstraints.LINE_END);
        addComponent(dateOfIssueInput, functionPanel, 1, 5, 1, GridBagConstraints.LINE_START);
        addComponent(email, functionPanel, 0, 6, 1, GridBagConstraints.LINE_END);
        addComponent(emailTF, functionPanel, 1, 6, 1, GridBagConstraints.LINE_START);
        addComponent(phoneNumber, functionPanel, 0, 7, 1, GridBagConstraints.LINE_END);
        addComponent(phoneNumberTF, functionPanel, 1, 7, 1, GridBagConstraints.LINE_START);
        addComponent(completeChechInBTN, functionPanel, 1, 8, 1, GridBagConstraints.LINE_END);
        addComponent(selectExpDate, functionPanel, 2,4, 1, GridBagConstraints.LINE_START);
        addComponent(selectDateOfIssueDate, functionPanel, 2,5,1,GridBagConstraints.LINE_START);
    }

    private void changeLoggedUser(String newUser){
        loggedUser = newUser;
    }

    private void resetButtonsColor(){
        changePass.setBackground(new Color(78, 80, 82));
        bookings.setBackground(new Color(78, 80, 82));
        searchTickets.setBackground(new Color(78, 80, 82));
        bookTickets.setBackground(new Color(78, 80, 82));
        checkIn.setBackground(new Color(78, 80, 82));
        checkedIn.setBackground(new Color(78, 80, 82));
        adminAddTickets.setBackground(new Color(78, 80, 82));
    }

    private JScrollPane createCalendarTable(GregorianCalendar calendar){
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

        GregorianCalendar finalCalendar = calendar;
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                calendarTableData = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                if(Objects.equals(calendarTableData, "")) return;

                String date = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
                if(date.length() == 1) date = "0" + date;
                SimpleDateFormat formater = new SimpleDateFormat("-MM-yyyy");

                functionPanel.remove(tableCont);
                try {
                    tableCont = createNewTableSearchByFlightDate(date + formater.format(finalCalendar.getTime()));
                } catch (SQLException ex){
                    throw new RuntimeException(ex);
                }

                table.getColumnModel().getColumn(table.getSelectedColumn()).setCellRenderer(new DefaultTableCellRenderer(){
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        if(row == table.getSelectedRow() && column == table.getSelectedColumn()) l.setBackground(new Color(75,110,175));
                        else l.setBackground(new Color(70,73,75));
                        return l;
                    }
                });

                addComponent(tableCont, functionPanel, 1, 0, 1, GridBagConstraints.LINE_END);
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
        tableContainer.setPreferredSize(new Dimension(searchTicketsPanel.getWidth() - 20, 220));

        return tableContainer;
    }
    private JScrollPane createNewBookedTable() throws SQLException {
        String [] columns = {"id", "Ticket Code", "Firstname", "Lastname", "Gender", "Bag Weight(kg)", "Check-in status"};
        ArrayList<String> tableContent = bookingsDataBase.getTableData(loggedUser);
        Object [][] data = new Object[tableContent.size()/7][7];
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

        for(int i = 0; i < tableContent.size()/7; i++){
            for(int j = 0; j < 7; j++) data[i][j] = tableContent.get(i * 7 + j);
        }

        for(int i = 0; i < tableContent.size()/7; i++){
            if(Objects.equals(data[i][6], "0")) data[i][6] = "Waiting Check-in";
            else data[i][6] = "Check-in Completed";
        }

        JTable table = new JTable(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setPreferredSize(new Dimension(functionPanel.getWidth() - 10, 75));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(150);
        table.getColumnModel().getColumn(6).setMinWidth(150);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.setRowHeight(30);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                if(Objects.equals(target.getValueAt(row, 6), "Waiting Check-in")){
                    String message = "Do you wish to continue Check-in for the ticket code " + target.getValueAt(row, 1) + " ?";
                    int buttonPressed = JOptionPane.showConfirmDialog(null, message, "Confirmation Pop-up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(buttonPressed == JOptionPane.YES_OPTION){
                        functionPanel.removeAll();
                        loadCheckInPanel();
                        functionPanel.revalidate();
                        functionPanel.repaint();
                        ticketCodeTF.setText(String.valueOf(target.getValueAt(row, 1)));
                        resetButtonsColor();
                        checkIn.setBackground(new Color(75,110,175));
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "This ticket has already been checked-in");
                }
            }
        });

        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableContainer = new JScrollPane(table);
        tableContainer.setPreferredSize(new Dimension(functionPanel.getWidth() - 10, functionPanel.getHeight() - 10));

        return tableContainer;
    }

    public JScrollPane createNewCheckedInTable() throws SQLException {
        String [] columns = {"Ticket Code"};
        ArrayList<String> tableContent = checkInDataBase.getTableCodes(loggedUser);
        String [][] data = new String[tableContent.size()][1];
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

        for(int i = 0; i < tableContent.size(); i++){
            data[i][0] = tableContent.get(i);
        }

        JTable table = new JTable(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setPreferredSize(new Dimension(functionPanel.getWidth()/2 - 10, 75));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.setRowHeight(30);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                String code = (String)table.getValueAt(row, 0);
                try {
                    ResultSet resultSet = checkInDataBase.getTicketCheckInInfo(code);
                    resultSet.next();
                    showTicketCode.setText(resultSet.getString(2));
                    showNationality.setText("Nationality: \t" + resultSet.getString(3));
                    showPassportID.setText("Passport ID: \t" + resultSet.getString(4));
                    showExpDate.setText("Expiration date: \t" + resultSet.getString(5));
                    showDateOfIssue.setText("Date of issue: \t" + resultSet.getString(6));
                    showEmail.setText("Email: \t\t" + resultSet.getString(7));
                    showPhoneNumber.setText("Phone number: \t" + resultSet.getString(8));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableContainer = new JScrollPane(table);
        tableContainer.setPreferredSize(new Dimension(functionPanel.getWidth()/2 - 10, functionPanel.getHeight() - 10));
        return tableContainer;
    }

    public JScrollPane createNewTableSearchByDestination(String destination) throws SQLException {
        String [] columns = {"ID", "Destination", "Take off date", "Take off hour", "Price", "Available Copies"};
        ArrayList<String> tableContent = ticketsDataBase.searchTicketByDestination(destination);
        return getJScrollPane(columns, tableContent);
    }

    public JScrollPane createNewTableSearchByFlightDate(String date) throws SQLException {
        String [] columns = {"ID", "Destination", "Take off date", "Take off hour", "Price", "Available Copies"};
        ArrayList<String> tableContent = ticketsDataBase.searchTicketByFlightDate(date);
        return getJScrollPane(columns, tableContent);
    }

    public JScrollPane createNewTableCombinedSearch(String destination, String date) throws SQLException {
        String [] columns = {"ID", "Destination", "Take off date", "Take off hour", "Price", "Available Copies"};
        ArrayList<String> tableContent = ticketsDataBase.combinedSearch(destination, date);
        return getJScrollPane(columns, tableContent);
    }

    private JScrollPane getJScrollPane(String[] columns, ArrayList<String> tableContent) {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        String [][] data = new String[tableContent.size()/6][6];
        for(int i = 0; i < tableContent.size()/6; i++){
            for(int j = 0; j < 6; j++){
                data[i][j] = tableContent.get(i * 6 + j);
            }
        }

        JTable table = new JTable(data, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
        table.getTableHeader().setPreferredSize(new Dimension(785, 75));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.setRowHeight(30);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.getColumnModel().getColumn(4).setMaxWidth(120);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                String message = "Do you wish to continue booking the ticket with ID " + target.getValueAt(row, 0) + "?";
                int buttonPressed = JOptionPane.showConfirmDialog(null, message, "Confirmation Pop-up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(buttonPressed == JOptionPane.YES_OPTION){
                    functionPanel.removeAll();
                    functionPanel.add(bookingPanel);
                    revalidate();
                    repaint();
                    resetButtonsColor();
                    bookTickets.setBackground(new Color(75,110,175));
                    searchByDestinationTF.setText("");
                    ticketBookingIDTF.setValue(Integer.parseInt((String) target.getValueAt(row, 0)));
                }
            }
        });

        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableContainer = new JScrollPane(table);
        tableContainer.setPreferredSize(new Dimension(785, 575));
        return tableContainer;
    }

    private void bookingPanelComponentSetup(){
        ticketBookingIDTF = createFormattedTextField();
        ticketBookingL.setFont(new Font("Arial", Font.PLAIN, 35));
        ticketIDL.setFont(new Font("Arial", Font.PLAIN, 25));
        firstName.setFont(new Font("Arial", Font.PLAIN, 25));
        lastName.setFont(new Font("Arial", Font.PLAIN, 25));
        gender.setFont(new Font("Arial", Font.PLAIN, 25));
        checkedInBaggage.setFont(new Font("Arial", Font.PLAIN, 25));
        ticketBookingIDTF.setPreferredSize(new Dimension(200,25));
        firstNameTF.setPreferredSize(new Dimension(200,25));
        lastNameTF.setPreferredSize(new Dimension(200,25));
        baggageSelection = new JComboBox((comboBoxList.toArray()));
    }
    private <T> void addComponent(T cmp, JPanel panel, int x, int y, int width, int anchor){
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = width;
        c.anchor = anchor;
        panel.add((Component)cmp, c);
    }

    private JFormattedTextField createFormattedTextField(){
        NumberFormat doubleFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(doubleFormat){
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.isEmpty()) return null;
                return super.stringToValue(text);
            }
        };
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0);
        return new JFormattedTextField(numberFormatter);
    }

    private void setupSearchComponents(){
        month.setFont(new Font("Arial", Font.PLAIN, 25));
        month.setHorizontalAlignment(JLabel.CENTER);
        searchTicketsTitle.setFont(new Font("Arial", Font.PLAIN, 35));
        searchByDestination.setFont(new Font("Arial", Font.PLAIN, 25));
        searchbyFlightDate.setFont(new Font("Arial", Font.PLAIN, 25));
        searchByDestinationTF.setPreferredSize(new Dimension(300,25));
    }

    private void buttonSetupJScrollPane(JButton b){
        b.setPreferredSize(new Dimension(280, 100));
        b.setFont(new Font("Arial", Font.PLAIN, 20));
    }
    private void componentSetup(){
        c.insets = new Insets(10,5,10,5);
        //login section
        usernameLabelLogin.setFont(new Font("Arial", Font.PLAIN, 25));
        passwordLabelLogin.setFont(new Font("Arial", Font.PLAIN, 25));
        loginLabel.setFont(new Font("Arial", Font.BOLD, 35));
        usernameLogin.setPreferredSize(new Dimension(200,25));
        passwordLogin.setPreferredSize(new Dimension(200, 25));

        //register section
        usernameLabelRegister.setFont(new Font("Arial", Font.PLAIN, 25));
        passwordLabelRegister.setFont(new Font("Arial", Font.PLAIN, 25));
        registerLabel.setFont(new Font("Arial", Font.BOLD, 35));
        usernameRegister.setPreferredSize(new Dimension(200,25));
        passwordRegister.setPreferredSize(new Dimension(200, 25));
        passwordRegisterDupe.setPreferredSize(new Dimension(200, 25));


    }

    private void addAccountPagePanel(String username) throws IOException, SQLException {
        //always on screen account page panel components
        userImage.setBounds(25,0,700,150);
        add(userImage);

        ImageIcon icon = new ImageIcon("Resources//pfp.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(120,120, Image.SCALE_SMOOTH);
        userImage.setIcon(new ImageIcon(newImage));
        userImage.setText(username);
        userImage.setFont(new Font("Arial", Font.PLAIN, 50));
        userImage.setIconTextGap(25);


        menu.setBounds(1006, 25, 500, 100);
        logout.setPreferredSize(new Dimension(200, 95));
        logout.setFont(new Font("Arial", Font.PLAIN, 35));
        home.setPreferredSize(new Dimension(200, 95));
        home.setFont(new Font("Arial", Font.PLAIN, 35));
        menu.add(home);
        menu.add(logout);

        add(menu);

        //scroll pane for function buttons
        c.insets = new Insets(3,3,3,3);
        buttonSetupJScrollPane(changePass);
        buttonSetupJScrollPane(bookings);
        buttonSetupJScrollPane(searchTickets);
        buttonSetupJScrollPane(bookTickets);
        buttonSetupJScrollPane(checkIn);
        buttonSetupJScrollPane(checkedIn);
        buttonSetupJScrollPane(adminAddTickets);

        addComponent(changePass, buttons, 0, 0, 1, GridBagConstraints.PAGE_START);
        addComponent(bookings, buttons, 0, 1, 1, GridBagConstraints.PAGE_START);
        addComponent(searchTickets, buttons, 0, 2, 1, GridBagConstraints.PAGE_START);
        addComponent(bookTickets, buttons, 0, 3, 1, GridBagConstraints.PAGE_START);
        addComponent(checkIn, buttons, 0, 4, 1, GridBagConstraints.PAGE_START);
        addComponent(checkedIn, buttons, 0, 5, 1, GridBagConstraints.PAGE_START);
        if(loggedUser.compareTo("admin") == 0) {
            addComponent(adminAddTickets, buttons, 0, 6, 1, GridBagConstraints.PAGE_START);
        }else{
            buttons.remove(adminAddTickets);
            buttons.revalidate();
            buttons.repaint();
        }

        buttonsScrollPane = new JScrollPane(buttons);
        buttonsScrollPane.setBounds(25, 150, 300, 585);
        buttonsScrollPane.setBorder(border);

        add(buttonsScrollPane);

        //home screen
        welcome.setFont(new Font("Arial", Font.BOLD, 100));
        addComponent(welcome, functionPanel, 0, 0, 1, GridBagConstraints.CENTER);
        functionPanel.setBounds(350, 150, 1110, 585);
        functionPanel.setBorder(border);
        add(functionPanel);
        //log out button action listener
        logout.addActionListener(e -> {
            setTitle("AirLine App");
            loggedUser = "";
            showTicketCode.setText("");
            showNationality.setText("");
            showPassportID.setText("");
            showExpDate.setText("");
            showDateOfIssue.setText("");
            showEmail.setText("");
            showPhoneNumber.setText("");
            takeOffDateInput.setText("");
            dateOfIssueInput.setText("");
            passportExpDateInput.setText("");
            remove(userImage);
            remove(menu);
            remove(buttons);
            remove(functionPanel);
            functionPanel.removeAll();
            remove(buttonsScrollPane);
            add(loginPanel);
            add(registerPanel);
            revalidate();
            repaint();
            accountDataBase.setAccountLoginStatenStateFalse();
            resetButtonsColor();
            buttons.remove(adminAddTickets);
        });

        //home button action listener
        home.addActionListener(e -> {
            searchTicketsPanel.remove(calendarTablePane);
            functionPanel.removeAll();
            functionPanel.add(welcome);
            revalidate();
            repaint();
            resetButtonsColor();
        });

        //change password button setup and action listener
        changePasswordLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        existingPassword.setPreferredSize(new Dimension(200, 25));
        newPassword.setPreferredSize(new Dimension(200, 25));
        newPasswordDupe.setPreferredSize(new Dimension(200, 25));
        pass.setFont(new Font("Arial", Font.PLAIN, 25));
        newPass.setFont(new Font("Arial", Font.PLAIN, 25));
        c.insets = new Insets(5,5,5,5);
        addComponent(changePasswordLabel, passChangePanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(pass, passChangePanel, 0,1,1,GridBagConstraints.LINE_END);
        addComponent(existingPassword, passChangePanel, 1, 1, 1, GridBagConstraints.CENTER);
        addComponent(newPass, passChangePanel, 0, 2, 1, GridBagConstraints.LINE_END);
        addComponent(newPassword, passChangePanel, 1, 2, 1, GridBagConstraints.CENTER);
        addComponent(newPasswordDupe, passChangePanel, 1, 3, 1, GridBagConstraints.CENTER);
        addComponent(changePasswordButton, passChangePanel, 1,4, 1, GridBagConstraints.LINE_END);

        changePass.addActionListener(e -> {
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            functionPanel.add(passChangePanel);
            revalidate();
            repaint();
            resetButtonsColor();
            changePass.setBackground(new Color(75,110,175));
        });

        changePasswordButton.addActionListener(e -> {
            try {
                accountDataBase.changePassword(username, String.valueOf(existingPassword.getPassword()), String.valueOf(newPassword.getPassword()),
                        String.valueOf(newPasswordDupe.getPassword()));
                existingPassword.setText("");
                newPassword.setText("");
                newPasswordDupe.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //add tickets to database (admin button) setup and action listeners
        priceTF = createFormattedTextField();
        availableCopiesTF = createFormattedTextField();
        addTix.setFont(new Font("Arial", Font.PLAIN, 35));
        destinationL.setFont(new Font("Arial", Font.PLAIN, 25));
        takeOffDateL.setFont(new Font("Arial", Font.PLAIN, 25));
        takeOffHourL.setFont(new Font("Arial", Font.PLAIN, 25));
        priceL.setFont(new Font("Arial", Font.PLAIN, 25));
        availableCopiesL.setFont(new Font("Arial", Font.PLAIN, 25));
        destinationTF.setPreferredSize(new Dimension(200, 25));
        takeoffHourTF.setPreferredSize(new Dimension(200, 25));
        takeOffDateInput.setPreferredSize(new Dimension(200, 25));
        priceTF.setPreferredSize(new Dimension(200, 25));
        availableCopiesTF.setPreferredSize(new Dimension(200, 25));
        takeOffDateInput.setFont(new Font("Arial", Font.PLAIN, 25));

        addComponent(addTix, adminAddTicketsPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(destinationL, adminAddTicketsPanel, 0, 1, 1, GridBagConstraints.LINE_END);
        addComponent(destinationTF, adminAddTicketsPanel, 1, 1, 1, GridBagConstraints.CENTER);
        addComponent(takeOffDateL, adminAddTicketsPanel, 0, 2, 1, GridBagConstraints.LINE_END);
        addComponent(takeOffDateInput, adminAddTicketsPanel, 1, 2, 1, GridBagConstraints.CENTER);
        addComponent(takeOffHourL, adminAddTicketsPanel, 0, 3, 1, GridBagConstraints.LINE_END);
        addComponent(takeoffHourTF, adminAddTicketsPanel, 1, 3, 1, GridBagConstraints.CENTER);
        addComponent(priceL, adminAddTicketsPanel, 0, 4, 1, GridBagConstraints.LINE_END);
        addComponent(priceTF, adminAddTicketsPanel, 1, 4, 1, GridBagConstraints.CENTER);
        addComponent(availableCopiesL, adminAddTicketsPanel, 0, 5, 1, GridBagConstraints.LINE_END);
        addComponent(availableCopiesTF, adminAddTicketsPanel, 1, 5, 1, GridBagConstraints.CENTER);
        addComponent(addTicketsButton, adminAddTicketsPanel, 1, 6, 1, GridBagConstraints.LINE_END);
        addComponent(selectTakeOffDate, adminAddTicketsPanel, 2,2,1,GridBagConstraints.LINE_START);

        adminAddTickets.addActionListener(e -> {
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            functionPanel.add(adminAddTicketsPanel);
            revalidate();
            repaint();
            resetButtonsColor();
            adminAddTickets.setBackground(new Color(75,110,175));
        });

        selectTakeOffDate.addActionListener(e -> takeOffDateCal.setVisible(true));

        takeOffDateCal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                takeOffDateInput.setText(takeOffDateCal.getDate());
            }
        });

        addTicketsButton.addActionListener(e -> {
            int id;
            try {
                id = ticketsDataBase.generateTicketID();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(destinationTF.getText().isEmpty() || takeOffDateInput.getText().isEmpty() || takeoffHourTF.getText().isEmpty() ||
                    priceTF.getValue() == null || availableCopiesTF.getValue() == null){
                JOptionPane.showMessageDialog(null, "Please fill all the needed information");
                destinationTF.setText("");
                takeOffDateInput.setText("");
                takeoffHourTF.setText("");
                priceTF.setText("");
                availableCopiesTF.setText("");
                return;
            }
            String dest = destinationTF.getText();
            String takeOffDate = takeOffDateInput.getText();
            String takeOffHour = takeoffHourTF.getText();
            int price = ((int) priceTF.getValue());
            int copies = (int) availableCopiesTF.getValue();
            try {
                if(!ticketsDataBase.ticketExists(dest, takeOffDate, takeOffHour)){
                    ticketsDataBase.addTicketToDatabase(id, dest, takeOffDate, takeOffHour, price, copies);
                    JOptionPane.showMessageDialog(null, "Ticket added successfully");
                }else{
                    JOptionPane.showMessageDialog(null, "Ticket has been already added to the database");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            destinationTF.setText("");
            takeOffDateInput.setText("");
            takeoffHourTF.setText("");
            priceTF.setText("");
            availableCopiesTF.setText("");
        });

        //search tickets menu and button
        searchTicketsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!calendarLoaded){
                    searchTicketsPanel.remove(calendarTablePane);
                    calendarTablePane = createCalendarTable(calendar);
                    addComponent(calendarTablePane, searchTicketsPanel, 0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
                    revalidate();
                    repaint();
                    calendarLoaded = true;
                }
            }
        });
        setupSearchComponents();
        addComponent(searchTicketsTitle, searchTicketsPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        c.insets = new Insets(10, 3, 10, 3);
        addComponent(searchByDestination, searchTicketsPanel, 0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);
        c.insets = new Insets(3,3,3,3);
        addComponent(searchByDestinationTF, searchTicketsPanel, 0, 2, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);
        addComponent(searchByDestinationBTN, searchTicketsPanel, 0, 3, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);

        c.insets = new Insets(10, 3, 10, 3);
        addComponent(searchbyFlightDate, searchTicketsPanel, 0, 4, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);
        c.insets = new Insets(3,3,3,3);

        addComponent(monthLeft, searchTicketsPanel, 0, 5, 1, GridBagConstraints.LINE_START);
        addComponent(month, searchTicketsPanel, 1, 5, 1, GridBagConstraints.CENTER);
        addComponent(monthRight, searchTicketsPanel, 2, 5, 1, GridBagConstraints.LINE_END);
        addComponent(combinedSearch, searchTicketsPanel, 0, 7, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);

        searchTickets.addActionListener(e -> {
            try {
                tableCont = createNewTableSearchByDestination("");
                calendarTablePane = createCalendarTable(calendar);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            addComponent(tableCont, functionPanel, 1, 0, 1, GridBagConstraints.LINE_END);
            addComponent(calendarTablePane, searchTicketsPanel, 0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            addComponent(searchTicketsPanel, functionPanel, 0, 0, 1, GridBagConstraints.LINE_START);
            calendarTablePane.setEnabled(true);
            resetButtonsColor();
            revalidate();
            repaint();
            searchTickets.setBackground(new Color(75,110,175));
        });

        monthRight.addActionListener(e -> {
            int m = calendar.get(GregorianCalendar.MONTH);
            m++;
            if(m > 11) calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR) + 1, GregorianCalendar.JANUARY, 1);
            else calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), m, 1);

            month.setText(sdf.format(calendar.getTime()));
            searchTicketsPanel.remove(calendarTablePane);
            calendarTablePane = createCalendarTable(calendar);
            addComponent(calendarTablePane, searchTicketsPanel, 0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        monthLeft.addActionListener(e -> {
            int m = calendar.get(GregorianCalendar.MONTH);
            m--;
            if(m < 0) calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR) - 1, GregorianCalendar.DECEMBER, 1);
            else calendar = new GregorianCalendar(calendar.get(GregorianCalendar.YEAR), m, 1);

            month.setText(sdf.format(calendar.getTime()));
            searchTicketsPanel.remove(calendarTablePane);
            calendarTablePane = createCalendarTable(calendar);
            addComponent(calendarTablePane, searchTicketsPanel, 0, 6, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
            revalidate();
            repaint();
        });

        searchByDestinationBTN.addActionListener(e -> {
            functionPanel.remove(tableCont);
            try {
                tableCont = createNewTableSearchByDestination(searchByDestinationTF.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            addComponent(tableCont, functionPanel, 1, 0, 1, GridBagConstraints.LINE_END);
            revalidate();
            repaint();
        });

        combinedSearch.addActionListener(e -> {
            if(Objects.equals(calendarTableData, "")) return;
            SimpleDateFormat df = new SimpleDateFormat("-MM-yyyy");
            if(calendarTableData.length() == 1) calendarTableData = "0" + calendarTableData;
            functionPanel.remove(tableCont);
            try {
                tableCont = createNewTableCombinedSearch(searchByDestinationTF.getText(), calendarTableData + df.format(calendar.getTime()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            addComponent(tableCont, functionPanel, 1, 0, 1, GridBagConstraints.LINE_END);
            revalidate();
            repaint();
        });

        bookTickets.addActionListener(e -> {
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            functionPanel.add(bookingPanel);
            revalidate();
            repaint();
            resetButtonsColor();
            bookTickets.setBackground(new Color(75,110,175));
        });

        //booking section
        c.insets = new Insets(10,3,10,3);
        addComponent(ticketBookingL, bookingPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        c.insets = new Insets(3,3,3,3);
        addComponent(ticketIDL, bookingPanel, 0, 1, 1, GridBagConstraints.LINE_END);
        addComponent(ticketBookingIDTF, bookingPanel, 1, 1, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(firstName, bookingPanel, 0, 2, 1, GridBagConstraints.LINE_END);
        addComponent(firstNameTF, bookingPanel, 1, 2, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(lastName, bookingPanel, 0, 3, 1, GridBagConstraints.LINE_END);
        addComponent(lastNameTF, bookingPanel, 1, 3, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(gender, bookingPanel, 0, 4, 1, GridBagConstraints.LINE_END);
        addComponent(male, bookingPanel, 1, 4, 1, GridBagConstraints.LINE_START);
        addComponent(female, bookingPanel, 2, 4, 1, GridBagConstraints.LINE_START);
        addComponent(checkedInBaggage, bookingPanel, 0, 5, 1, GridBagConstraints.LINE_END);
        addComponent(baggageSelection, bookingPanel, 1, 5, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);
        addComponent(denyBaggageChechIn, bookingPanel, 1, 6, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_START);
        addComponent(bookTicketButton, bookingPanel, 1, 7, GridBagConstraints.REMAINDER, GridBagConstraints.LINE_END);

        male.addActionListener(e -> {
            female.setSelected(false);
            if(!male.isSelected()) male.setSelected(true);
        });
        female.addActionListener(e -> {
            male.setSelected(false);
            if(!female.isSelected()) female.setSelected(true);
        });

        denyBaggageChechIn.addActionListener(e -> {
            baggageSelection.setEnabled(!denyBaggageChechIn.isSelected());
        });

        bookTicketButton.addActionListener(e -> {
            int tixID;
            if(ticketBookingIDTF.getValue() != null){
                tixID = (int)ticketBookingIDTF.getValue();
            }else{
                tixID = -1;
            }
            String firstname = firstNameTF.getText();
            String lastname = lastNameTF.getText();
            String gender;
            String ticketCode = "";
            try {
                ticketCode = bookingsDataBase.generateTicketCode();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(male.isSelected()) gender = "male";
            else gender = "female";

            int baggageAmount;
            if(denyBaggageChechIn.isSelected()) baggageAmount = 0;
            else {
                String baggageAmountString = (String)baggageSelection.getSelectedItem();
                baggageAmount = Integer.parseInt(baggageAmountString.substring(0,2));
            }

            boolean bookingAlreadyExists;
            try {
                bookingAlreadyExists = bookingsDataBase.bookingAlreadyExists(tixID, loggedUser, firstname, lastname, gender);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if(firstname.isEmpty() || lastname.isEmpty() || tixID == -1) {
                JOptionPane.showMessageDialog(null, "Please fill all the needed information");
            }else if(bookingAlreadyExists){
                JOptionPane.showMessageDialog(null, "Booking already exists");
            }else {
                try {
                    if (ticketsDataBase.isTicketValid(tixID)) {
                        String message = "Your ticket will cost " + (ticketsDataBase.getTicketPrice(tixID) + baggageAmount) + "$\nDo you wish to continue?";
                        int buttonPressed = JOptionPane.showConfirmDialog(null, message, "Confirmation Pop-up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(buttonPressed == JOptionPane.YES_OPTION){
                            ticketsDataBase.bookTicket(tixID);
                            bookingsDataBase.addToBookingsDatabase(tixID, loggedUser, firstname, lastname, gender, baggageAmount, ticketCode);
                            JOptionPane.showMessageDialog(null, "Booking successful");
                            ticketBookingIDTF.setText("");
                            firstNameTF.setText("");
                            lastNameTF.setText("");
                            male.setSelected(false);
                            female.setSelected(false);
                            denyBaggageChechIn.setSelected(false);
                            baggageSelection.setEnabled(true);
                        }else{
                            JOptionPane.showMessageDialog(null, "Booking cancelled");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ticket ID");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        bookings.addActionListener(e -> {
            try {
                tablePane = createNewBookedTable();
                searchTicketsPanel.remove(calendarTablePane);
                functionPanel.removeAll();
                functionPanel.add(tablePane);
                revalidate();
                repaint();
                resetButtonsColor();
                bookings.setBackground(new Color(75,110,175));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        checkIn.addActionListener(e -> {
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            loadCheckInPanel();
            revalidate();
            repaint();
            resetButtonsColor();
            checkIn.setBackground(new Color(75,110,175));
        });

        selectExpDate.addActionListener(e -> expDateCal.setVisible(true));
        selectDateOfIssueDate.addActionListener(e -> dateOfIssueCal.setVisible(true));

        expDateCal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                passportExpDateInput.setText(expDateCal.getDate());
            }
        });

        dateOfIssueCal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dateOfIssueInput.setText(dateOfIssueCal.getDate());
            }
        });

        completeChechInBTN.addActionListener(e -> {

            if(Objects.equals(ticketCodeTF.getText(), "") || Objects.equals(nationalityTF.getText(), "") || Objects.equals(dateOfIssueInput.getText(), "") ||
                    Objects.equals(passportIDTF.getText(), "") || Objects.equals(passportExpDateInput.getText(), "") || Objects.equals(dateOfIssueInput.getText(), "") ||
                    Objects.equals(emailTF.getText(), "") || Objects.equals(phoneNumberTF.getText(), "")){
                        JOptionPane.showMessageDialog(null, "Please fill all the needed information");
                        return;
            }

            try {
                if(!bookingsDataBase.ticketCodeExists(ticketCodeTF.getText())){
                    JOptionPane.showMessageDialog(null, "You haven't booked such ticket/Invalid ticket code");
                    return;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            String expDate = passportExpDateInput.getText();
            if(expDate.length() !=  10 || expDate.charAt(2) != '-' || expDate.charAt(5) != '-'){
                JOptionPane.showMessageDialog(null, "Invalid expiration date format\nShould be DD-MM-YYYY");
                return;
            }

            String doiDate = dateOfIssueInput.getText();
            if(doiDate.length() !=  10 || doiDate.charAt(2) != '-' || doiDate.charAt(5) != '-'){
                JOptionPane.showMessageDialog(null, "Invalid date of issue date format\nShould be DD-MM-YYYY");
                return;
            }

            String pn = phoneNumberTF.getText();
            char [] charSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+'};

            boolean charIsInCharSet = false;
            for(int i = 0; i < pn.length(); i++){
                for (char value : charSet) {
                    if (pn.charAt(i) == value) charIsInCharSet = true;
                }
                if(!charIsInCharSet){
                    JOptionPane.showMessageDialog(null, "Invalid phone number");
                    return;
                }
            }

            try {
                checkInDataBase.addToDatabase(loggedUser, ticketCodeTF.getText(), nationalityTF.getText(), passportIDTF.getText(), passportExpDateInput.getText(), dateOfIssueInput.getText(),
                        emailTF.getText(), phoneNumberTF.getText());
                bookingsDataBase.checkInComplete(ticketCodeTF.getText());
                JOptionPane.showMessageDialog(null, "Check-in successful");
                ticketCodeTF.setText("");
                nationalityTF.setText("");
                passportIDTF.setText("");
                passportExpDateInput.setText("");
                dateOfIssueInput.setText("");
                emailTF.setText("");
                phoneNumberTF.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        checkInInfoPanel.setPreferredSize(new Dimension(functionPanel.getWidth()/2 - 10, functionPanel.getHeight() - 10));
        checkInInfoPanel.setBorder(border);

        showTicketCode.setFont(new Font("Arial", Font.PLAIN, 35));
        showTicketCode.setForeground(new Color(75,110,175));
        showNationality.setFont(new Font("Arial", Font.PLAIN, 25));
        showPassportID.setFont(new Font("Arial", Font.PLAIN, 25));
        showExpDate.setFont(new Font("Arial", Font.PLAIN, 25));
        showDateOfIssue.setFont(new Font("Arial", Font.PLAIN, 25));
        showEmail.setFont(new Font("Arial", Font.PLAIN, 25));
        showPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 25));

        addComponent(showTicketCode, checkInInfoPanel, 0, 0, 1, GridBagConstraints.CENTER);
        addComponent(showNationality, checkInInfoPanel, 0, 1, 1, GridBagConstraints.CENTER);
        addComponent(showPassportID, checkInInfoPanel, 0, 2, 1, GridBagConstraints.CENTER);
        addComponent(showExpDate, checkInInfoPanel, 0, 3, 1, GridBagConstraints.CENTER);
        addComponent(showDateOfIssue, checkInInfoPanel, 0, 4, 1, GridBagConstraints.CENTER);
        addComponent(showEmail, checkInInfoPanel, 0, 5, 1, GridBagConstraints.CENTER);
        addComponent(showPhoneNumber, checkInInfoPanel, 0, 6, 1, GridBagConstraints.CENTER);

        checkedIn.addActionListener(e -> {
            functionPanel.removeAll();
            searchTicketsPanel.remove(calendarTablePane);
            try {
                checkedInScrollPane = createNewCheckedInTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            addComponent(checkedInScrollPane, functionPanel, 0, 0, 1 , GridBagConstraints.CENTER);
            addComponent(checkInInfoPanel, functionPanel, 1, 0, 1, GridBagConstraints.CENTER);
            revalidate();
            repaint();
            resetButtonsColor();
            checkedIn.setBackground(new Color(75,110,175));
        });
    }
    GUI() throws IOException {
        super("AirLine App");
        setLayout(null);
        loginPanel.setBorder(border);
        registerPanel.setBorder(border);
        componentSetup();
        comboBoxList.add("10kg");
        comboBoxList.add("20kg");
        comboBoxList.add("26kg");
        comboBoxList.add("32kg");
        calendarTablePane = createCalendarTable(calendar);
        bookingPanelComponentSetup();

        //login section
        addComponent(loginLabel, loginPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(usernameLabelLogin, loginPanel, 0, 1, 1, GridBagConstraints.CENTER);
        addComponent(usernameLogin, loginPanel, 1, 1, 1, GridBagConstraints.CENTER);
        addComponent(passwordLabelLogin, loginPanel, 0, 2, 1, GridBagConstraints.CENTER);
        addComponent(passwordLogin, loginPanel, 1, 2, 1, GridBagConstraints.CENTER);
        addComponent(loginButton, loginPanel, 1, 3, 1, GridBagConstraints.LINE_END);

        //register section
        addComponent(registerLabel, registerPanel, 0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.CENTER);
        addComponent(usernameLabelRegister, registerPanel, 0, 1, 1, GridBagConstraints.CENTER);
        addComponent(usernameRegister, registerPanel, 1, 1, 1, GridBagConstraints.CENTER);
        addComponent(passwordLabelRegister, registerPanel, 0,2,1, GridBagConstraints.CENTER);
        addComponent(passwordRegister, registerPanel, 1,2,1, GridBagConstraints.CENTER);
        addComponent(passwordRegisterDupe, registerPanel, 1,3,1, GridBagConstraints.CENTER);
        addComponent(registerButton, registerPanel, 1, 4, 1, GridBagConstraints.LINE_END);

        //action listeners for the buttons
        loginButton.addActionListener(e -> {
            String inputUsername = usernameLogin.getText();
            String inputPassword = String.valueOf(passwordLogin.getPassword());
            usernameLogin.setText("");
            passwordLogin.setText("");
            try {
                accountDataBase.authenticate(inputUsername, inputPassword);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(accountDataBase.isLoggedIn()) {
                if(!firstLoginHasHappened){
                    loggedUser = inputUsername;
                    try {
                        addAccountPagePanel(inputUsername);
                    } catch (IOException | SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    firstLoginHasHappened = true;
                }else{
                    changeLoggedUser(inputUsername);
                    add(menu);
                    add(buttonsScrollPane);
                    add(functionPanel);
                    functionPanel.add(welcome);
                    add(userImage);
                }
                if(Objects.equals(loggedUser, "admin")){
                    addComponent(adminAddTickets, buttons, 0, 6, 1, GridBagConstraints.PAGE_START);
                }
                userImage.setText(inputUsername);
                searchTicketsPanel.remove(calendarTablePane);
                setTitle(getTitle() + ": " + inputUsername);
                remove(loginPanel);
                remove(registerPanel);
                revalidate();
                repaint();
            }
        });

        registerButton.addActionListener(e -> {
            String inputUsername = usernameRegister.getText();
            String inputPassword = String.valueOf(passwordRegister.getPassword());
            String inputPasswordDupe = String.valueOf(passwordRegisterDupe.getPassword());
            try {
                accountDataBase.register(inputUsername, inputPassword, inputPasswordDupe);
            } catch (SQLException ex){
                throw new RuntimeException(ex);
            }
            usernameRegister.setText("");
            passwordRegister.setText("");
            passwordRegisterDupe.setText("");
        });

        //JFrame config
        loginPanel.setBounds(200,75,500,600);
        registerPanel.setBounds(785,75,500,600);
        add(loginPanel);
        add(registerPanel);
        setVisible(true);
        setResizable(false);
        setSize(1500,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}