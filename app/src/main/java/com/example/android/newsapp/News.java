package com.example.android.newsapp;


public class News {

    private String mheading;
    private String mDate;
    private String serialno;
    private String murl;

    public News(String heading, String Date, String number, String url) {
        mheading = heading;
        mDate = Date;
        serialno = number;
        murl = url;
    }

    public String getMheading() {
        return mheading;
    }

    public String getmDate() {
        return mDate;
    }

//    public String SetDate(String time) {
//        String inputPattern = "yyyy-MM-dd HH:mm:ss";
//        String outputPattern = "dd-MMM-yyyy h:mm a";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//
//        Date date = null;
//        String str = null;
//
//        try {
//            date = inputFormat.parse(time);
//            str = outputFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
    public String getSerialno() {
        return serialno;
    }
    public String geturl(){
        return murl;
    }
}
