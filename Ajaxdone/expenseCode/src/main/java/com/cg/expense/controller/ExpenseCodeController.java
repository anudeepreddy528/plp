package com.cg.expense.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.cg.expense.entity.ExpenseCode;
import com.cg.expense.exception.ExpenseNotFoundException;
import com.cg.expense.service.IExpenseCodeService;

@RefreshScope
@RestController
public class ExpenseCodeController {

	public int k;
	public int getK() {
		return k;
	}
public void setK(int i) {
		
		this.k=i;	
	}

	@Autowired
	IExpenseCodeService expenseCodeService;
	
	
	@PostMapping("/create")
	public void createExpenseCode(@Valid @RequestBody ExpenseCode expenseCode, BindingResult result ) {
		if(!result.hasErrors())
		{
			k=1;
			ExpenseCode exp=expenseCodeService.save(expenseCode);
			if(k==0) {
				throw new ExpenseNotFoundException("The expense type already exists "+exp.getExpenseCode());
			}
		}
		else
		{
			throw new ExpenseNotFoundException(result.getFieldError().getDefaultMessage());
		}
	
	}
	@GetMapping("/expenses")
	public List<ExpenseCode> getAllExpenses() {
		return expenseCodeService.getAllExpenses();
	}
	
	@GetMapping("/{expenseCode}")
	public ExpenseCode getExpense(@Valid @PathVariable Integer expenseCode) {
	
		
		ExpenseCode exp=expenseCodeService.getExpense(expenseCode);
		if(exp==null) {
			throw new ExpenseNotFoundException("No expense type exists for the expense code ");
		}
		else {
			return exp;
		}
	}
	
	
	
	
	
	@PutMapping("/update")
	public void updateExpense(@RequestBody ExpenseCode expenseCode) {
		ExpenseCode exp=expenseCodeService.update(expenseCode);
		
		if(exp==null) {
			throw new ExpenseNotFoundException("No expense type exists for the expense code ");
		}
	}
	
	@DeleteMapping("/delete/{expenseCode}")
	public void deleteCustomer(@PathVariable Integer expenseCode) {
		Integer exp=expenseCodeService.delete(expenseCode);
		if(exp==0)
		{
			throw new ExpenseNotFoundException("No expense type exists for the expense code ");
		}
		
	}
	
	
}