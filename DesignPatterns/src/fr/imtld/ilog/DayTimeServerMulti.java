package fr.imtld.ilog;

public class DayTimeServerMulti extends DayTimeServer {
	public DayTimeServerMulti(ServerListener lsn) {
		super(lsn);
	}
	@Override
	public void run() {
		System.out.println("DayTimeServerMulti starting");
		super.run();
	}
}
