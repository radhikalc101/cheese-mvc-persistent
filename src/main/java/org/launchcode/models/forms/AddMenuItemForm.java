package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.NotNull;

public class AddMenuItemForm {

    private Menu menu;

    private Iterable<Cheese> cheeses;

    @NotNull
    private int menuId;
    @NotNull
    private int cheeseId;

    //getters
    public Menu getMenu(){
        return menu;
    }
    public Iterable<Cheese> getCheeses(){
        return cheeses;
    }
    //-------------------------------
    public int getMenuId(){
        return menuId;
    }
    public int getCheeseId(){
        return cheeseId;
    }
    //setters ---------------------------
    public void setMenu(Menu menu){
        this.menu = menu;
    }
    public void setCheeses(Iterable<Cheese> cheeses){
        this.cheeses = cheeses;
    }
    // default constructor
    public AddMenuItemForm(){}

    // constructor with 2 params
    public AddMenuItemForm(Menu menu, Iterable<Cheese> cheeses){
        this.menu = menu;
        this.cheeses = cheeses;
    }


}
