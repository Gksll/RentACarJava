package com.kodlamaio.rentACar.Core.webservices;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Entities.Concretes.IndividualCustomer;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisServiceAdapter {
	public boolean CheckIfRealPerson(IndividualCustomer individualCustomer) {
		KPSPublicSoapProxy client = new KPSPublicSoapProxy();
		boolean result = false;
		try {
			result = client.TCKimlikNoDogrula(Long.parseLong(individualCustomer.getIdentityNumber()), individualCustomer.getFirstName().toUpperCase(),
					individualCustomer.getLastName().toUpperCase(), individualCustomer.getBirthYear());
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
