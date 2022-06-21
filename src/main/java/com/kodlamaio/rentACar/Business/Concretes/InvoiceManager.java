package com.kodlamaio.rentACar.Business.Concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.Business.Abstracts.InvoiceService;
import com.kodlamaio.rentACar.Business.Requests.Invoice.CreateInvoiceRequest;
import com.kodlamaio.rentACar.Business.Requests.Invoice.StateUpdateInvoiceRequest;
import com.kodlamaio.rentACar.Core.Utilities.Results.ErrorResult;
import com.kodlamaio.rentACar.Core.Utilities.Results.Result;
import com.kodlamaio.rentACar.Core.Utilities.Results.SuccessResult;
import com.kodlamaio.rentACar.Core.Utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.DataAccess.Abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.DataAccess.Abstracts.RentalDetailRepository;
import com.kodlamaio.rentACar.Entities.Concretes.Invoice;
import com.kodlamaio.rentACar.Entities.Concretes.RentalDetail;

@Service
public class InvoiceManager implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private RentalDetailRepository rentalDetailRepository;

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		RentalDetail rentalDetailToAddTotalPrice = rentalDetailRepository
				.findById(createInvoiceRequest.getRentalDetailId()).get();
		invoice.setTotalPrice(rentalDetailToAddTotalPrice.getRental().getTotalPrice());
		if (!checkIfInvoicesNumber(invoice.getInvoiceNumber())) {
			invoiceRepository.save(invoice);
			return new SuccessResult("Fatura eklendi");
		}

		return new ErrorResult("Fatura zaten var, numara kontrolü yap!");
	}

	@Override
	public boolean checkIfInvoicesNumber(int invoiceNumber) {
		boolean state = false;
		List<Invoice> invoices = invoiceRepository.findAll();
		for (Invoice item : invoices) {
			if (item.getInvoiceNumber() == invoiceNumber) {
				state = true;
			}
		}
		return state;
	}

	@Override
	public Result cancelInvoice(StateUpdateInvoiceRequest stateUpdateInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(stateUpdateInvoiceRequest, Invoice.class);
		if (checkIfInvoicesNumber(invoice.getInvoiceNumber())) {
			invoice.setState(false);
			invoiceRepository.save(invoice);
			return new SuccessResult("Fatura pasife geçilmiştir.");
		}
		return new ErrorResult("Fatura yok neyi silem abem.");

	}

}
