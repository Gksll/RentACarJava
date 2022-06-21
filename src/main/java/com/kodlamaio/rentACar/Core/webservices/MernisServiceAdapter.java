package com.kodlamaio.rentACar.Core.webservices;
import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Entities.Concretes.Customer;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;
@Service
public class MernisServiceAdapter  {
	public boolean CheckIfRealPerson(Customer customer) {
		KPSPublicSoapProxy client = new KPSPublicSoapProxy();
		boolean result = false;
		try {
			result = client.TCKimlikNoDogrula(Long.parseLong(customer.getTcNo()), customer.getFirstName().toUpperCase(),
	customer.getLastName().toUpperCase(), customer.getBirthYear());
		} catch (NumberFormatException e) {
			System.out.println("format");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("remote");
			e.printStackTrace();
		}
		return result;

	}
}
