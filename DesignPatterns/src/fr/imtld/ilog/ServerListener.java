package fr.imtld.ilog;

public interface ServerListener {
	void serverStarted(int iPort);
	void serverStopped(int iCause, int iPort);
}
