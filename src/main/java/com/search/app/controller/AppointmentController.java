package com.search.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.microsoft.azure.storage.table.TableServiceException;
import com.search.app.dto.Doctor;
import com.search.app.dto.Person;
import com.search.app.dto.PersonAzure;
import com.search.app.model.DoctorModel;
import com.search.app.repository.DoctorRepository;
import com.search.app.repository.PersonRepository;
import com.search.app.service.PersonService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController{

    private static final String template = "Hello, %s";
	private final AtomicLong count = new AtomicLong();

	@Autowired
	PersonRepository personRepo;

	@Autowired
	PersonService personService;

	@Autowired
	DoctorRepository docRepo;

    @RequestMapping("/greeting")
    public DoctorModel greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
        
        return new DoctorModel(count.incrementAndGet(), String.format(template, name));
    }
    @RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/registerdoc")
	public String registerdoc() {
		return "registerdoc";
	}

	@RequestMapping(value = "/registereddoc")
	public String registereddoc(Doctor doctor) {
		docRepo.save(doctor);
		return "redirect:/";
	}

	@RequestMapping(value = "/")
	public String home() {
		return "start";
	}

	@RequestMapping(value = "/patlog")
	public String patlog() {
		return "index";
	}

	@RequestMapping(value = "/doclog")
	public String doclog() {
		return "doclog";
	}

	@RequestMapping(value = "/fail_login")
	public String fail_login() {
		return "fail_login";
	}

	@RequestMapping(value = "/authenticate")
	public String authenticate(Person person,HttpSession session) {
		if(personRepo.existsById(person.getEmail()) && personRepo.findById(person.getEmail()).get().getPassword().equals(person.getPassword())) {
			session.setAttribute("person", person.getEmail());
			return "redirect:/home";
			}
		return "redirect:/fail_login";
	}

	/*@RequestMapping("/registered")
	public String registered(Person person) {
		personRepo.save(person);
		return "redirect:/";
	}*/

	@RequestMapping("/registered")
	public String registered1(PersonAzure person) throws Exception{
		try{
			personService.insertPersonRecord(person);
		}catch(TableServiceException e){
			System.err.println(e.getMessage());
		}
		
		return "redirect:/";
	}

	@RequestMapping(value = "/home")
	public ModelAndView display(HttpSession session) {
		ModelAndView mav= new ModelAndView("fail_login");
		String email = null;
		if(session.getAttribute("person")!=null) {
			mav = new ModelAndView("home");
		email = (String) session.getAttribute("person");
		}
		mav.addObject("email",email);
		return mav;
	}

	@RequestMapping(value = "/docdetails")
	public ModelAndView DocDetails(HttpSession session) {
		
		List<Doctor> doctors = new ArrayList<Doctor>();
		docRepo.findAll().forEach(doctors::add);
		Map<String, Object> params = new HashMap<>();
		params.put("doctor", doctors);
		params.put("email", session.getAttribute("person"));
		return new ModelAndView("doctorlist", params);
	}
}
