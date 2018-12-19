package org.launchcode.controllers;


import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model){
        System.out.println("/menu");
        model.addAttribute("title","Menu List");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";//render template
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String menuAddForm(Model model){
//        System.out.println("/menu/add (GET)");
        model.addAttribute("title","Add Menu ");
        model.addAttribute(new Menu());//created a new menu object
        return "menu/add";// render template
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String menuAddFormProcess(@ModelAttribute @Valid Menu menu, Errors errors, Model model){
//        System.out.println("/menu/add (POST)");
        if(errors.hasErrors()){
            model.addAttribute("title","Add Menu");
            return "menu/add";
        }
        menuDao.save(menu);
        return "redirect:view/"+ menu.getId();
    }

    @RequestMapping(value = "view/{menuId}",method = RequestMethod.GET)
    public String viewMenu(Model model,@PathVariable int menuId){
//        System.out.println("/menu/view/"+menuId);
        Menu menu = menuDao.findOne(menuId);

        model.addAttribute("title",menu.getName());
        model.addAttribute("cheeses",menu.getCheeses());
        model.addAttribute("menuId",menu.getId());
        return "menu/view";
    }
    @RequestMapping(value = "add-item/{menuId}",method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId){
//        System.out.println("(GET)/menu/add-item/"+menuId);
        Menu menu = menuDao.findOne(menuId);// created menu object with the given id

        AddMenuItemForm form = new AddMenuItemForm(menu,cheeseDao.findAll());//created an instance of AddMenuItemForm with the given Menu object, as well as the list of all Cheese items in the database.
        model.addAttribute("title","Add item to menu:" + menu.getName());//a title that reads "Add item to menu: MENU NAME" (using the actual menu name).
        model.addAttribute("form",form);// Pass this form object into the view with the name "form"
        return "menu/add-item";//rendering the add-item.html template
    }

    @RequestMapping(value ="add-item",method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm form, Errors errors, Model model){
//        System.out.println("(POST)/menu/add-item");
//        System.out.println(errors);
        if(errors.hasErrors()){
            model.addAttribute("form",form);
            return "menu/add-item";
        }
        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        Menu theMenu = menuDao.findOne(form.getMenuId());
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:/menu/view/" + theMenu.getId();

    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveMenuForm(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Remove Menu");
        return "menu/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveMenuForm(@RequestParam int[] menuIds) {

        for (int menuId : menuIds) {
            menuDao.delete(menuId);
        }

        return "redirect:/menu";
    }

}
