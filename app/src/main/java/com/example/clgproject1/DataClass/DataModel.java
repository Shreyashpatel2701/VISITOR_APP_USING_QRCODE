package com.example.clgproject1.DataClass;

public class DataModel {

    DataModel(){

          }




    String Vuid;
    String RescheduledTime;
    String Reason;
    String WhomToMeet_Name;
    String First_Name;
    String Mobile_NO;
    String Email_ID;
    String AreaCode;
    String Breathing_Difficulty;
    String Cough;
    String FeverStatus;
    String Last_Name;
    String MDate;
    String MTime;
    String ReasonResp_Problem;
    String State;
    boolean Visited_Status;
    String WhomToMeet;
    boolean Notification_Status;
    String Visited_Status_Update;

    public DataModel(String vuid, String rescheduledTime, String reason, String whomToMeet_Name, String first_Name, String mobile_NO, String email_ID, String areaCode, String breathing_Difficulty, String cough, String feverStatus, String last_Name, String MDate, String MTime, String reasonResp_Problem, String state, boolean visited_Status, String whomToMeet, boolean notification_Status, String visited_Status_Update) {
        Vuid = vuid;
        RescheduledTime = rescheduledTime;
        Reason = reason;
        WhomToMeet_Name = whomToMeet_Name;
        First_Name = first_Name;
        Mobile_NO = mobile_NO;
        Email_ID = email_ID;
        AreaCode = areaCode;
        Breathing_Difficulty = breathing_Difficulty;
        Cough = cough;
        FeverStatus = feverStatus;
        Last_Name = last_Name;
        this.MDate = MDate;
        this.MTime = MTime;
        ReasonResp_Problem = reasonResp_Problem;
        State = state;
        Visited_Status = visited_Status;
        WhomToMeet = whomToMeet;
        Notification_Status = notification_Status;
        Visited_Status_Update = visited_Status_Update;
    }

    public String getVuid() {
        return Vuid;
    }

    public void setVuid(String vuid) {
        Vuid = vuid;
    }

    public String getRescheduledTime() {
        return RescheduledTime;
    }

    public void setRescheduledTime(String rescheduledTime) {
        RescheduledTime = rescheduledTime;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getWhomToMeet_Name() {
        return WhomToMeet_Name;
    }

    public void setWhomToMeet_Name(String whomToMeet_Name) {
        WhomToMeet_Name = whomToMeet_Name;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getMobile_NO() {
        return Mobile_NO;
    }

    public void setMobile_NO(String mobile_NO) {
        Mobile_NO = mobile_NO;
    }

    public String getEmail_ID() {
        return Email_ID;
    }

    public void setEmail_ID(String email_ID) {
        Email_ID = email_ID;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getBreathing_Difficulty() {
        return Breathing_Difficulty;
    }

    public void setBreathing_Difficulty(String breathing_Difficulty) {
        Breathing_Difficulty = breathing_Difficulty;
    }

    public String getCough() {
        return Cough;
    }

    public void setCough(String cough) {
        Cough = cough;
    }

    public String getFeverStatus() {
        return FeverStatus;
    }

    public void setFeverStatus(String feverStatus) {
        FeverStatus = feverStatus;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getMDate() {
        return MDate;
    }

    public void setMDate(String MDate) {
        this.MDate = MDate;
    }

    public String getMTime() {
        return MTime;
    }

    public void setMTime(String MTime) {
        this.MTime = MTime;
    }

    public String getReasonResp_Problem() {
        return ReasonResp_Problem;
    }

    public void setReasonResp_Problem(String reasonResp_Problem) {
        ReasonResp_Problem = reasonResp_Problem;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public boolean isVisited_Status() {
        return Visited_Status;
    }

    public void setVisited_Status(boolean visited_Status) {
        Visited_Status = visited_Status;
    }

    public String getWhomToMeet() {
        return WhomToMeet;
    }

    public void setWhomToMeet(String whomToMeet) {
        WhomToMeet = whomToMeet;
    }

    public boolean isNotification_Status() {
        return Notification_Status;
    }

    public void setNotification_Status(boolean notification_Status) {
        Notification_Status = notification_Status;
    }

    public String getVisited_Status_Update() {
        return Visited_Status_Update;
    }

    public void setVisited_Status_Update(String visited_Status_Update) {
        Visited_Status_Update = visited_Status_Update;
    }
}
