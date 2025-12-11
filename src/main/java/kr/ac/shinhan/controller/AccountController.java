package kr.ac.shinhan.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.ac.shinhan.Account;
import kr.ac.shinhan.AccountDAO;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountDAO dao;

	// 가계부 목록 (월별)
	@GetMapping
	public String listAccount(@RequestParam(value = "month", required = false) String month, Model model) {
		try {
			// 월이 지정되지 않으면 현재 월 사용
			if (month == null || month.isEmpty()) {
				month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
			}
			
			List<Account> list = dao.getByMonth(month);
			int totalIncome = dao.getTotalIncomeByMonth(month);
			int totalExpense = dao.getTotalExpenseByMonth(month);
			int balance = totalIncome - totalExpense;
			List<String> availableMonths = dao.getAvailableMonths();
			
			model.addAttribute("accountList", list);
			model.addAttribute("currentMonth", month);
			model.addAttribute("totalIncome", totalIncome);
			model.addAttribute("totalExpense", totalExpense);
			model.addAttribute("balance", balance);
			model.addAttribute("availableMonths", availableMonths);
		} catch (Exception e) {
			model.addAttribute("error", "가계부 목록을 불러오는 데 실패했습니다: " + e.getMessage());
		}
		return "account/accountList";
	}
	
	// 통계 페이지
	@GetMapping("/stats")
	public String showStats(@RequestParam(value = "month", required = false) String month, Model model) {
		try {
			if (month == null || month.isEmpty()) {
				month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
			}
			
			int totalIncome = dao.getTotalIncomeByMonth(month);
			int totalExpense = dao.getTotalExpenseByMonth(month);
			List<Map<String, Object>> expenseByCategory = dao.getExpenseByCategory(month);
			List<Map<String, Object>> incomeByCategory = dao.getIncomeByCategory(month);
			List<Map<String, Object>> monthlyStats = dao.getMonthlyStats();
			List<String> availableMonths = dao.getAvailableMonths();
			
			model.addAttribute("currentMonth", month);
			model.addAttribute("totalIncome", totalIncome);
			model.addAttribute("totalExpense", totalExpense);
			model.addAttribute("balance", totalIncome - totalExpense);
			model.addAttribute("expenseByCategory", expenseByCategory);
			model.addAttribute("incomeByCategory", incomeByCategory);
			model.addAttribute("monthlyStats", monthlyStats);
			model.addAttribute("availableMonths", availableMonths);
		} catch (Exception e) {
			model.addAttribute("error", "통계를 불러오는 데 실패했습니다: " + e.getMessage());
		}
		return "account/accountStats";
	}
	
	// 등록 폼 (GET)
	@GetMapping("/write")
	public String showWriteForm(Model model) {
		// 오늘 날짜를 기본값으로 설정
		model.addAttribute("today", LocalDate.now().toString());
		return "account/accountForm";
	}
	
	// 등록 처리 (POST)
	@PostMapping("/write")
	public String addAccount(@ModelAttribute Account account) {
		try {
			dao.addAccount(account);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("등록 실패: " + e.getMessage(), StandardCharsets.UTF_8);
			return "redirect:/account?error=" + errorMsg;
		}
		return "redirect:/account";
	}
	
	// 수정 폼 (GET)
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") int id, Model model) {
		try {
			Account account = dao.getAccount(id);
			if (account == null) {
				String errorMsg = URLEncoder.encode("해당 내역을 찾을 수 없습니다", StandardCharsets.UTF_8);
				return "redirect:/account?error=" + errorMsg;
			}
			model.addAttribute("account", account);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("내역을 찾을 수 없습니다", StandardCharsets.UTF_8);
			return "redirect:/account?error=" + errorMsg;
		}
		return "account/accountEdit";
	}
	
	// 수정 처리 (POST)
	@PostMapping("/edit")
	public String updateAccount(@ModelAttribute Account account) {
		try {
			dao.updateAccount(account);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("수정 실패", StandardCharsets.UTF_8);
			return "redirect:/account?error=" + errorMsg;
		}
		return "redirect:/account";
	}
	
	// 삭제
	@GetMapping("/delete/{id}")
	public String deleteAccount(@PathVariable("id") int id) {
		try {
			dao.deleteAccount(id);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("삭제 실패", StandardCharsets.UTF_8);
			return "redirect:/account?error=" + errorMsg;
		}
		return "redirect:/account";
	}
}






