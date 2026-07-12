package com.akh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akh.service.AuthService;
import com.akh.service.MoneyService;
import com.akh.util.MoneyVo;
import com.akh.util.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

	@Autowired
	private AuthService authService;

	@Autowired
	private MoneyService moneyService;

	@GetMapping("/")
	public String homePage() {
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String loginPage(HttpSession session) {

		// If already logged in, go directly to dashboard
		if (session.getAttribute("loggedInUser") != null) {
			return "redirect:/dashboard";
		}

		return "login";
	}

	@GetMapping("/signup")
	public String signupPage(HttpSession session) {

		// If already logged in, go directly to dashboard
		if (session.getAttribute("loggedInUser") != null) {
			return "redirect:/dashboard";
		}

		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignup(String email, String password, Model model, RedirectAttributes redirectAttributes) {

		String result = authService.signup(email, password);

		if (result.equals("Signup successful")) {

			redirectAttributes.addFlashAttribute("message", "Account created successfully. Please sign in.");
			redirectAttributes.addFlashAttribute("isSuccess", true);

			return "redirect:/login";
		}

		model.addAttribute("message", result);
		model.addAttribute("isSuccess", false);

		return "signup";
	}

	@PostMapping("/login")
	public String handleLogin(String email, String password, HttpSession session, Model model) {

		User user = authService.login(email, password);
		if (user != null) {
			session.setAttribute("loggedInUser", user);
			return "redirect:/dashboard";
		}
		model.addAttribute("message", "Credentials are wrong");
		model.addAttribute("isSuccess", false);
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {

		// Prevent access without login
		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}

		User user = (User) session.getAttribute("loggedInUser");

		List<MoneyVo> entries = moneyService.getAllDetails(user);

		float total = moneyService.totalAmount(user);

		model.addAttribute("entries", entries);
		model.addAttribute("total", total);
		model.addAttribute("moneyVo", new MoneyVo());

		return "dashboard";
	}

	@PostMapping("/entries")
	public String saveEntry(@ModelAttribute MoneyVo moneyVo, HttpSession session) {

		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}

		User user = (User) session.getAttribute("loggedInUser");
		moneyVo.setUser(user);
		moneyService.insertDetails(moneyVo);
		return "redirect:/dashboard";
	}

	@GetMapping("/entries/{id}/edit")
	public String editEntry(@PathVariable Integer id, HttpSession session, Model model) {

		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}

		User user = (User) session.getAttribute("loggedInUser");

		List<MoneyVo> entries = moneyService.getAllDetails(user);

		MoneyVo selected = entries.stream().filter(entry -> id.equals(entry.getId())).findFirst().orElse(null);

		float total = moneyService.totalAmount(user);

		model.addAttribute("entries", entries);
		model.addAttribute("moneyVo", selected);
		model.addAttribute("total", total);
		model.addAttribute("average", entries.isEmpty() ? 0f : total / entries.size());

		return "dashboard";
	}

	@PostMapping("/entries/{id}/update")
	public String updateEntry(@PathVariable Integer id, @ModelAttribute MoneyVo moneyVo, HttpSession session) {

		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}

		moneyVo.setId(id);
		User user = (User) session.getAttribute("loggedInUser");
		moneyVo.setUser(user);

		moneyService.updateDetails(moneyVo);

		return "redirect:/dashboard";
	}

	@PostMapping("/entries/{id}/delete")
	public String deleteEntry(@PathVariable Integer id, HttpSession session) {

		if (session.getAttribute("loggedInUser") == null) {
			return "redirect:/login";
		}
		
		moneyService.deleteDetails(id);

		return "redirect:/dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}

}