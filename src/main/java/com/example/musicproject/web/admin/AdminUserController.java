package com.example.musicproject.web.admin;
import com.example.musicproject.dao.entities.User;
import com.example.musicproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model,
                            @RequestParam(name="page",defaultValue = "0") int page,
                            @RequestParam(name="taille",defaultValue = "8") int taille,
                            @RequestParam(name="search",defaultValue = "") String keyword) {

        Page<User> users = userService.findAllUsers(page,taille,keyword);
        model.addAttribute("users",users.getContent());
        int[] pages = new int[users.getTotalPages()];
        for (int i=0;i<pages.length;i++){
            pages[i]=i;
        }

        model.addAttribute("pages",pages);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "admin/user/list";

    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("user", new User());
        return "admin/user/add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          Model model){

        userService.addUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user){
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/info/{id}")
    public String showUserInfo(Model model,@PathVariable("id") Long id){
        User user = userService.findUserById(id);
        model.addAttribute("user",user);
        return "admin/user/info";
    }


}
