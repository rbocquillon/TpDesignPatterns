package fr.imtld.ilog;

public class FactoryMulti extends Factory {

	@Override
	public DayTimeServer createDayTimeServer(ServerListener srv) {
		if(dts==null) {
			dts = new DayTimeServerMulti(srv);
		}
		return dts;
	}
	
}
