package com.search.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.microsoft.azure.storage.table.TableServiceException;
import com.search.app.dto.AppointmentAzureDelete;
import com.search.app.dto.AppointmentAzureEntity;
import com.search.app.dto.DoctorAzureEntity;
import com.search.app.dto.PersonAzure;
import com.search.app.service.AppointmentService;
import com.search.app.service.DoctorService;
import com.search.app.service.PersonService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentAzureController{

	@Autowired
	PersonService personService;

	@Autowired
	DoctorService doctorService;

	@Autowired
	AppointmentService appointmentService;
	
    @RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/registerdoc")
	public String registerdoc() {
		return "registerdoc";
	}

	@RequestMapping(value = "/registereddoc")
	public String registereddoc(DoctorAzureEntity doctor) throws Exception {
		doctorService.insertDoctorRecord(doctor);
		return "redirect:/";
	}

	@RequestMapping(value = "/")
	public String home() {
		return "start";
	}

	@RequestMapping(value = "/patlog")
	public String patlog(HttpSession session) {
		session.setAttribute("type", "person");
		return "index";
	}

	@RequestMapping(value = "/doclog")
	public String doclog(HttpSession session) {
		session.setAttribute("type", "doctor");
		return "doclog";
	}

	@RequestMapping(value = "/fail_login")
	public String fail_login() {
		return "fail_login";
	}

	@RequestMapping(value = "/authenticate")
	public String authenticate(PersonAzure person,HttpSession session) throws Exception{
		if(personService.searchPersonRecordbyId(person) == true) {
			session.setAttribute("person", person.getEmail());
			session.setAttribute("email", person.getEmail());
			return "redirect:/home";
			}
		return "redirect:/fail_login";
	}

	@RequestMapping("/authenticatedoc")
	public String authenticatedoc(DoctorAzureEntity doctor,HttpSession session) throws Exception{
		if(doctorService.searchDoctorRecordbyId(doctor) == true) {
			session.setAttribute("doctor", doctor.getEmail());
			session.setAttribute("email", doctor.getEmail());
			return "redirect:/patientlist";
			}
		return "redirect:/fail_login";
	}

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
	public ModelAndView DocDetails(HttpSession session) throws Exception{
		
		List<DoctorAzureEntity> doctors = doctorService.searchAllDoctorRecord();
		Map<String, Object> params = new HashMap<>();
		params.put("doctor", doctors);
		params.put("email", session.getAttribute("person"));
		return new ModelAndView("doctorlist", params);
	}

	@RequestMapping("/assignment")
	public String submitted(AppointmentAzureEntity app,HttpSession session) throws Exception{
		appointmentService.insertAppointmentRecord(app);
		session.setAttribute("email", app.getEmail());
		return "redirect:/docdetails";
	}

	@RequestMapping("/patientlist")
	public ModelAndView PatientList(HttpSession session) throws Exception{
		List<AppointmentAzureEntity> apps = appointmentService.findPatientListByDoctorId(session.getAttribute("doctor").toString());
		Map<String,Object> params = new HashMap<>();
		params.put("appointments", apps);
		params.put("email", session.getAttribute("doctor"));
		
		return new ModelAndView("appointedDoc",params);
	}

	@RequestMapping("/cancel")
	public String cancel(AppointmentAzureDelete deleteAppointment,HttpSession session) throws Exception{
		System.out.println("Appointment Delete Param: Email-"+ deleteAppointment.getEmail()+", Date-"+deleteAppointment.getDate());
		appointmentService.deleteAppointmentListByEmailIdAndDate(deleteAppointment);
		session.setAttribute("email", deleteAppointment.getEmail());
		return "redirect:/userdetails";
	}

	@GetMapping("/userdetails")
	public ModelAndView UserDetails(HttpSession session) throws Exception{
		List<AppointmentAzureEntity> apps = appointmentService.findAppointmentListByEmailId(session.getAttribute("email").toString());
		Map<String,Object> params = new HashMap<>();
		params.put("appointments", apps);
		params.put("email", session.getAttribute("email"));
		if(session.getAttribute("type") == "person"){
			return new ModelAndView("appointed",params);
		}
		return new ModelAndView("appointedDoc",params);
	}
}
