public abstract class Person implements IPerson   {
    private String name ;
    private String phoneNumber ;
    private LinkedList<IRide> rideHistory = new LinkedList<IRide>() ;
    private int id ;
    public Person(int id, String name, String phoneNumber ) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        
    }
    public int getId(){
        return id ;
    }
    public String getName(){
        return name ;
    }
    public void setName(String name){
        this.name = name ;
    }
    public String getPhoneNumber(){
        return phoneNumber ;
    }
    public void setPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.length() !=10){
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
        for(int i = 0 ; i < phoneNumber.length() ; i++){
            if(!Character.isDigit(phoneNumber.charAt(i))){
                throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
            }

        }
        this.phoneNumber = phoneNumber ;
    }
    public LinkedList<IRide> getRideHistory(){
        return rideHistory ;
    }
    public String toString(){
        return "ID: " + getId() + ", Name: " + getName() + ", Phone: " + getPhoneNumber() ;
    }

    
}
