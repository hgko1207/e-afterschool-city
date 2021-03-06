package com.ysc.afterschool.controller;

import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysc.afterschool.domain.db.Student;
import com.ysc.afterschool.domain.db.Student.TargetType;
import com.ysc.afterschool.service.CityService;
import com.ysc.afterschool.service.SchoolService;
import com.ysc.afterschool.service.StudentService;

import reactor.core.publisher.Mono;

/**
 * 학생 관리 컨트롤러 클래스
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private CityService cityService;

	/**
	 * 학생 등록 화면
	 * 
	 * @param model
	 * @param cityId
	 */
	@GetMapping("regist")
	public void regist(Model model, int cityId) { 
		
		model.addAttribute("schools", schoolService.getList(cityId).stream()
				.map(s -> s.getName()).sorted().collect(Collectors.toList()));
		model.addAttribute("city", cityService.get(cityId));
		model.addAttribute("cityId", cityId);
	}
	
	/**
	 * 주민번호 중복 확인
	 * 
	 * @param student
	 * @return
	 */
	@GetMapping("search/jumin")
	@ResponseBody
	public Mono<Boolean> juminCheck(Student student) {
		return Mono.just(studentService.searchJumin(student));
	}
	
	/**
	 * 등록된 학생인지 확인
	 * 
	 * @param student
	 * @return
	 */
	@GetMapping("search")
	@ResponseBody
	public Mono<Boolean> search(Student student) {
		return Mono.just(studentService.search(student));
	}
	
	/**
	 * 학생 등록 기능
	 * @param student
	 * @return
	 */
	@PostMapping(value = "regist")
	@ResponseBody
	public ResponseEntity<?> regist(Student student) {
		
		if (!student.getTel().contains("-")) {
			String tel = student.getTel();
			tel = tel.substring(0, 4) + "-" + tel.substring(4, tel.length());
			student.setTel(student.getService() + "-" + tel);
		} else {
			student.setTel(student.getService() + "-" + student.getTel());
		}
		
		student.setName(student.getName().trim());
		
		String school = student.getSchool();
		student.setTargetType(school.contains("초등학교") ? TargetType.초등 : TargetType.중등);
		
		student.setCity(schoolService.get(school).getCity());
		school = school.endsWith("초등학교") ? school.substring(0, school.length() - 4) : school.substring(0, school.length() - 3);
		student.setSchoolInfo(school);
		
		if (student.isAgree() && !student.getCity().equals("함양")) {
			student.setResidentNumber(student.getJumin1() + "-" + student.getJumin2());
		}
		
		if (studentService.regist(student)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 학생 정보 변경 화면
	 * 
	 * @param model
	 * @param infoId
	 * @param authentication
	 * @param cookie
	 */
	@GetMapping("update")
	public void update(Model model, int infoId, Authentication authentication, 
			@CookieValue(value = "cityId", required = false) Cookie cookie) { 
		
		Student student = (Student) authentication.getPrincipal();
		
		model.addAttribute("schools", schoolService.getList().stream()
				.map(s -> s.getName()).sorted().collect(Collectors.toList()));
		model.addAttribute("infoId", infoId);
		model.addAttribute("city", cityService.get(cookie.getValue()));
		model.addAttribute("cityId", cookie.getValue());
		
		if (student.isAgree()) {
			String[] residentNumber = student.getResidentNumber().split("-");
			student.setJumin1(residentNumber[0]);
			student.setJumin2(residentNumber[1]);
		}
		
		model.addAttribute("student", student);
	}
	
	/**
	 * 학생 정보 변경 기능
	 * 
	 * @param student
	 * @return
	 */
	@PutMapping(value = "update")
	@ResponseBody
	public ResponseEntity<?> update(Student student) {
		
		Student temp = studentService.get(student.getId());
		temp.setGrade(student.getGrade());
		temp.setClassType(student.getClassType());
		temp.setNumber(student.getNumber());
		temp.setAgree(student.isAgree());
		
		if (student.isAgree()) {
			temp.setResidentNumber(student.getJumin1() + "-" + student.getJumin2());
		}
		
		String school = student.getSchool();
		temp.setSchool(school);
		temp.setTargetType(school.contains("초등학교") ? TargetType.초등 : TargetType.중등);
		temp.setCity(schoolService.get(school).getCity());
		
		school = school.endsWith("초등학교") ? school.substring(0, school.length() - 4) : school.substring(0, school.length() - 3);
		temp.setSchoolInfo(school);
		
		if (studentService.update(temp)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
