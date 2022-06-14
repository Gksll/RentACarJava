package com.kodlamaio.rentACar.Core.webservices;
import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Entities.Concretes.User;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;
@Service
public class MernisServiceAdapter  {
	public boolean CheckIfRealPerson(User user) {
		KPSPublicSoapProxy client = new KPSPublicSoapProxy();
		boolean result = false;
		try {
			result = client.TCKimlikNoDogrula(Long.parseLong(user.getTcNo()), user.getFirstName().toUpperCase(),
	user.getLastName().toUpperCase(), user.getBirthYear());
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
