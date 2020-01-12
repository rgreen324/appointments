/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author richard
 */
public class Appointment {
    private static int currentUserId = UserDB.getCurrentUserId();
    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    private String customerName;
    private LocalDateTime localStart;
    private LocalDateTime localEnd;
    private String reportDate;
    private String reportStart;
    private String reportEnd;
    private String reportTime;

    
    public static ObservableList<String> types = FXCollections.observableArrayList();
    public static ObservableList<String> startTimes = FXCollections.observableArrayList();
    public static ObservableList<String> endTimes = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> reportAppointments = FXCollections.observableArrayList();

    
    // Simple constructor
    public Appointment(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    
    // Full constructor
    public Appointment(int customerId, int userId, String title, String description, String location, String contact, String type, String url, LocalDateTime start, LocalDateTime end) {
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }

    // appointmentId getter/setter
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    // customerId getter/setter
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // userId getter/setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // title getter/setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // description getter/setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // location getter/setter
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // contact getter/setter
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // type getter/setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // url getter/setter
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // start getter/setter
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    // end getter/setter
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    // customerName getter/setter
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // localStart getter/setter
    public LocalDateTime getLocalStart() {
        return localStart;
    }

    public void setLocalStart(LocalDateTime localStart) {
        this.localStart = localStart;
    }

    // localEnd getter/setter
    public LocalDateTime getLocalEnd() {
        return localEnd;
    }

    public void setLocalEnd(LocalDateTime localEnd) {
        this.localEnd = localEnd;
    }

    // reportDate getter/setter
    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    // reportStart getter/setter
    public String getReportStart() {
        return reportStart;
    }

    public void setReportStart(String reportStart) {
        this.reportStart = reportStart;
    }

    // reportEnd getter/setter
    public String getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(String reportEnd) {
        this.reportEnd = reportEnd;
    }

    // reportTime getter/setter
    public String getReportTime() {
        return reportTime;
    }
    
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
    
    
    // Generate month appointments
    public static void generateMonthAppointments() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        for(Appointment a: appointments){
            if (a.localStart.getMonthValue() == month && a.userId == UserDB.getCurrentUserId()) {
                monthAppointments.add(a);
            }
        }
    }
    
    // Generate week appointments
    public static void generateWeekAppointments() {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
        int week = LocalDate.now().get(woy);

        for(Appointment a: appointments){
            if (a.localStart.get(woy) == week && a.userId == UserDB.getCurrentUserId()) {
                weekAppointments.add(a);
            }
        }
    }
    

    // Generate appointment related lists
    public static void generateLists() {
        try {        
            // Populate appointments list
            appointments.clear();
            AppointmentDB.generateAppointments();
            
            // Populate monthAppointments list
            monthAppointments.clear();
            generateMonthAppointments();
            
            // Populate weekAppointments list
            weekAppointments.clear();
            generateWeekAppointments();
            
            // Refresh customer objects from database
            Customer.clearAllCustomers();
            CustomerDB.generateCustomers();

            // Populate types list
            types.clear();
            types.add("Intro");
            types.add("Consultation");
            types.add("Emergency");

            // Populate times list
            startTimes.clear();
            startTimes.add("9:00 AM");
            startTimes.add("10:00 AM");
            startTimes.add("11:00 AM");
            startTimes.add("12:00 PM");
            startTimes.add("1:00 PM");
            startTimes.add("2:00 PM");
            startTimes.add("3:00 PM");
            startTimes.add("4:00 PM");
            startTimes.add("5:00 PM");

            // Populate times list
            endTimes.clear();
            endTimes.add("10:00 AM");
            endTimes.add("11:00 AM");
            endTimes.add("12:00 PM");
            endTimes.add("1:00 PM");
            endTimes.add("2:00 PM");
            endTimes.add("3:00 PM");
            endTimes.add("4:00 PM");
            endTimes.add("6:00 PM");
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    // Return types list
    public static ObservableList<String> getTypes() {
        return types;
    }
    
    // Return startTimes list
    public static ObservableList<String> getStartTimes() {
        return startTimes;
    }
    
    // Return endTimes list
    public static ObservableList<String> getEndTimes() {
        return endTimes;
    }
    
    // Add appointment to appointments list
    public static void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    
    // Remove appointment from appointments list
    public static void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
    
    // Get appointments list
    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }
    
    // Format localdatetime to UTC
    public static LocalDateTime ldtFormatter(LocalDate date, String time){
        LocalDateTime ldt;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime lt = LocalTime.from(dtf.parse(time));
        ldt = LocalDateTime.of(date, lt);
        
        ZonedDateTime zdt;
        ZoneId zid = ZoneId.systemDefault();
        zdt = ldt.atZone(zid);
        
        ZonedDateTime utczdt;
        utczdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        
        LocalDateTime dbldt = utczdt.toLocalDateTime();
        
        return dbldt;
    }
    
    // Format localdate time from UTC to local
    public static LocalDateTime dbToLocalDT(LocalDateTime ldt) {
        ZoneId zid1 = ZoneId.of("UTC");
        ZonedDateTime zdtUTC = ldt.atZone(zid1);

        ZoneId zid2 = ZoneId.systemDefault();
        ZonedDateTime zdtLocal = zdtUTC.withZoneSameInstant(ZoneId.of(zid2.toString()));
        
        LocalDateTime localDT = zdtLocal.toLocalDateTime();

        return localDT;
    }
    
    // Format report times
    public static String reportTime(LocalDateTime ldt) {
        String reportTime;
        String format = "h:mm a";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        reportTime = ldt.format(dtf);
        
        return reportTime;
    }
    
    // Format report dates
    public static String reportDate(LocalDateTime ldt) {
        String reportDate;
        String format = "MMM dd, yyyy";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        reportDate = ldt.format(dtf);
        
        return reportDate;
    }
    
    // Get appointment from id
    public static Appointment getAppointment(int appointmentId) {
        Appointment appointment = null;
        for(Appointment a: appointments){
            if (a.appointmentId == appointmentId) {
                appointment = a;
            }
        }
        return appointment;
    }
    
    // Get month appointments
    public static ObservableList<Appointment> getMonthAppointments() {
        return monthAppointments;
    }
    
    // Get week appointments
    public static ObservableList<Appointment> getWeekAppointments() {
        return weekAppointments;
    }
    
    // Get report appointments
    public static ObservableList<Appointment> getReportAppointments(int userId) {
        reportAppointments.clear();
        generateReportAppointments(userId);
        return reportAppointments;
    }
    
    // Validate time sequence
    public static boolean isValidSequence(LocalDateTime start, LocalDateTime end) {
        boolean valid = false;
        boolean sequence = false;
        
        if(start.isBefore(end)) {
            sequence = true;
        }
        
        if(sequence) {
            valid = true;
        }
        
        return valid;
    }
    
    // Validate business hours
    public static boolean isValidBusinessHours(LocalDate date, LocalDateTime start, LocalDateTime end) {
        boolean valid = false;
        boolean weekday = false;
        
        if(date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7) {
            weekday = true;
        }
        
        if(weekday) {
            valid = true;
        }
        
        return valid;
    }
    
    // Validate overlapping appointment
    public static boolean isValidTimeSlot(int apptId, LocalDateTime start, LocalDateTime end) {
        boolean valid = false;
        boolean overlap = false;
        
        for(Appointment a: appointments){
            if(a.getAppointmentId() != apptId) {
                if(start.isAfter(a.getStart()) && start.isBefore(a.getEnd())) {
                    overlap = true;
                }
                if(end.isAfter(a.getStart()) && end.isBefore(a.getEnd())) {
                    overlap = true;
                }
                if(end.isEqual(a.getEnd()) || start.isEqual(a.getStart())) {
                    overlap = true;
                }
            }
        }    
        if(!overlap) {
            valid = true;
        }  
        return valid;
    }
    
    // Check for appointment within 15 minutes of user login
    public static Appointment loginAlert(int userId) throws Exception {
        Appointment appointment = null;
        appointments.clear();
        AppointmentDB.generateAppointments();
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt2 = ldt.plusMinutes(15);

        for(Appointment a: appointments){
            if (a.localStart.isAfter(ldt) && a.localStart.isBefore(ldt2) && a.userId == userId) {
                appointment = a;
            }
        }
        return appointment;
    }
    
    // Generate appointments from userId
    public static void generateReportAppointments(int userId) {
        if(userId == 0) {
            for(Appointment a: appointments){
                reportAppointments.add(a);
            }
        }
        else {
            for(Appointment a: appointments){
                if (a.userId == userId) {
                    reportAppointments.add(a);
                }
            }
        }
    }

}
