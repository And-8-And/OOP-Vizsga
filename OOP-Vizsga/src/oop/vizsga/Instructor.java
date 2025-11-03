package oop.vizsga;

public class Instructor extends BaseEntity implements Identifiable 
{
    private String name;
    
    public Instructor(String id, String name)
    {
        super(id);
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
    
    @Override
    public String businessKey() 
    {
        return "INSTR-" + getId();
    }

    @Override
    public String toString() 
    {
        return "Instructor{id=" + getId() + ", name='" + name + "'}";
    }
}
