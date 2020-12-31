package com.kbs.pocis.service;

import android.util.Log;

import com.kbs.pocis.api.APIClient;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.model.Model_Monitoring;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.onlineboking.Filters;
import com.kbs.pocis.service.onlinebooking.CallingData;

import java.util.Calendar;

public class UserData {
    //region Singleton
    public static UserData i;
    public static boolean isExists(){
        return (i!=null);
    }
    //endregion
    static final int detik = 10;
    public String username;
    public String password;
    public String m_customer_id;
    private String token;
    private long time;
    private UserService service;
    public Filters filter;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
        RefreshTime();
        i = this;
    }


    public void updateFilter(String project, String no, String vessel) {
        if (!project.isEmpty() && !no.isEmpty() && !vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(Model_Project data) {
                    return  data.project_no!=null && data.project_no.contains(project) && data.booking_no!=null && data.vessel_name != null && data.booking_no.contains(no) && data.vessel_name.contains(vessel);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return  data.voyage_no!=null && data.voyage_no.contains(project) && data.schedule_code!=null && data.vessel_name != null && data.schedule_code.contains(no) && data.vessel_name.contains(vessel);
                }
            };
        }else
        if (!project.isEmpty() && !no.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(Model_Project data) {
                    return  data.project_no!=null && data.project_no.contains(project) && data.booking_no!=null && data.booking_no.contains(no);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return  data.voyage_no!=null && data.voyage_no.contains(project) && data.schedule_code!=null && data.schedule_code.contains(no);
                }
            };
        }else
        if (!project.isEmpty() && !vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(Model_Project data) {
                    return  data.project_no!=null && data.project_no.contains(project) && data.vessel_name != null && data.vessel_name.contains(vessel);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return  data.voyage_no!=null && data.voyage_no.contains(project) && data.vessel_name != null && data.vessel_name.contains(vessel);
                }
            };
        }else
        if (!no.isEmpty() && !vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.no_booking!=null && data.vessel_name != null && data.no_booking.contains(no) && data.vessel_name.contains(vessel);
                }
                @Override
                public boolean checkFilter(Model_Project data) {
                    return data.booking_no!=null && data.vessel_name != null && data.booking_no.contains(no) && data.vessel_name.contains(vessel);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return data.schedule_code!=null && data.vessel_name != null && data.schedule_code.contains(no) && data.vessel_name.contains(vessel);
                }
            };
        } else if (!no.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.no_booking!=null && data.no_booking.contains(no);
                }
                @Override
                public boolean checkFilter(Model_Project data) {
                    return data.booking_no!=null && data.booking_no.contains(no);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return data.schedule_code!=null && data.schedule_code.contains(no);
                }
            };
        } else if (!vessel.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(CallingData.Booking data) {
                    return data.vessel_name != null && data.vessel_name.contains(vessel);
                }
                @Override
                public boolean checkFilter(Model_Project data) {
                    return data.vessel_name != null && data.vessel_name.contains(vessel);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return data.vessel_name != null && data.vessel_name.contains(vessel);
                }
            };
        } else
        if (!project.isEmpty()) {
            filter = new Filters(no,vessel) {
                @Override
                public boolean checkFilter(Model_Project data) {
                    return  data.project_no!=null && data.project_no.contains(project);
                }

                @Override
                public boolean checkFilter(Model_Monitoring data) {
                    return  data.voyage_no!=null && data.voyage_no.contains(project);
                }
            };
        }else{
            filter = null;
        }
    }

    public UserData(String username, String password, long time) {
        this.username = username;
        this.password = password;
        this.time = time;
        filter = null;
    }

    public UserService getService(){
        if (service == null){
            service = APIClient.getClient().create(UserService.class);
        }
        return service;
    }

    public void RefreshTime(){
        time = Calendar.getInstance().getTime().getTime();
    }

    public String getToken() {
        RefreshTime();
        return token;
    }

    public String getCustID(){
        return m_customer_id;
    }

    public long getTime(){
        return time;
    }

    public boolean CheckTime(){
        Log.i("time","check Null time user " + (time)+ " "+Calendar.getInstance().getTime().getTime());
        return (Calendar.getInstance().getTime().getTime()-time)/(1000) <= detik;
    }

    public int CheckCount(){
        return (int)(Calendar.getInstance().getTime().getTime()-time)/1000;
    }

    public void setToken(String token) {
        Log.i("token","Token Updated to : "+token);
        this.token = token;
    }

    public void setCustId(String id){
        Log.i("token","m_customer Updated to : "+id);
        this.m_customer_id = id;
    }
}
