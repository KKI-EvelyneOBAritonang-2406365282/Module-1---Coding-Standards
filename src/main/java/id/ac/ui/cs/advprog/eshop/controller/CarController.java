package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Car controller works!";
    }


    @GetMapping("/list")
    public String carListPage(Model model) {
        List<Car> cars = carService.findAll();
        model.addAttribute("cars", cars);
        return "CarList";
    }

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "CreateCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable String id, Model model) {
        Car car = carService.findById(id);
        model.addAttribute("car", car);
        return "EditCar";
    }

    @PostMapping("/edit")
    public String editCarPost(@ModelAttribute Car car) {
        carService.update(car.getCarId(), car);
        return "redirect:/car/list";
    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam String id) {
        carService.deleteCarById(id);
        return "redirect:/car/list";
    }

}