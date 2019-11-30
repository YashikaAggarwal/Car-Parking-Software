package VehicleEntryLog;

import java.sql.Date;
import java.sql.Time;

public class VehicleBean {
    String vehicleno;
    String vehicletype;
    String mob;
    String floor;
    Date entrydate;
    Time entrytime;
    Date exitdate;
    Time exittime;
    
    VehicleBean(){};
    
    public VehicleBean(String vehicleno, String vehicletype, String mob, String floor, Date entrydate, Time entrytime) {
		super();
		this.vehicleno = vehicleno;
		this.vehicletype = vehicletype;
		this.mob = mob;
		this.floor = floor;
		this.entrydate = entrydate;
		this.entrytime = entrytime;
		
	}
    
    public VehicleBean(String vehicleno, String vehicletype, String mob, String floor, Date entrydate, Time entrytime,Date exitdate ,Time exittime) {
		super();
		this.vehicleno = vehicleno;
		this.vehicletype = vehicletype;
		this.mob = mob;
		this.floor = floor;
		this.entrydate = entrydate;
		this.entrytime = entrytime;
		this.exitdate = exitdate;
		this.exittime = exittime;
	}

	public String getVehicleno() {
		return vehicleno;
	}

	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}

	public String getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Date getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}

	public Time getEntrytime() {
		return entrytime;
	}

	public void setEntrytime(Time entrytime) {
		this.entrytime = entrytime;
	}

	public Date getExitdate() {
		return exitdate;
	}

	public void setExitdate(Date exitdate) {
		this.exitdate = exitdate;
	}

	public Time getExittime() {
		return exittime;
	}

	public void setExittime(Time exittime) {
		this.exittime = exittime;
	}

	
    
}
