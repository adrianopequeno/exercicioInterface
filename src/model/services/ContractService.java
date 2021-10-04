package model.services;

import java.util.Calendar;
import java.util.Date;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	// declara com o tipo da interface
	private OnlinePaymentService ops;
	
	public ContractService(OnlinePaymentService ops) {
		this.ops = ops;
	}

	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		// basicQuota = 200
		for (int i = 1; i <= months; i++) {
			double upadateQuota = basicQuota + ops.interest(basicQuota, i);
			// basicQuota = 202
			double fullQuota = upadateQuota + ops.paymentFee(upadateQuota);
			// basicQuota = 206.04
			Date dueDate = addMonth(contract.getDate(), i);
			contract.getInstallments().add(new Installment(dueDate, fullQuota));
		}
	}
	
	private Date addMonth(Date date, int N) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, N);
		return calendar.getTime();
	}
}
