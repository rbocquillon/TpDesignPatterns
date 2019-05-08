package fr.imtld.ilog;

public class Factory {
    private static Factory INSTANCE = new Factory();
    protected DayTimeUI dtui;
    protected DayTimeServer dts;
    
    public static Factory getFactory() {
        return INSTANCE;
    }
    public static void setFactory( Factory fact ) {
        INSTANCE = fact;
    }
   
    public DayTimeUI createDayTimeUI () {
    	if(dtui==null) {
    		dtui = new DayTimeUI();
    	}
    	return dtui;
    }
    
    public DayTimeServer createDayTimeServer(ServerListener srv) {
    	if(dts==null) {
    		dts = new DayTimeServer(srv);
    	}
    	return dts;
    }
    
}