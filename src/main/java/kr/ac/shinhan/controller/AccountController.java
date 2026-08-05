package kr.ac.shinhan.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.ac.shinhan.Account;
import kr.ac.shinhan.dto.CategorySum;
import kr.ac.shinhan.dto.MonthlySummary;
import kr.ac.shinhan.dto.MonthlyTrend;
import kr.ac.shinhan.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// 가계부 목록 (월별)
	@GetMapping
	public String listAccount(@RequestParam(value = "month", required = false) String month, Model model) {
		String resolvedMonth = accountService.resolveMonth(month);
		try {
			List<Account> list = accountService.getByMonth(resolvedMonth);
			MonthlySummary summary = accountService.getMonthlySummary(resolvedMonth);
			List<String> availableMonths = accountService.getAvailableMonths();

			model.addAttribute("accountList", list);
			model.addAttribute("currentMonth", resolvedMonth);
			model.addAttribute("summary", summary);
			model.addAttribute("availableMonths", availableMonths);
		} catch (Exception e) {
			// 예외 메시지를 그대로 노출하면 테이블명/SQL 구조가 드러날 수 있어 고정 문구만 표시
			model.addAttribute("error", "가계부 목록을 불러오는 데 실패했습니다.");
		}
		return "account/accountList";
	}

	// 통계 페이지
	@GetMapping("/stats")
	public String showStats(@RequestParam(value = "month", required = false) String month, Model model) {
		String resolvedMonth = accountService.resolveMonth(month);
		try {
			MonthlySummary summary = accountService.getMonthlySummary(resolvedMonth);
			List<CategorySum> expenseByCategory = accountService.getExpenseByCategory(resolvedMonth);
			List<CategorySum> incomeByCategory = accountService.getIncomeByCategory(resolvedMonth);
			List<MonthlyTrend> monthlyStats = accountService.getMonthlyStats();
			List<String> availableMonths = accountService.getAvailableMonths();

			model.addAttribute("currentMonth", resolvedMonth);
			model.addAttribute("summary", summary);
			model.addAttribute("expenseByCategory", expenseByCategory);
			model.addAttribute("incomeByCategory", incomeByCategory);
			model.addAttribute("monthlyStats", monthlyStats);
			model.addAttribute("availableMonths", availableMonths);
		} catch (Exception e) {
			model.addAttribute("error", "통계를 불러오는 데 실패했습니다.");
		}
		return "account/accountStats";
	}

	// 등록 폼 (GET)
	@GetMapping("/write")
	public String showWriteForm(Model model) {
		model.addAttribute("today", LocalDate.now().toString());
		return "account/accountForm";
	}

	// 등록 처리 (POST)
	@PostMapping("/write")
	public String addAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes) {
		try {
			accountService.addAccount(account);
			redirectAttributes.addFlashAttribute("message", "등록되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "등록 실패");
		}
		return "redirect:/account";
	}

	// 수정 폼 (GET)
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		Account account = accountService.getAccount(id);
		if (account == null) {
			redirectAttributes.addFlashAttribute("error", "해당 내역을 찾을 수 없습니다.");
			return "redirect:/account";
		}
		model.addAttribute("account", account);
		return "account/accountEdit";
	}

	// 수정 처리 (POST)
	@PostMapping("/edit")
	public String updateAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes) {
		try {
			accountService.modifyAccount(account);
			redirectAttributes.addFlashAttribute("message", "수정되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "수정 실패");
		}
		return "redirect:/account";
	}

	// 삭제
	// GET 은 서버 상태를 바꾸지 않는다는 HTTP 규약이 있다. GET 으로 삭제를 구현하면
	// 크롤러, 링크 미리보기, 브라우저 프리페치가 URL 에 접근하는 것만으로 데이터가 지워질 수 있어 POST 로 변경.
	@PostMapping("/delete/{id}")
	public String deleteAccount(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		try {
			accountService.removeAccount(id);
			redirectAttributes.addFlashAttribute("message", "삭제되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "삭제 실패");
		}
		return "redirect:/account";
	}
}
