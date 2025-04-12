package com.example.Employee_Management.controller;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Employee_Management.entity.Employee;
import com.example.Employee_Management.entity.User;
import com.example.Employee_Management.repository.EmployeeRepository;
import com.example.Employee_Management.service.EmployeeService;
import com.example.Employee_Management.service.UserService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;




@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService  employeeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@GetMapping("employeeLogin")
	public String Employeelogin(Model model){
		model.addAttribute("User", new User());
		return "employeeLogin";
	}
	
	@PostMapping("employeeLogin")
	public String employeeSubmitLogin(@ModelAttribute ("User") User user,Model model) {
		User valid=userService.login(user.getEmail(), user.getPassword());
		
		if(valid!=null) {
			return "viewEmployeeList";

		}
		else {
			model.addAttribute("errorMsg", "Enter valid details");
		}
		return "employeeLogin";
		
	}

	@GetMapping("/addEmployee")
	public String addEmployeePage(Model model) {
	    model.addAttribute("Employee", new Employee());
	    return "addEmployee";
	}
	
	
   
   @PostMapping("/addEmployee")
   public String showAddEmployee(@ModelAttribute("Employee") Employee employee, Model model) {
       boolean status = employeeService.showAddEmployee(employee);
       
       if (status) {
           model.addAttribute("successMsg", "Employee added successfully!");
       } else {
           model.addAttribute("errorMsg", "Failed to add employee.");
       }
       return "addEmployee";
   }
   
   
   @GetMapping("aboutUs")
	public String aboutUs(Model model){
		model.addAttribute("User", new User());
		return "aboutUs";
	}
   
//   @GetMapping("/employees")
//   public String viewEmployees(Model model) {
//       List<Employee> employees = employeeService.getAllEmployees();
//       model.addAttribute("employees", employees);
//       return "viewEmployeeList"; 
//   }
//   
   
   
   @GetMapping("viewEmployeeList")
   public String getAllEmployeesList(Model model){
   	List<Employee> employees = employeeService.getAllEmployeesList();
   	model.addAttribute("employees", employees);
   	return "viewEmployeeList";
   }
   
   @GetMapping("/editEmployee/{id}")
   public String showUpdateEmployeePage(@PathVariable int id, Model model) {
       Employee employee = employeeService.getEmployeeById(id);
       if (employee != null) {
           model.addAttribute("Employee", employee);
           return "updateEmployee"; 
       } else {
           model.addAttribute("errorMsg", "Employee not found.");
           return "redirect:/viewEmployeeList";
       }
   }
   
   
   @PostMapping("/updateEmployee")
   public String editEmployee(@ModelAttribute("Employee") Employee employee, Model model) {
       boolean status = employeeService.updateEmployee(employee);
       if (status) {
           model.addAttribute("successMsg", "Employee updated successfully!");
       } else {
           model.addAttribute("errorMsg", "Failed to update employee.");
       }
       return "updateEmployee";
   }
	

   @GetMapping("/deleteEmployee/{id}")
   public String deleteEmployee(@PathVariable int id) {
       Employee employee = employeeRepository.findById(id).orElse(null);
       if (employee != null) {
           employeeRepository.delete(employee);
       }
       return "redirect:/viewEmployeeList";
   }
  

	    public EmployeeController(EmployeeService employeeService) {
	        this.employeeService = employeeService;
	    }

	    @GetMapping("/download")
	    public ResponseEntity<byte[]> downloadEmployeeReport() {
	        List<Employee> employees = employeeService.getAllEmployees();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(baos))) {
	            writer.println("ID, Name, Email, Department, Position, Salary, Address, Joining Date, Employee Type");
	            for (Employee employee : employees) {
	                writer.println(String.format("%d, %s, %s, %s, %s, %.2f, %s, %s, %s",
	                        employee.getId(), 
	                        employee.getName(), 
	                        employee.getEmail(),
	                        employee.getDepartment(), 
	                        employee.getPosition(),
	                        employee.getSalary(), 
	                        employee.getAddress(), 
	                        employee.getDateOfJoining(),   
	                        employee.getEmpType() ));
	                
	            }
	            writer.flush();
	        }
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=employee_report.csv");
	        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
	    }

	    @GetMapping("/download/pdf")
	    public ResponseEntity<byte[]> downloadPDF() throws Exception {
	        List<Employee> employees = employeeService.getAllEmployees();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter writer = new PdfWriter(baos);
	        PdfDocument pdfDocument = new PdfDocument(writer);
	        Document document = new Document(pdfDocument);
	        document.add(new Paragraph("Employee Report"));
	        document.add(new Paragraph("\n"));
	        for (Employee employee : employees) {
	            document.add(new Paragraph("ID: " + employee.getId()));
	            document.add(new Paragraph("Name: " + employee.getName()));
	            document.add(new Paragraph("Email: " + employee.getEmail()));
	            document.add(new Paragraph("Department: " + employee.getDepartment()));
	            document.add(new Paragraph("Position: " + employee.getPosition()));
	            document.add(new Paragraph("Salary: " + employee.getSalary()));
	            document.add(new Paragraph("Address: " + employee.getAddress()));
	            document.add(new Paragraph("Joining Date: " + employee.getDateOfJoining())); 
	            document.add(new Paragraph("Employee Type: " + employee.getEmpType()));                 
	            document.add(new Paragraph("\n"));
	        }

	        document.close();
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=employee_report.pdf");
	        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
	    }
	    
	  


	    @GetMapping("/employees")
	    public String showEmployees(@RequestParam(value = "field", required = false) String field,
	                                @RequestParam(value = "keyword", required = false) String keyword,
	                                Model model) {
	        List<Employee> employees;

	        if (field == null || field.isEmpty()) {
	            employees = employeeService.getAllEmployees(); // for all data
	        } else {
	            employees = employeeService.searchByField(field, keyword); //  for By search
	        }

	        model.addAttribute("employees", employees);
//	        model.addAttribute("keyword", keyword);
	        return "viewEmployeeList";
	    }
	    
	    
}
