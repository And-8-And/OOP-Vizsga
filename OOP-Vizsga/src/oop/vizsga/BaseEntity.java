/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.vizsga;

/**
 *
 * @author And8And
 */
public abstract class BaseEntity 
{
    protected String id;

    public BaseEntity(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public abstract String businessKey();
}
