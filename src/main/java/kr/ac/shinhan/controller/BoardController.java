package kr.ac.shinhan.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.shinhan.Board;
import kr.ac.shinhan.BoardDAO;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardDAO dao;

	// 게시판 목록
	@GetMapping
	public String listBoard(Model model) {
		try {
			List<Board> list = dao.getAll();
			model.addAttribute("boardList", list);
		} catch (Exception e) {
			model.addAttribute("error", "게시글 목록을 불러오는 데 실패했습니다.");
		}
		return "board/boardList";
	}
	
	// 게시글 상세 보기
	@GetMapping("/{id}")
	public String getBoard(@PathVariable("id") int id, Model model) {
		try {
			dao.increaseViewCount(id); // 조회수 증가
			Board board = dao.getBoard(id);
			model.addAttribute("board", board);
		} catch (Exception e) {
			model.addAttribute("error", "게시글을 불러오는 데 실패했습니다.");
		}
		return "board/boardView";
	}
	
	// 게시글 작성 폼 (GET)
	@GetMapping("/write")
	public String showWriteForm() {
		return "board/boardForm";
	}
	
	// 게시글 작성 처리 (POST)
	@PostMapping("/write")
	public String addBoard(@ModelAttribute Board board) {
		try {
			dao.addBoard(board);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("등록 실패", StandardCharsets.UTF_8);
			return "redirect:/board?error=" + errorMsg;
		}
		return "redirect:/board";
	}
	
	// 게시글 수정 폼 (GET)
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") int id, Model model) {
		try {
			Board board = dao.getBoard(id);
			model.addAttribute("board", board);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("게시글을 찾을 수 없습니다", StandardCharsets.UTF_8);
			return "redirect:/board?error=" + errorMsg;
		}
		return "board/boardEdit";
	}
	
	// 게시글 수정 처리 (POST)
	@PostMapping("/edit")
	public String updateBoard(@ModelAttribute Board board) {
		try {
			dao.updateBoard(board);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("수정 실패", StandardCharsets.UTF_8);
			return "redirect:/board?error=" + errorMsg;
		}
		return "redirect:/board/" + board.getId();
	}
	
	// 게시글 삭제
	@GetMapping("/delete/{id}")
	public String deleteBoard(@PathVariable("id") int id) {
		try {
			dao.deleteBoard(id);
		} catch (Exception e) {
			String errorMsg = URLEncoder.encode("삭제 실패", StandardCharsets.UTF_8);
			return "redirect:/board?error=" + errorMsg;
		}
		return "redirect:/board";
	}
}




