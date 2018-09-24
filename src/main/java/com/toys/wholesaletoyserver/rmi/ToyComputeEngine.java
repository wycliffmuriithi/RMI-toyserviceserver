package com.toys.wholesaletoyserver.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import com.toys.wholesaletoyservice.compute.Compute;
import com.toys.wholesaletoyservice.compute.Task;



public class ToyComputeEngine extends UnicastRemoteObject implements Compute {
	
	private static final long serialVersionUID = 5885886202424414094L;
	
	public ToyComputeEngine() throws RemoteException {
		super();
	}

	public <T> T executeTask(Task<T> t) {
		return t.execute();
	}
	
	public static void main(String[] args) {
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Compute", new ToyComputeEngine());
		} catch (ExportException ex) {
			try {
				registry = LocateRegistry.getRegistry(1099);
				registry.rebind("Compute", new ToyComputeEngine());
			} catch (RemoteException e) {
				System.err.println("Something wrong happended on the remote end");
				e.printStackTrace();
				System.exit(-1); // can't just return, rmi threads may not exit
			}
		} catch (RemoteException e) {
			System.err.println("Something wrong happended on the remote end");
			e.printStackTrace();
			System.exit(-1); // can't just return, rmi threads may not exit
		}
		System.out.println("ToyComputeEngine is ready");
	}
}
