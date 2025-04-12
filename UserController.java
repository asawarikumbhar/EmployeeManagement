package com.example.Employee_Management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.example.Employee_Management.entity.Employee;
import com.example.Employee_Management.entity.User;
import com.example.Employee_Management.repository.EmployeeRepository;
import com.example.Employee_Management.service.EmployeeService;
import com.example.Employee_Management.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@Autowired
	private EmployeeService  employeeService;
	
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("registration")
	public String registration(Model model){
		model.addAttribute("User", new User());
		return "registration";
	}
	
	
	@PostMapping("registration")
	public String showRegistration(@Valid @ModelAttribute("User") User user,BindingResult result, Model model){
		
		if(result.hasErrors()) {
			return "registration";
		}
       
		boolean status=userService.showRegistration(user);
		
		if(status) {
			model.addAttribute("successMsg", "registration sucessfully");
		}
		else{
			model.addAttribute("errorMsg", "You have already registered");
		}
		return "registration";
	}
	
	
	@GetMapping("loginPage")
	public String login(Model model){
		model.addAttribute("User", new User());
		return "login";
	}
	
	
	

	
	@PostMapping("login")
	public String submitLoginPage(@ModelAttribute ("User") User user,Model model) {
		User valid=userService.login(user.getEmail(), user.getPassword());
		
		if(valid!=null) {
			return "redirect:/viewEmployeeList";

		}
		else {
			model.addAttribute("errorMsg", "Enter valid details");
		}
		return "login";
		
	}
	
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
	  
//	  
//	  @GetMapping("/employees")
//	   public String viewEmployees(Model model) {
//	       List<Employee> employees = employeeService.getAllEmployees();
//	       model.addAttribute("employees", employees);
//	       return "viewEmployeeList"; 
//	   }
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
	  
	  
	  
	@GetMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		return "logout";
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
//        model.addAttribute("keyword", keyword);
        return "viewEmployeeList";
    }
    
    
	
	
	
}


