package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
   public EmployeeController(EmployeeService theEmployeeService){

       employeeService = theEmployeeService;
   }

   @GetMapping("/list")
    public String listEmployee(Model theModel){
       // get the employees from db

       List<Employee > theEmployees =employeeService.findAll();
       //add to the Spring model
       theModel.addAttribute("employees",theEmployees);

       return "employees/list-employees";

   }
   @GetMapping("/showForAdd")
    public String showFormForAdd(Model theModel){

        Employee theEmployee = new Employee();
        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
   }
   @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
  //save the employee
        employeeService.save(theEmployee);

        return "redirect:/employees/list";
   }
   @GetMapping("/showFormForUpdate")

    public String showFormForUpdate (@RequestParam("employeeId") int theId,Model theModel){

        Employee theEmployee = employeeService.findById(theId);

        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
   }
   @GetMapping("/delete")
// detele the employee
    public String delete(@RequestParam ("employeeId") int theId){
// redirect to the/employees/list
         employeeService.deleteById(theId);
  return "redirect:/employees/list";


   }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model theModel) {
        List<Employee> theEmployees = employeeService.searchByKeyword(keyword);
        theModel.addAttribute("employees", theEmployees);
        return "employees/list-employees";
    }



}
