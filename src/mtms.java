import java.sql.*;
import java.util.Scanner;

public class mtms
{
    private String dataconn= "jdbc:mariadb://localhost:3307/mtms";
    private String user = "root";
    private String pass = "12345";
	
    Connection sqlconn=null;
    Statement stmt=null;
    ResultSet rs,rs1=null;

    // Database Connection
    
    public void db_connection()
    {
        try
        {
            Class.forName("org.mariadb.jdbc.Driver");
	    sqlconn=DriverManager.getConnection(dataconn,user,pass);
	    stmt=sqlconn.createStatement();
	}
	catch(ClassNotFoundException | SQLException ex)
	{
            System.out.println("Exception : "+ex);
        }
    }
    
    // Customer Login
    
    public void customer_login()
    {
        Scanner sc = new Scanner(System.in);
        
        int choice;
        char ch;
        
        do
        {
            customer_menu();
            
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();
            
            switch(choice)
            {
                case 1 -> purchase_card();
                case 2 -> recharge_card();
                case 3 -> check_card_balance();
                case 4 -> purchase_token();
                case 5 -> view_route();
                case 6 -> 
                { 
                    System.out.println("\n!!!  Successfully Exited  !!!");
                    System.out.println();
                    System.exit(0);
                }
            }
            
            System.out.print("\nWant to go customer menu (y/n) : ");
            ch = sc.next().charAt(0);
        }while(ch=='y');
    }
    
    // Purchase Card
    
    public void purchase_card()
    {
        Scanner sc = new Scanner(System.in);
        
        String name, address;
        long mobile_number;
        boolean flag = false;
        
        System.out.println("\n     *****     Purchase Card     *****");
        
        flag = false;
        do
        {
            System.out.print("\nEnter your name : ");       
            name = sc.nextLine();
         
            int space = 0, alphabet = 0;
            
            for(int i=0;i<name.length();i++)
            {
                if(Character.isAlphabetic(name.charAt(i)))
                    alphabet++;
                else if(name.charAt(i)==' ')
                    space++;
            }
            
            if((space==0 && alphabet==name.length()) || (space==1 && alphabet==name.length()-1))
                flag = true;
            else
                System.out.println("\n!!!   Please input a correct name   !!!");
                
        }while(flag!=true);
        
        System.out.print("\nEnter address : ");
        address = sc.nextLine();
        
        flag = false;
        String temp;
        do
        {
            int count = 0;
            boolean z = false;
            
            System.out.print("\nEnter your mobile number : ");
            temp = sc.next();
            
            for(int i=0;i<temp.length();i++)
            {
                if(Character.isDigit(temp.charAt(i)))
                    count++;
            }
            
            if( (count==10 && temp.charAt(0)!='0') || (count==11 && temp.charAt(0)=='0') )
                flag = true;
            else
                System.out.println("\n!!!   Please input a correct mobile number   !!!");
            
            if(flag==true)
            {
                try
                {
                    rs = stmt.executeQuery("select mobile_number from customer");   

                    while(rs.next())
                    {
                        if(temp.equals(rs.getString("mobile_number")))
                        {
                            z = true;
                            break;
                        }
                    }
                }
                catch(SQLException ex)
                {
                    System.out.println("Exception : "+ex);
                }
            }
            
            if(z==true)
            {
                flag = false;
                System.out.println("\n!!!   Mobile number is already registered   !!!");
            }
            
        }while(flag!=true);
        
        mobile_number = Long.parseLong(temp);
        
        System.out.println("\nYour Fare is 200");
        
        try
        {
            stmt.executeUpdate("insert into customer(balance,name,address,mobile_number) values(200,'"+name+"','"+address+"',"+mobile_number+")");
            System.out.println("\nCard Purchased Successfully.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Recharge Card
    
    public void recharge_card()
    {
        Scanner sc = new Scanner(System.in);
        
        int id, amount;
        boolean flag = false;
        
        System.out.println("\n     *****     Recharge Card     *****");
        
        System.out.print("\nEnter the id : ");
        id = sc.nextInt();
        
        try
        {
            rs = stmt.executeQuery("select * from customer");
            while(rs.next())
            {
                if(rs.getInt("id")==id)
                {
                    flag = true;
                    System.out.println("\nYour Current Balance is "+rs.getInt("balance"));
                    System.out.print("\nEnter amount to recharge : ");
                    amount = sc.nextInt();
                    amount = amount + rs.getInt("balance");
                    stmt.executeUpdate("update customer set balance="+amount+" where id="+id);
                    System.out.println("\nCard Recharged Succesfully.....");
                    System.out.println("\nNow Your Balance is "+amount);
                    break;
                }
            }
            if(flag==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Check Card Balance
    
    public void check_card_balance()
    {
        Scanner sc = new Scanner(System.in);
        
        int id;
        boolean flag = false;
        
        System.out.println("\n     *****     Check Card Balance     *****");
        
        System.out.print("\nEnter the id : ");
        id = sc.nextInt();
        
        try
        {
            rs = stmt.executeQuery("select * from customer");
            while(rs.next())
            {
                if(rs.getInt("id")==id)
                {
                    flag = true;
                    System.out.println("\nCard Details are given below.....\n");
                    System.out.println("Id : "+id);
                    System.out.println("Balance : "+rs.getInt("balance"));
                    System.out.println("Name : "+rs.getString("name"));
                    System.out.println("Address : "+rs.getString("address"));
                    System.out.println("Mobile Number : "+rs.getString("mobile_number"));
                    break;
                }
            }
            if(flag==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Purchase Token
    
    public void purchase_token()
    {
        Scanner sc = new Scanner(System.in);
        int x = 0, y = 0, count = 0, station = 0, k = 0;
        String source, destination, line = "";
        boolean flag = false;
        String s[] = new String[50];
        
        System.out.println("\n     *****     Purchase Token     *****");
        
        System.out.print("\nEnter the source : ");
        source = sc.nextLine();
        System.out.print("\nEnter the destination : ");
        destination = sc.nextLine();
        
        try
        {
            rs = stmt.executeQuery("select * from red_line");
            while(rs.next())
            {
                count++;
                if(source.equalsIgnoreCase(rs.getString("station")))
                    x = count;
                if(destination.equalsIgnoreCase(rs.getString("station")))
                    y = count;
            }
            
            if((x==0 && y!=0) || (y==0 && x!=0))
                System.out.println("\nSource and Destination do not belong to same line.....");
            else if(x!=0 && y!=0)
            {
                flag = true;
                line = "red_line";
                if(x>y)
                    station = x - y ;
                else
                    station = y - x ;
            }
            else if(x==0 && y==0)
            {
                count = 0;
                rs = stmt.executeQuery("select * from yellow_line");
                while(rs.next())
                {
                    count++;
                    if(source.equalsIgnoreCase(rs.getString("station")))
                        x = count;
                    if(destination.equalsIgnoreCase(rs.getString("station")))
                        y = count;
                }
                if((x==0 && y!=0) || (y==0 && x!=0))
                System.out.println("\nSource and Destination do not belong to same line.....");
                else if(x!=0 && y!=0)
                {
                    flag = true;
                    line = "yellow_line";
                    if(x>y)
                        station = x - y ;
                    else
                        station = y - x ;
                }
                else if(x==0 && y==0)
                {
                    count = 0;
                    rs = stmt.executeQuery("select * from pink_line");
                    while(rs.next())
                    {
                        count++;
                        if(source.equalsIgnoreCase(rs.getString("station")))
                            x = count;
                        if(destination.equalsIgnoreCase(rs.getString("station")))
                            y = count;
                    }
                    if((x==0 && y!=0) || (y==0 && x!=0))
                        System.out.println("\nSource and Destination do not belong to same line.....");
                    else if(x!=0 && y!=0)
                    {
                        flag = true;
                        line = "pink_line";
                        if(x>y)
                            station = x - y ;
                        else
                            station = y - x ;
                    }
                }
            }
            if(x==0 && y==0)
            {
                System.out.println("\nStations are not correct.....");
            }
            if(x!=0 && y!=0)
            {
                System.out.println("\nFare Amount is : "+station*3);
                switch(line) 
                {
                    case "red_line" -> System.out.println("\nYou are going to visit Red Line");
                    case "yellow_line" -> System.out.println("\nYou are going to visit Yellow Line");
                    case "pink_line" -> System.out.println("\nYou are going to visit Pink Line");
                }
            }
            if(flag==true)
            {
                count = 0;
                rs = stmt.executeQuery("select * from "+line);
                while(rs.next())
                {
                    count++;
                    if( (count<=x && count>=y) || (count>=x && count<=y) )
                    {
                        s[k] = rs.getString("station");
                        k++;
                    }
                }
                System.out.println("\nIntermediate Station are given below.....\n");
                if(x<=y)
                {
                    for(int i=0;i<k;i++)
                    {
                        System.out.println(s[i]);
                    }
                }
                else
                {
                    for(int i=k-1;i>=0;i--)
                    {
                        System.out.println(s[i]);
                    }
                }
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // View Route
    
    public void view_route()
    {
        Scanner sc = new Scanner(System.in);
        int x = 0, y = 0, count = 0, k = 0;
        String source, destination, line = "";
        boolean flag = false;
        String[] s = new String[50];
        
        System.out.println("\n     *****     View Route     *****");
        
        System.out.print("\nEnter the source : ");
        source = sc.nextLine();
        System.out.print("\nEnter the destination : ");
        destination = sc.nextLine();
        
        try
        {
            rs = stmt.executeQuery("select * from red_line");
            while(rs.next())
            {
                count++;
                if(source.equalsIgnoreCase(rs.getString("station")))
                    x = count;
                if(destination.equalsIgnoreCase(rs.getString("station")))
                    y = count;
            }
            
            if((x==0 && y!=0) || (y==0 && x!=0))
                System.out.println("\nSource and Destination do not belong to same line.....");
            else if(x!=0 && y!=0)
            {
                flag = true;
                line = "red_line";
            }
            else if(x==0 && y==0)
            {
                count = 0;
                rs = stmt.executeQuery("select * from yellow_line");
                while(rs.next())
                {
                    count++;
                    if(source.equalsIgnoreCase(rs.getString("station")))
                        x = count;
                    if(destination.equalsIgnoreCase(rs.getString("station")))
                        y = count;
                }
                if((x==0 && y!=0) || (y==0 && x!=0))
                System.out.println("\nSource and Destination do not belong to same line.....");
                else if(x!=0 && y!=0)
                {
                    flag = true;
                    line = "yellow_line";
                }
                else if(x==0 && y==0)
                {
                    count = 0;
                    rs = stmt.executeQuery("select * from pink_line");
                    while(rs.next())
                    {
                        count++;
                        if(source.equalsIgnoreCase(rs.getString("station")))
                            x = count;
                        if(destination.equalsIgnoreCase(rs.getString("station")))
                            y = count;
                    }
                    if((x==0 && y!=0) || (y==0 && x!=0))
                       System.out.println("\nSource and Destination do not belong to same line.....");
                    else if(x!=0 && y!=0)
                    {
                        flag = true;
                        line = "pink_line";
                    }
                }
            }
            if(x==0 && y==0)
            {
                System.out.println("\nStations are not correct.....");
            }
            if(flag==true)
            {
                switch(line) 
                {
                    case "red_line" -> System.out.println("\nYou are going to visit Red Line");
                    case "yellow_line" -> System.out.println("\nYou are going to visit Yellow Line");
                    case "pink_line" -> System.out.println("\nYou are going to visit Pink Line");
                }
                
                count = 0;
                rs = stmt.executeQuery("select * from "+line);
                while(rs.next())
                {
                    count++;
                    if( (count>=x && count<=y) || (count<=x && count>=y) )
                    {
                        s[k] = rs.getString("station");
                        k++;
                    }
                }
                System.out.println("\nIntermediate Station are given below.....\n");
                if(x<=y)
                {
                    for(int i=0;i<k;i++)
                    {
                        System.out.println(s[i]);
                    }
                }
                else
                {
                    for(int i=k-1;i>=0;i--)
                    {
                        System.out.println(s[i]);
                    }
                }
            }
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Admin Login
    
    public void admin_login()
    {
        Scanner sc = new Scanner(System.in);
        
        int choice;
        char ch;
        String user_name = "Saket Anand", password = "5134", x, y;
        
        System.out.println("\n====================================================================");        
        
        System.out.print("\nEnter the user name : ");
        x = sc.nextLine();
        System.out.print("\nEnter the password : ");
        y = sc.nextLine();

        System.out.println("\n====================================================================");        
        
        if(user_name.equals(x) && password.equals(y))
        {
            System.out.println("\n                  !!!   Login successfull   !!!");
            do
            {
                admin_menu();

                System.out.print("\nEnter your choice : ");
                choice = sc.nextInt();

                switch(choice)
                {
                    case 1 -> customer_detail();
                    case 2 -> staff_control();
                    case 3 -> staff_detail();
                    case 4 -> 
                    { 
                        System.out.println("\n!!!  Successfully Exited  !!!");
                        System.out.println();
                        System.exit(0);
                    }
                }

                System.out.print("\nWant to go admin menu (y/n) : ");
                ch = sc.next().charAt(0);
            }while(ch=='y');
        }
        else
        {
            System.out.println("\n                  !!!   Login unsuccessfull   !!!");
            System.out.println("\n====================================================================");        
        }    
    }
    
    // Customer Detail
    
    public void customer_detail()
    {
        boolean flag = false;
                    
        try
        {
            rs = stmt.executeQuery("select * from customer");   

            while(rs.next())
            {
                if(flag==false)
                    System.out.println("\n     *****    Available Customer     *****");
                    
                System.out.println("\nId : "+rs.getInt("id"));
                System.out.println("Balance : "+rs.getInt("balance"));
                System.out.println("Name : "+rs.getString("name"));
                System.out.println("Address : "+rs.getString("address"));
                System.out.println("Mobile Number : "+rs.getString("mobile_number"));
                System.out.println("\n********************************************************************");
                flag = true;
            }
            
            if(flag==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Staff Control
    
    public void staff_control()
    {
        Scanner sc = new Scanner(System.in);
        
        int choice;
        char ch;
        
        do
        {
            staff_menu();
            
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();
            
            switch(choice)
            {
                case 1 -> add_staff();
                case 2 -> remove_staff();
                case 3 -> update_staff();
            }
            
            System.out.print("\nWant to go staff control (y/n) : ");
            ch = sc.next().charAt(0);
        }while(ch=='y');
    }
    
    // Add Staff
    
    public void add_staff()
    {
        Scanner sc = new Scanner(System.in);
        
        String name, address;
        long mobile_number;
        boolean flag = false;
        int salary;
        
        System.out.println("\n     *****     Add Staff     *****");
        
        do
        {
            System.out.print("\nEnter your name : ");       
            name = sc.nextLine();
         
            int space = 0, alphabet = 0;
            
            for(int i=0;i<name.length();i++)
            {
                if(Character.isAlphabetic(name.charAt(i)))
                    alphabet++;
                else if(name.charAt(i)==' ')
                    space++;
            }
            
            if((space==0 && alphabet==name.length()) || (space==1 && alphabet==name.length()-1))
                flag = true;
            else
                System.out.println("\n!!!   Please input a correct name   !!!");
                
        }while(flag!=true);
        
        System.out.print("\nEnter address : ");
        address = sc.nextLine();
        
        flag = false;
        String temp;
        do
        {
            int count = 0;
            boolean z = false;
            
            System.out.print("\nEnter your mobile number : ");
            temp = sc.next();
            
            for(int i=0;i<temp.length();i++)
            {
                if(Character.isDigit(temp.charAt(i)))
                    count++;
            }
            
            if( (count==10 && temp.charAt(0)!='0') || (count==11 && temp.charAt(0)=='0') )
                flag = true;
            else
                System.out.println("\n!!!   Please input a correct mobile number   !!!");
            
            if(flag==true)
            {
                try
                {
                    rs = stmt.executeQuery("select mobile_number from staff");   

                    while(rs.next())
                    {
                        if(temp.equals(rs.getString("mobile_number")))
                        {
                            z = true;
                            break;
                        }
                    }
                }
                catch(SQLException ex)
                {
                    System.out.println("Exception : "+ex);
                }
            }
            
            if(z==true)
            {
                flag = false;
                System.out.println("\n!!!   Mobile number is already registered   !!!");
            }
            
        }while(flag!=true);
        
        mobile_number = Long.parseLong(temp);
        
        System.out.print("\nEnter your salary : ");
        while(!sc.hasNextInt())
        {
            System.out.print("\n!!!   Input must be an integer   !!!");
            System.out.print("\n\nEnter your salary : ");
            sc.next();
        }  
       salary = sc.nextInt();
        
        try
        {
            stmt.executeUpdate("insert into staff(name,address,mobile_number,salary) values('"+name+"','"+address+"',"+mobile_number+","+salary+")");
            System.out.println("\nStaff Added Successfully.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Remove Staff
    
    public void remove_staff()
    {
        Scanner sc = new Scanner(System.in);
        
        boolean flag = false;
        int id;
        
        System.out.println("\n     *****     Remove Staff     *****");
        
        System.out.print("\nEnter the id : ");
        id = sc.nextInt();
        
        try
        {
            rs = stmt.executeQuery("select * from staff");   

            while(rs.next())
            {
                if(rs.getInt("id")==id)
                {
                    flag = true;
                    stmt.executeUpdate("delete from staff where id="+id);
                    System.out.println("\nStaff Removed Successfully.....");
                    break;
                }
            }
            
            if(flag==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Update Staff 
    
    public void update_staff()
    {
        Scanner sc = new Scanner(System.in);
        
        int id, salary;
        String name, address;
        long mobile_number;
        boolean flag1 = false, flag;
        
        System.out.println("\n     *****     Update Staff     *****");
        
        System.out.print("\nEnter the id : ");
        id = sc.nextInt();
        
        try
        {   
            rs = stmt.executeQuery("select * from staff");   

            while(rs.next())
            {
                if(rs.getInt("id")==id)
                {
                    flag1 = true;
                    
                    flag = false;
                    do
                    {
                        System.out.print("\nEnter your name : ");       
                        sc = new Scanner(System.in);
                        name = sc.nextLine();

                        int space = 0, alphabet = 0;

                        for(int i=0;i<name.length();i++)
                        {
                            if(Character.isAlphabetic(name.charAt(i)))
                                alphabet++;
                            else if(name.charAt(i)==' ')
                                space++;
                        }

                        if((space==0 && alphabet==name.length()) || (space==1 && alphabet==name.length()-1))
                            flag = true;
                        else
                            System.out.println("\n!!!   Please input a correct name   !!!");

                    }while(flag!=true);

                    System.out.print("\nEnter address : ");
                    sc = new Scanner(System.in);
                    address = sc.nextLine();

                    flag = false;
                    String temp;
                    do
                    {
                        int count = 0;
                        boolean z = false;

                        System.out.print("\nEnter your mobile number : ");
                        sc = new Scanner(System.in);
                        temp = sc.next();

                        for(int i=0;i<temp.length();i++)
                        {
                            if(Character.isDigit(temp.charAt(i)))
                                count++;
                        }

                        if( (count==10 && temp.charAt(0)!='0') || (count==11 && temp.charAt(0)=='0') )
                            flag = true;
                        else
                            System.out.println("\n!!!   Please input a correct mobile number   !!!");

                        if(flag==true)
                        {
                            try
                            {
                                rs1 = stmt.executeQuery("select mobile_number from customer");   

                                while(rs1.next())
                                {
                                    if(temp.equals(rs1.getString("mobile_number")))
                                    {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                            catch(SQLException ex)
                            {
                                System.out.println("Exception : "+ex);
                            }
                        }

                        if(z==true)
                        {
                            flag = false;
                            System.out.println("\n!!!   Mobile number is already registered   !!!");
                        }

                    }while(flag!=true);

                    mobile_number = Long.parseLong(temp);
                    
                    System.out.print("\nEnter your salary : ");   
        
                    while(!sc.hasNextInt())
                    {
                        System.out.print("\n!!!   Input must be an integer   !!!");
                        System.out.print("\n\nEnter your salary : ");
                        sc.next();
                    }  
                    salary = sc.nextInt();
                    
                    stmt.executeUpdate("update staff set name='"+name+"', address='"+address+"', mobile_number="+mobile_number+", salary="+salary+" where id="+id);
                    System.out.println("\nStaff Updated Successfully.....");
                    break;
                }
            }
            if(flag1==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
        
    }
    
    // Staff Detail
    
    public void staff_detail()
    {
        boolean flag = false;
                    
        try
        {
            rs = stmt.executeQuery("select * from staff");   

            while(rs.next())
            {
                if(flag==false)
                    System.out.println("\n     *****    Available Staff     *****");
                    
                System.out.println("\nId : "+rs.getInt("id"));
                System.out.println("Name : "+rs.getString("name"));
                System.out.println("Address : "+rs.getString("address"));
                System.out.println("Mobile Number : "+rs.getString("mobile_number"));
                System.out.println("Salary : "+rs.getInt("salary"));
                System.out.println("\n********************************************************************");
                flag = true;
            }
            
            if(flag==false)
                System.out.println("\nNo Record Found.....");
        }
        catch(SQLException ex)
        {
            System.out.println("Exception : "+ex);
        }
    }
    
    // Main Menu
    
    public void main_menu()
    {
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$   -----------------------------  $");
        System.out.println("$   METRO TRAIN MANAGEMENT SYSTEM  $");
        System.out.println("$   -----------------------------  $");
        System.out.println("$..................................$");
        System.out.println("$                                  $");
        System.out.println("$         1. Customer              $");
        System.out.println("$                                  $");
        System.out.println("$         2. Admin                 $");
        System.out.println("$                                  $");
        System.out.println("$         3. Exit                  $");
        System.out.println("$                                  $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
    
    // Customer Menu
    
    public void customer_menu()
    {
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$  -----------------------------   $");
        System.out.println("$            CUSTOMER              $");
        System.out.println("$  -----------------------------   $");
        System.out.println("$..................................$");
        System.out.println("$                                  $");
        System.out.println("$        1. Purchase Card          $");
        System.out.println("$                                  $");
        System.out.println("$        2. Recharge Card          $");
        System.out.println("$                                  $");
        System.out.println("$        3. Check Card Balance     $");
        System.out.println("$                                  $");
        System.out.println("$        4. Purchase Token         $");
        System.out.println("$                                  $");
        System.out.println("$        5. View Route             $");
        System.out.println("$                                  $");
        System.out.println("$        6. Exit                   $");
        System.out.println("$                                  $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
    
    // Admin Menu
    
    public void admin_menu()
    {
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$  -----------------------------   $");
        System.out.println("$              ADMIN               $");
        System.out.println("$  -----------------------------   $");
        System.out.println("$..................................$");
        System.out.println("$                                  $");
        System.out.println("$        1. Customer Details       $");
        System.out.println("$                                  $");
        System.out.println("$        2. Staff Control          $");
        System.out.println("$                                  $");
        System.out.println("$        3. Staff Details          $");
        System.out.println("$                                  $");
        System.out.println("$        4. Exit                   $");
        System.out.println("$                                  $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
    
    // Staff Menu
    
    public void staff_menu()
    {
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$  -----------------------------   $");
        System.out.println("$              STAFF               $");
        System.out.println("$  -----------------------------   $");
        System.out.println("$..................................$");
        System.out.println("$                                  $");
        System.out.println("$          1. Add Staff            $");
        System.out.println("$                                  $");
        System.out.println("$          2. Remove Staff         $");
        System.out.println("$                                  $");
        System.out.println("$          3. Update Staff         $");
        System.out.println("$                                  $");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
    
    // Main Function
    
    public static void main(String[] args) 
    {
        mtms obj = new mtms();
    	obj.db_connection();
        
        Scanner sc = new Scanner(System.in);
        
        int choice;
        char ch;
        
        do
        {
            obj.main_menu();
          
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();
            
            switch(choice)
            {
                case 1 -> obj.customer_login();
                case 2 -> obj.admin_login();
                case 3 -> 
                { 
                    System.out.println("\n!!!  Successfully Exited  !!!");
                    System.out.println();
                    System.exit(0);
                }
            }
            
            System.out.print("\nWant to go main menu (y/n) : ");
            ch = sc.next().charAt(0);
            
        }while(ch=='y');
        System.out.println();
    }
}